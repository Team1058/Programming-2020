package frc.robot.autonomous;

import frc.robot.actuation.DifferentialDrive;
import frc.robot.geometry.Pose;
import frc.robot.subsystems.DriveTrainSubsystem;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

public class MotionPlanner {

    Trajectory trajectory;
    DifferentialDrive drivetrain;
    EncoderFollower leftFollower;
    EncoderFollower rightFollower;
    public boolean hasRun = false;
    public int startLeftEncoderTick;
    public int startRightEncoderTick;

    Trajectory.Config config = new Trajectory.Config(
        Trajectory.FitMethod.HERMITE_CUBIC,
        Trajectory.Config.SAMPLES_LOW,
        0.02, // Time delta in seconds
        1, // Maximum velocity in m/s
        1, // Max acceleration in m/s^2
        100 // Max jerk in m/s^3
    );
    int segmentIndex = 0;

    public MotionPlanner(DifferentialDrive drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void moveTo(double x, double y, double angle, boolean forward) {
        if (forward) {
            startLeftEncoderTick = drivetrain.getLeftEncoderTicks();
            startRightEncoderTick = drivetrain.getRightEncoderTicks();
        } else {
            startLeftEncoderTick = drivetrain.getRightEncoderTicks();
            startRightEncoderTick = drivetrain.getLeftEncoderTicks(); 
        }
        Pose pose = drivetrain.getPose();
        Waypoint[] singleMoveTo = new Waypoint[]{
            new Waypoint(pose.getX(), pose.getX(), pose.getYaw()), new Waypoint(x, y, angle)
        };
        trajectory = Pathfinder.generate(singleMoveTo, config);
        TankModifier modifier = new TankModifier(trajectory).modify(drivetrain.getTrackWidth());
        leftFollower = new EncoderFollower(modifier.getLeftTrajectory());
        rightFollower = new EncoderFollower(modifier.getRightTrajectory());
        leftFollower.configureEncoder(startLeftEncoderTick, 42 , drivetrain.getWheelRadius() * 2);
        rightFollower.configureEncoder(startRightEncoderTick, 42 , drivetrain.getWheelRadius() * 2);
        leftFollower.configurePIDVA(0.25, 0.0, 0.0, 1 / 5.842, 0);
        rightFollower.configurePIDVA(0.25, 0.0, 0.0, 1 / 5.842, 0);
        segmentIndex = 0;
        System.out.println(trajectory.length());

        hasRun = true;
    }

    public void followPath() { 
        // Matt says this might need to be trajectory.length()-1
        if (trajectory == null || segmentIndex == trajectory.length() - 1) {
            drivetrain.setTargetVelocity(0, 0);
        } else {
            // Trajectory.Segment segment = trajectory.get(segmentIndex);
            // Trajectory.Segment nextSegment = trajectory.get(segmentIndex + 1);
            // double omegaZ = (nextSegment.heading - segment.heading)/.02;
            // drivetrain.setTargetVelocity(segment.velocity, omegaZ);

            double left = leftFollower.calculate(drivetrain.getLeftEncoderTicks());
            double right = rightFollower.calculate(drivetrain.getRightEncoderTicks());

            double desiredHeading = Pathfinder.r2d(leftFollower.getHeading());

            double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - drivetrain.getPose().getYaw());

            double turn = angleDifference / 180 * .5;

            drivetrain.setPercentVelocity(left - turn, right + turn);


            // System.out.println("Segment Index: " + segmentIndex + " Velocity: " + segment.velocity);
            segmentIndex++;
        }
    }

    public void forwardPath() {
        if (hasRun){
            double left = leftFollower.calculate(drivetrain.getLeftEncoderTicks());
            double right = rightFollower.calculate(drivetrain.getRightEncoderTicks());
            double degreeNAVX = Pathfinder.r2d(drivetrain.getPose().getYaw());

            // System.out.println("left: " + left);
            // System.out.println("right: " + right);
            
            double desiredHeading = Pathfinder.r2d(leftFollower.getHeading());

            double angleDifference = Pathfinder.boundHalfDegrees(Pathfinder.boundHalfDegrees(desiredHeading) - degreeNAVX);

            System.out.println("Heading: " + Pathfinder.boundHalfDegrees(desiredHeading));
            System.out.println("NAVX Yaw: " + degreeNAVX);

            double turn = angleDifference / 180 * .5;

            drivetrain.setPercentVelocity(left - turn, right + turn);
            
            if (leftFollower.isFinished()){
                hasRun = false;
            }
        }
    }

    public void reversePath() {
        if (hasRun){
            double left = leftFollower.calculate(startLeftEncoderTick - (drivetrain.getRightEncoderTicks() - startLeftEncoderTick));
            double right = rightFollower.calculate(startRightEncoderTick - (drivetrain.getLeftEncoderTicks() - startRightEncoderTick));
            double degreeNAVX = Pathfinder.r2d(drivetrain.getPose().getYaw());

            // System.out.println("left: " + left);
            // System.out.println("right: " + right);
            
            double desiredHeading = Pathfinder.r2d(leftFollower.getHeading());

            double angleDifference = Pathfinder.boundHalfDegrees(Pathfinder.boundHalfDegrees(desiredHeading) - degreeNAVX);

            System.out.println("Heading: " + Pathfinder.boundHalfDegrees(desiredHeading));
            System.out.println("NAVX Yaw: " + degreeNAVX);

            double turn = angleDifference / 180 * .5;

            drivetrain.setPercentVelocity(-right - turn, -left + turn);

            if (leftFollower.isFinished()){
                hasRun = false;
            }
        }
    }

    public void cancelPath() {
        trajectory = null;
    }

    public void printNAVX(){
      
    }

    public void resetNAVX(){
        
    }

}
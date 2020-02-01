package frc.robot.autonomous;

import frc.robot.actuation.DifferentialDrive;
import frc.robot.geometry.Pose;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class MotionPlanner {

    Trajectory trajectory;
    DifferentialDrive drivetrain;
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

    public void moveTo(double x, double y, double angle) {

        Pose pose = drivetrain.getPose();
        Waypoint[] singleMoveTo = new Waypoint[]{
            new Waypoint(pose.getX(), pose.getX(), pose.getYaw()), new Waypoint(x, y, angle)
        };
        trajectory = Pathfinder.generate(singleMoveTo, config);
        segmentIndex = 0;
        System.out.println(trajectory.length());

    }

    public void followPath() { 
        // Matt says this might need to be trajectory.length()-1
        if (trajectory == null || segmentIndex == trajectory.length() - 1) {
            drivetrain.setTargetVelocity(0, 0);
        } else {
            Trajectory.Segment segment = trajectory.get(segmentIndex);
            Trajectory.Segment nextSegment = trajectory.get(segmentIndex + 1);
            double omegaZ = (nextSegment.heading - segment.heading)/.02;
            drivetrain.setTargetVelocity(segment.velocity, omegaZ);
            segmentIndex++;
        }
    }

    public void cancelPath() {
        trajectory = null;
    }

}

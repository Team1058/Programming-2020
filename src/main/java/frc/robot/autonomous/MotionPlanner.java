package frc.robot.autonomous;

import frc.robot.actuation.DifferentialDrive;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class MotionPlanner {

    Trajectory trajectory;
    DifferentialDrive drivetrain;
    Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, 0.02, 1, 1, 100);
    int segmentIndex = 0;

    public MotionPlanner(DifferentialDrive drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void moveTo(double x, double y, double angle) {

        Waypoint singleMoveTo = new Waypoint(x,y,angle);
        trajectory = Pathfinder.generate(new Waypoint[] {singleMoveTo}, config);
        segmentIndex = 0;

    }

    public void followPath(){ 
        // Matt says this might need to be trajectory.length()-1
        if (trajectory == null || segmentIndex == trajectory.length()) {
            drivetrain.setTargetVelocity(0, 0);
        } else {
            Trajectory.Segment segment = trajectory.get(segmentIndex);
            double omegaZ = (segment.heading - drivetrain.getPose().getYaw())/.02;
            drivetrain.setTargetVelocity(segment.velocity, omegaZ);
            segmentIndex++;
        }
    }

}

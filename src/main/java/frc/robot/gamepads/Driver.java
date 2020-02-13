package frc.robot.gamepads;

import org.opencv.core.Mat;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;
import frc.robot.subsystems.DriveTrainSubsystem;

/* Driver Controls
    - Tank Mode (Joysticks Y)
    - Moving on bar (Triggers) - hold
   Operator Controls
    - Spinning Stage 2 (X) - hold
    - Spinning Stage 3 (A) - hold
    - Extend Climber (Y) - hold
    - Retract Climber (B) - hold
    - Shoot (Right Trigger) - click
    - Start two shooting motors (Left trigger) - hold
    - Intake down and in (Left bumper) - hold
    - Intake up and out (right bumper) - hold
    - Shooting angle up/down (right joystick y)*/

public class Driver {
  
    private XboxController gamepad = new XboxController(0);
    private final double DEADBAND_VALUE = 0.025;
   
    public void splitArcadeDrive(){

        //sets the value for easier implementation
        double vX = -gamepad.getY(Hand.kLeft);
        vX *= Math.abs(vX);
        double omegaZ = -gamepad.getX(Hand.kRight);
        omegaZ *= Math.abs(omegaZ);
        SmartDashboard.putNumber("Left Joystick", vX);
        SmartDashboard.putNumber("Right Joystick", omegaZ);
        vX = clampDeadband(vX);
        omegaZ = clampDeadband(omegaZ);
        vX *= Robot.driveTrainSubsystem.drivetrain.getMaxVelocityX();
        omegaZ *= Robot.driveTrainSubsystem.drivetrain.getMaxOmegaZ();
        Robot.driveTrainSubsystem.setDrive(vX, omegaZ);

    }

    public void BarDriving()
    {
        // Gets values of each trigger
        double right = gamepad.getTriggerAxis(Hand.kRight);
        double left = gamepad.getTriggerAxis(Hand.kLeft);
        double difference=right-left;

        if (Math.abs(difference) > .05)
        {
            Robot.climberSubsystem.DriveBar(difference);
        } else
        {
            Robot.climberSubsystem.driveStop();
        }
    }

    private boolean outsideDeadband(double inputValue){
            
        return (Math.abs(inputValue) > DEADBAND_VALUE);
 
    }

    private double clampDeadband(double inputValue){
        if (outsideDeadband(inputValue)) {
            return inputValue;
        } else {
            return 0;
        }
    }

}

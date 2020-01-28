/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.gamepads;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
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
    private XboxController gamepad = new XboxController(1);
    private final double DEADBAND_VALUE = 0.075;
    
    
    /*public void splitArcadeDrive(){

        //sets the value for easier implementation
        double x = gamepad.getX(Hand.kLeft);
        double y = gamepad.getY(Hand.kRight);

        //if either stick is outside its deadband it sets the motor to the level of the controller
        if (outsideDeadband(x) || outsideDeadband(y)) {
            Robot.driveTrainSubsystem.setDrive(
                // ? is an if else statement
                outsideDeadband(x) ? x : 0,
                outsideDeadband(y) ? y : 0
            );
        } else {
            //if no deadband is broken it stops the motors
            Robot.driveTrainSubsystem.stopAll();
        }

    }*/

    public void BarDriving()
    {
        // Gets values of each trigger
        double right = gamepad.getTriggerAxis(Hand.kRight);
        double left = gamepad.getTriggerAxis(Hand.kLeft);

        // Rest of code to apply to climber
    }

    private boolean outsideDeadband(double inputValue){
            
        return (Math.abs(inputValue) > DEADBAND_VALUE);
 
    }
}

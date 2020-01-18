/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.gamepads;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Robot;


/**
 * Add your docs here.
 */
public class Gamepad {
    private XboxController gamepad = new XboxController(0);
    private final double DEADBAND_VALUE = 0.075;
    
    
    public void splitArcadeDrive(){

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

    }

    public void turnToColor(){
        if(gamepad.getAButton()){
            Robot.spinnerSubsystem.spinForStageThree();
        }else if(gamepad.getXButtonPressed()){
            Robot.spinnerSubsystem.setTrackedColor();
        }else if(gamepad.getXButton()){
            Robot.spinnerSubsystem.spinForStageTwo();
        }else if (gamepad.getXButtonReleased() || gamepad.getAButtonReleased()){
            Robot.spinnerSubsystem.resetColorChecks();
        }else {
            Robot.spinnerSubsystem.stopMotor();
        }

    }

    private boolean outsideDeadband(double inputValue){
            
        return (Math.abs(inputValue) > DEADBAND_VALUE);
 
    }
}

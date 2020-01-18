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
    
    
    public void arcadeDrive(){

        double x = gamepad.getX(Hand.kLeft);
        double y = gamepad.getY(Hand.kRight);

        if (outsideDeadband(x) || outsideDeadband(y)) {
            Robot.driveTrainSubsystem.setDrive(
                outsideDeadband(x) ? x : 0,
                outsideDeadband(y) ? y : 0
            );
        } else {
            Robot.driveTrainSubsystem.stopAll();
        }

    }

    public void turnToColor(){
        if(gamepad.getAButton()){
            Robot.spinnerSubsystem.spinTillColor(Robot.spinnerSubsystem.getGameColor());
        }else{
            Robot.spinnerSubsystem.stopMotor();
        }

    }

    private boolean outsideDeadband(double inputValue){
            
        return (Math.abs(inputValue) > 0.075);
 
    }
}

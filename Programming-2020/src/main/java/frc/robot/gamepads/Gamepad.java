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
    
    
    public void ArcadeDrive(){


        if (Math.abs(gamepad.getY(Hand.kRight)) > 0.05){

            System.out.println("Right Y: " + gamepad.getY(Hand.kRight));
            
            if (gamepad.getY(Hand.kRight) < 0){
                Robot.driveTrainSubsystem.setDrive(gamepad.getY(Hand.kRight),gamepad.getY(Hand.kRight));
            } else if (gamepad.getY(Hand.kRight) > 0){
                Robot.driveTrainSubsystem.setDrive(gamepad.getY(Hand.kRight), gamepad.getY(Hand.kRight));
            }

        }else{
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
}

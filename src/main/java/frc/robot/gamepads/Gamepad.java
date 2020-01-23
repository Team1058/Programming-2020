/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.gamepads;

import org.opencv.core.Mat;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrainSubsystem;


/**
 * Add your docs here.
 */
public class Gamepad {
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

    public void turnToColor(){
        if(gamepad.getAButton()){
            Robot.spinnerSubsystem.spinForStageThree();
        }else if(gamepad.getXButtonPressed()){
            Robot.spinnerSubsystem.setTrackedColor();
        }else if(gamepad.getXButton()){
            Robot.spinnerSubsystem.spinForStageTwo();
        }else {
            Robot.spinnerSubsystem.stopMotor();
        }
        if (gamepad.getXButtonReleased() || gamepad.getAButtonReleased()){
            Robot.spinnerSubsystem.resetColorChecks();
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

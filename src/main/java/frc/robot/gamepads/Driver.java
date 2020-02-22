package frc.robot.gamepads;

import java.util.Optional;

import org.opencv.core.Mat;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;
import frc.robot.actuation.DifferentialDrive;
import frc.robot.subsystems.ClimberSubsystem;
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
  
    private XboxController gamepad;
    private final double DEADBAND_VALUE = 0.025;
    private DriveTrainSubsystem drivetrain;
    private ClimberSubsystem climber;

    public Driver(int gamepadNum, DriveTrainSubsystem drivetrain) {
        this.drivetrain = drivetrain;
        gamepad = new XboxController(gamepadNum);
    }
   
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
        if (gamepad.getBumper(Hand.kLeft)) {
            vX *= 0.5;
            omegaZ *= 0.5;
        }
       
        drivetrain.setArcadeDrive(vX, omegaZ);
    }

    public void driveOnBar()
    {
        // Gets values of each trigger
        double right = gamepad.getTriggerAxis(Hand.kRight);
        double left = gamepad.getTriggerAxis(Hand.kLeft);
        double difference=right-left;

        if (Math.abs(difference) > .05) {
            climber.DriveBar(difference);
        } else {
            climber.driveStop();
        }
    }

    public void update() {
        if (gamepad.getXButton()) {
            Optional<Double> angleError = drivetrain.snapToTarget();
            if (angleError.isPresent()) {
                if (angleError.get() < 0) {
                    gamepad.setRumble(RumbleType.kRightRumble, 1);
                    gamepad.setRumble(RumbleType.kLeftRumble, 0);
                } else if (angleError.get() > 0) {
                    gamepad.setRumble(RumbleType.kRightRumble, 0);
                    gamepad.setRumble(RumbleType.kLeftRumble, 1);
                } else {
                    gamepad.setRumble(RumbleType.kRightRumble, 1);
                    gamepad.setRumble(RumbleType.kLeftRumble, 1);   
                }
            } else {
                gamepad.setRumble(RumbleType.kRightRumble, 0);
                gamepad.setRumble(RumbleType.kLeftRumble, 0);
            }
        } else {
            gamepad.setRumble(RumbleType.kRightRumble, 0);
            gamepad.setRumble(RumbleType.kLeftRumble, 0);
            splitArcadeDrive();
        }
        if (gamepad.getBackButtonPressed()) {
            drivetrain.resetOdometry();
        }

    }

    public void turnToTarget(){
        if(gamepad.getStartButton()){
            Robot.driveTrainSubsystem.snapToTargetV2();
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

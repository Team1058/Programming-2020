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
    - Split Arcade Mode (Left Joy - Y  Right Joy - X)
    - Moving on bar (Lt - left Rt - Right) - hold 
    - Climber Up (Y) - hold
    - Climber Down (A) - hold */
    
public class Driver {
  
    private XboxController gamepad;
    private final double DEADBAND_VALUE = 0.05;
    private DriveTrainSubsystem drivetrain;
    private ClimberSubsystem climber;

    boolean runOnce = true;

    public Driver(int gamepadNum, DriveTrainSubsystem drivetrain) {
        this.drivetrain = drivetrain;
        gamepad = new XboxController(gamepadNum);
    }
   
    public void tankDrive() {
        double leftJoystickY = clampDeadband(gamepad.getY(Hand.kLeft));
        double rightJoystickY = clampDeadband(gamepad.getY(Hand.kRight));

        if (outsideDeadband(gamepad.getTriggerAxis(Hand.kLeft))){
            leftJoystickY = (rightJoystickY + leftJoystickY) / 2;
            rightJoystickY = leftJoystickY;
        }


        if (gamepad.getBumper(Hand.kRight)) {
            leftJoystickY *= .5;
            rightJoystickY *= .5;
        } else if (gamepad.getBumper(Hand.kLeft)) {
            leftJoystickY *= .25;
            rightJoystickY *= .25;
        }

        drivetrain.getDrivetrain().setPercentVelocity(leftJoystickY, rightJoystickY);
    }

    public void VideoGameDrive() {

        double rightTrigger = -clampDeadband(gamepad.getTriggerAxis(Hand.kRight));
        double leftTrigger = -clampDeadband(gamepad.getTriggerAxis(Hand.kLeft));

        double speed = rightTrigger - leftTrigger;
        double turn = clampDeadband(gamepad.getX(Hand.kLeft));

        drivetrain.setArcadeDrive(speed, turn);

    }
    
    public void splitArcadeDrive() {

        //sets the value for easier implementation
        if(outsideDeadband(gamepad.getY(Hand.kLeft)) || outsideDeadband(gamepad.getX(Hand.kRight))) {
            double vX = gamepad.getY(Hand.kLeft);
            double omegaZ = gamepad.getX(Hand.kRight);
            SmartDashboard.putNumber("Left Joystick", vX);
            SmartDashboard.putNumber("Right Joystick", omegaZ);
            vX *= Robot.driveTrainSubsystem.drivetrain.getMaxVelocityX();
            omegaZ *= Robot.driveTrainSubsystem.drivetrain.getMaxOmegaZ();
            if (gamepad.getBumper(Hand.kRight)) {
                vX *= 0.5;
                omegaZ *= 0.5;
            }else if (gamepad.getBumper(Hand.kLeft)){
                vX *= .25;
                omegaZ *= .25;
            }
        
            drivetrain.setArcadeDrive(vX, omegaZ);
        }else {
            drivetrain.setArcadeDrive(0,0);
        }
    }

    public void driveOnBar() {
        // Gets values of each trigger
        double move = gamepad.getX(Hand.kRight);

        if (Math.abs(move) > .05) {
            //climber.DriveBar(move);
        } else {
            //climber.driveStop();
        }
    }

    
    public void climber() {
        double multiplier = 1.0;

        if (gamepad.getBumper(Hand.kLeft)){
            multiplier = .25;
        }else if(gamepad.getBumper(Hand.kRight)){
            multiplier = .5;
        }

        if (gamepad.getYButton()) {
            Robot.climberSubsystem.climberExtend(1 * multiplier);
        } else if (gamepad.getAButton()) {
            Robot.climberSubsystem.climberRetract(1 * multiplier);
        } else {
            Robot.climberSubsystem.climberStop();
        }

        if (gamepad.getBButton()) {
            Robot.climberSubsystem.lockRatchet();
        }else{
            Robot.climberSubsystem.resetClimberServo();
        }
    }

    public void update() {
        if (gamepad.getXButton()) {
            Robot.driveTrainSubsystem.snapToTargetV2();
            if (Robot.driveTrainSubsystem.snapToTargetV2()){
                gamepad.setRumble(RumbleType.kLeftRumble, .5);
                gamepad.setRumble(RumbleType.kRightRumble, .5);
            }else{   
                gamepad.setRumble(RumbleType.kLeftRumble, 0);
                gamepad.setRumble(RumbleType.kRightRumble, 0);
            }
        } else {
            gamepad.setRumble(RumbleType.kLeftRumble, 0);
            gamepad.setRumble(RumbleType.kRightRumble, 0);
            VideoGameDrive();
            //splitArcadeDrive();
            //tankDrive();
        }

        if (gamepad.getBackButtonPressed()) {
            drivetrain.resetOdometry();
        }
    }

    private boolean outsideDeadband(double inputValue) {
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
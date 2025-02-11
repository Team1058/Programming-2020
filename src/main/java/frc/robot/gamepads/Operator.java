package frc.robot.gamepads;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.ShooterSubsystem;

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

public class Operator {
  
    private XboxController gamepad = new XboxController(1);

    private final double DEADBAND_VALUE = 0.5;

    public void Feed() {

        if (gamepad.getAButton() && triggerDeadband(gamepad.getTriggerAxis(Hand.kRight))) {
            Robot.shooterSubsystem.autoFeed = true;
        } else if (gamepad.getAButton() && triggerDeadband(gamepad.getTriggerAxis(Hand.kLeft))) {
            Robot.shooterSubsystem.autoFeed = false;
            Robot.shooterSubsystem.fireAtCommand();
        } else if (gamepad.getAButtonReleased()){
            Robot.shooterSubsystem.stopFeeder();
        } else if(gamepad.getBumper(Hand.kLeft)) {
            Robot.shooterSubsystem.reverseFeeder();
        } else {
            Robot.shooterSubsystem.autoFeed = false;
        }
    }

    public void Intake() {
        if (gamepad.getBumper(Hand.kRight)) {
            Robot.intakeSubsystem.intakeGoDown();           
        } else if (gamepad.getBumperReleased(Hand.kRight)) {
            Robot.intakeSubsystem.intakeGoUp();
        }
    }

    public void BallPath(){
        if (gamepad.getBumper(Hand.kRight) || gamepad.getAButton()){
            Robot.ballPath.ballsToShooter();
        } else if (gamepad.getBumper(Hand.kLeft)){
            Robot.ballPath.ballsToIntake();
        } else {
            Robot.ballPath.stopBalls();
        }
    }

    public void toggleLed(){
        if (gamepad.getStickButtonPressed(Hand.kRight)){         
            Robot.limelight.toggleLed();
        }
    }

    // public void Spinning() {
    //     if (gamepad.getAButton()) {
    //         Robot.spinnerSubsystem.spinForStageThree();
    //     } else if (gamepad.getXButtonPressed()) {
    //         Robot.spinnerSubsystem.setTrackedColor();
    //     } else if (gamepad.getXButton()) {
    //         Robot.spinnerSubsystem.spinForStageTwo();
    //     } else {
    //         Robot.spinnerSubsystem.stopMotor();
    //     }

    //     if (gamepad.getXButtonReleased() || gamepad.getAButtonReleased()) {
    //         Robot.spinnerSubsystem.resetColorChecks();
    //     }
    // }
    boolean ran = false;
    double staticDistance;

    public void changeShooterState() {
        if (triggerDeadband(gamepad.getTriggerAxis(Hand.kLeft))) {
            Robot.shooterSubsystem.manualDisableStateMachine();
            //This equation gets the rpm (We got this equation using point 1 as .1,2000 and point 2 as 1,3950)
            double rpm = 2166.666 * gamepad.getTriggerAxis(Hand.kLeft) + 1783.334;
            rpm = Robot.shooterSubsystem.distanceToRPMMaxHood(156);
            Robot.shooterSubsystem.manualFlywheel(rpm);
            if (Robot.shooterSubsystem.manualAtSpeed(rpm)){
                Robot.individualLeds.changeAllColors(0,0,255);
            }else{
                Robot.individualLeds.changeAllColors(255, 255, 255);
            }
        } else if (triggerDeadband(gamepad.getTriggerAxis(Hand.kRight))) {
            Robot.shooterSubsystem.enable();
            if (Robot.shooterSubsystem.hoodAtMax()) {
                Robot.shooterSubsystem.setSpeed(Robot.shooterSubsystem.distanceToRPMMaxHood(Robot.limelight.getSimpleDistance()));

                SmartDashboard.putNumber("Ideal RPM", Robot.shooterSubsystem.distanceToRPMMaxHood(Robot.limelight.getSimpleDistance()));
            } else {
                Robot.shooterSubsystem.setSpeed(Robot.shooterSubsystem.distanceToRPMMaxHood(Robot.limelight.getSimpleDistance()));
                //Robot.shooterSubsystem.setSpeed(Robot.shooterSubsystem.distanceToRPMMinHood(Robot.limelight.getSimpleDistance()));
            }
            
        } else {
            Robot.shooterSubsystem.disable();      
        }

        if (!triggerDeadband(gamepad.getTriggerAxis(Hand.kLeft)) && !triggerDeadband(gamepad.getTriggerAxis(Hand.kRight))){
            Robot.individualLeds.red();
        }
    }

    public void shooterHoodPosition() {
        if ((gamepad.getPOV() >= 315 && gamepad.getPOV() <= 360) || 
            (gamepad.getPOV() >= 0 && gamepad.getPOV() <= 45)) {
                Robot.shooterSubsystem.shooterHoodExtend();
        } else if (gamepad.getPOV() >= 135 && gamepad.getPOV() <= 225) {
                Robot.shooterSubsystem.shooterHoodRetract();
        }
    }

    public void rumbleOn(){
        gamepad.setRumble(RumbleType.kLeftRumble, .5);
        gamepad.setRumble(RumbleType.kRightRumble, .5);
    } 

    public void rumbleOff(){
        gamepad.setRumble(RumbleType.kLeftRumble, 0);
        gamepad.setRumble(RumbleType.kRightRumble, 0);
    }

    private boolean outsideDeadband(double inputValue) {
        return (Math.abs(inputValue) > DEADBAND_VALUE);
    }

    private boolean triggerDeadband(double inputValue){
        return (Math.abs(inputValue) > 0);
    }

}

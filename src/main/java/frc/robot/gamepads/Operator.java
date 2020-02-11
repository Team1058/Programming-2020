package frc.robot.gamepads;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
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

    private final double DEADBAND_VALUE = 0.075;

    public void Climber()
    {
        if (gamepad.getYButton())
        {
            // Code to extend climber
        } 
        else if (gamepad.getBButton())
        {
            // Code to retract climber
        }
        else
        {
            // Does nothing
        }
    }

    public void Shoot()
    {
        if (gamepad.getTriggerAxis(Hand.kRight)!=0)
        {
            // Code to shoot
        }
        else
        {
            // Does nothing
        }
    }

    public void SpinShootMotors()
    {
        if (gamepad.getTriggerAxis(Hand.kLeft)!=0)
        {
            // spins shooting motors
        }
        else
        {
            // Does nothing
        }
    }

    public void Intake()
    {
        if (gamepad.getBumper(Hand.kLeft))
        {
            // Code to intake
        }
        else if (gamepad.getBumper(Hand.kRight))
        {
            // code to outttake
        }
        else
        {
            // stops wheels
        }
    }

    public void ShootingAngle()
    {
        // Gets values of each joystick
        double change = gamepad.getY(Hand.kRight);

        // Rest of code to apply to shoooting angle
    }

    public void Spinning(){
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

    public void shooterHoodPosition() {
        System.out.println(gamepad.getPOV());
        if ((gamepad.getPOV() >= 315 && gamepad.getPOV() <= 360) || 
            (gamepad.getPOV() >= 0 && gamepad.getPOV() <= 45)) {
                Robot.shooterSubsystem.shooterExtendHood();
        } else if (gamepad.getPOV() >= 135 && gamepad.getPOV() <= 225) {
                Robot.shooterSubsystem.shooterRetractHood();
        }
    }

    private boolean outsideDeadband(double inputValue){
            
        return (Math.abs(inputValue) > DEADBAND_VALUE);
 
    }
}

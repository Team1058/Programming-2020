package frc.robot.gamepads;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Robot;

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
            Robot.climberSubsystem.climberExtend();
        } 
        else if (gamepad.getAButton())
        {
            Robot.climberSubsystem.climberRetract();
        }
        else
        {
            Robot.climberSubsystem.climberStop();
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
        if (gamepad.getBumper(Hand.kLeft)){
            Robot.intakeSubsystem.intakeBalls(-1);
        }else if (gamepad.getBumper(Hand.kRight)){
            Robot.intakeSubsystem.intakeSpit(.5);
        }else{
            Robot.intakeSubsystem.intakeSpit(0);
        }
    }

    public void ShootingAngle()
    {
        // Gets values of each joystick
        double change = gamepad.getY(Hand.kRight);

        // Rest of code to apply to shoooting angle
    }

    public void Spinning(){
        //TODO UNCOMMENT AND CHANGE BUTTON MAPPING IF WE HAVE A SPINNER
        // if(gamepad.getAButton()){
        //     Robot.spinnerSubsystem.spinForStageThree();
        // }else if(gamepad.getXButtonPressed()){
        //     Robot.spinnerSubsystem.setTrackedColor();
        // }else if(gamepad.getXButton()){
        //     Robot.spinnerSubsystem.spinForStageTwo();
        // }else {
        //     Robot.spinnerSubsystem.stopMotor();
        // }
        // if (gamepad.getXButtonReleased() || gamepad.getAButtonReleased()){
        //     Robot.spinnerSubsystem.resetColorChecks();
        // }
    }

    private boolean outsideDeadband(double inputValue){
            
        return (Math.abs(inputValue) > DEADBAND_VALUE);
 
    }
}

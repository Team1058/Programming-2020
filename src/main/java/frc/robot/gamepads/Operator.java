package frc.robot.gamepads;

import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.revrobotics.CANDigitalInput.LimitSwitch;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Robot;
import frc.robot.subsystems.IntakeSubsystem;

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

    IntakeSubsystem intakeSubsystem;

    public double FwdLimit = Robot.intakeSubsystem.intakeLift.isFwdLimitSwitchClosed();
    public double RevLimit = Robot.intakeSubsystem.intakeLift.isRevLimitSwitchClosed();
    public boolean UpDown = true;
    public double BallSpeed = 0.5;
    double LiftSlow = 0.1;
    double LiftFast = 0.5;
    double DropSlow = -0.1;
    double DropFast = -0.5;

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
    public void Intake() {
        if (UpDown == true && gamepad.getBumper(Hand.kRight) && Robot.intakeSubsystem.intakeLift.isFwdLimitSwitchClosed() == 0 )
        {
            UpDown = false;
        }
        else if(UpDown == false && gamepad.getBumper(Hand.kRight) && Robot.intakeSubsystem.intakeLift.isRevLimitSwitchClosed() == 0)
        {
            UpDown = true;
        }
     //  System.out.println(UpDown);   
     // limit switches default to 1 when not pressed
     // fwd = green wire = left bumper
     // rev = white wire = right bumper
     //this keeps intake up mapped to left bumper
        if (Robot.intakeSubsystem.intakeLift.isFwdLimitSwitchClosed() == 0 && UpDown == true) // If the forward limit switch is pressed, we want to keep the values between -1 and 0
        {
            Robot.intakeSubsystem.liftIntake(LiftSlow);
        }
        else if(Robot.intakeSubsystem.intakeLift.isFwdLimitSwitchClosed()==1 && UpDown == true) // if the limit switch is open and the bumper is pressed then the motor gets more power
        {
            Robot.intakeSubsystem.liftIntake(LiftFast);
        }
        else if(Robot.intakeSubsystem.intakeLift.isRevLimitSwitchClosed() == 0 && UpDown == false) // If the reversed limit switch is pressed, we want to keep the values between 0 and 1
        { 
           Robot.intakeSubsystem.liftIntake(DropSlow);
           Robot.intakeSubsystem.intakeBalls(BallSpeed);
        }   
        else if(Robot.intakeSubsystem.intakeLift.isRevLimitSwitchClosed() == 1 && UpDown == false) // if the limit switch is open and the bumper is pressed then the motor gets more power
        {
            Robot.intakeSubsystem.liftIntake(DropFast);
            Robot.intakeSubsystem.intakeBalls(BallSpeed);
        }
        else
        {
            Robot.intakeSubsystem.intakeOff();
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

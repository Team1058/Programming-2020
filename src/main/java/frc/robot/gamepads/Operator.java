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

/* Operator Controls
    - Auto flywheel and hood (RT) - hold
    - Manual flywheel (LT) - hold
    - Manual hood (left joystick y)
    - Feed (A) - hold
    - Intake up (X) - click
    - Intake down (B) - click
    - Intake wheels (RB) - hold */
    

public class Operator {

  
    private XboxController gamepad = new XboxController(1);
    private final double DEADBAND_VALUE = 0.075;

    public void update() {
        intake();
    }





    public void AutoFlywheelAndHood(){

        if (gamepad.getTriggerAxis(Hand.kRight) > 0.1){

        }else{

        }

    }

    public void Feed()
    {
        if (gamepad.getAButton()){
            
            // Code to shoot
        } else {
            // Does nothing
        }
    }

    public void SpinManualFlywheels() {
        if (gamepad.getTriggerAxis(Hand.kLeft)> 0.1) {
            // spins shooting motors
        } else {
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
            Robot.intakeSubsystem.intakeBalls(0);
        }
        else if(Robot.intakeSubsystem.intakeLift.isFwdLimitSwitchClosed()==1 && UpDown == true) // if the limit switch is open and the bumper is pressed then the motor gets more power
        {
            Robot.intakeSubsystem.liftIntake(LiftFast);
            Robot.intakeSubsystem.intakeBalls(0);
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
     
   public void ManualHood(){
        // Gets values of each joystick
        
        if (outsideDeadband(gamepad.getY(Hand.kLeft))){

        }else{

        }

        // Rest of code to apply to shoooting angle
    }

    public void ControlPanelSpins(){
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

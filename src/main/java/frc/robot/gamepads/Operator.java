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
import frc.robot.subsystems.ShooterSubsystem;

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

    public double FwdLimit = Robot.intakeSubsystem.intakeLift.isFwdLimitSwitchClosed();
    public double RevLimit = Robot.intakeSubsystem.intakeLift.isRevLimitSwitchClosed();
    public boolean UpDown = true;
    public boolean useAuto = true;
    public double BallSpeed = 0.5;
    double LiftSlow = 0.1;
    double LiftFast = 0.5;
    double DropSlow = -0.1;
    double DropFast = -0.5;

    public void AutoFlywheelAndHood(){

        if (gamepad.getTriggerAxis(Hand.kRight) > 0.1){
            useAuto = true;
            // Spins flywheel and moves hood to apropriate positions bases on distance using limelight
        }else{
            // Stops the auto
        }

    }

    public void Feed()
    {
        if (gamepad.getAButton() && useAuto){
            // Uses the auto feeding function
        } else if(gamepad.getAButton() && !useAuto){
            // Enables the feeder motor 
        } else {
            // Stops feeding balls
        } 
    }

    public void SpinManualFlywheels() {
        if (gamepad.getTriggerAxis(Hand.kLeft)> 0.1) {
            useAuto = false;
            //spins the flywheel at triggeraxix * 3950
        } else {
            // Does nothing
            Robot.shooterSubsystem.fireOff();
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
        
        if (outsideDeadband(gamepad.getY(Hand.kLeft))){
            // Moves the hood by using the thumbstick
        }else{
            // Stops moving the hood
        }
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

    public void changeShooterState(){
        
        if(gamepad.getTriggerAxis(Hand.kRight) > 0.5){
            Robot.shooterSubsystem.enable();
        }else{
            Robot.shooterSubsystem.disable();
        }
    }

    public void shooterHoodPosition() {
        System.out.println(gamepad.getPOV());
        if ((gamepad.getPOV() >= 315 && gamepad.getPOV() <= 360) || 
            (gamepad.getPOV() >= 0 && gamepad.getPOV() <= 45)) {
                Robot.shooterSubsystem.shooterHoodExtend();
        } else if (gamepad.getPOV() >= 135 && gamepad.getPOV() <= 225) {
                Robot.shooterSubsystem.shooterHoodRetract();
        }
    }

    private boolean outsideDeadband(double inputValue){
            
        return (Math.abs(inputValue) > DEADBAND_VALUE);
 
    }
}

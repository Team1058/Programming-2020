package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SpinnerSubsystem;
import frc.robot.gamepads.Driver;
import frc.robot.gamepads.Operator;
import frc.robot.sensing.Limelight;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.gamepads.*;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IndividualLeds;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import frc.robot.subsystems.ShooterSubsystem;

public class Robot extends TimedRobot {

  public static SpinnerSubsystem spinnerSubsystem = new SpinnerSubsystem();
  public static LEDSubsystem ledSubsystem = new LEDSubsystem();
  public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
  public static IndividualLeds individualLeds = new IndividualLeds();
  public static ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  public static Driver driverGP = new Driver();
  public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public static Operator operatorGP = new Operator();
  public boolean UpDown = true;
  public static Limelight limelight = new Limelight();
  public static ShooterSubsystem shooterSubsystem = new ShooterSubsystem(); 

  @Override
  public void robotInit() {
    spinnerSubsystem.initialize();
  // driveTrainSubsystem.initialize();
    climberSubsystem.initialize();
    intakeSubsystem.initialize();
    CameraServer.getInstance().startAutomaticCapture();
  }

  @Override
  public void teleopInit() {
    intakeSubsystem.inferState();
    SmartDashboard.putNumber("SHOOTER_SPEED", 0);
    SmartDashboard.putNumber("servo_SPEED", 0);

  }


  @Override
  public void disabledInit() {
    shooterSubsystem.disable();
  }

  @Override
  public void teleopPeriodic() {
    shooterSubsystem.tuneShooterFromDashboard();
    driverGP.splitArcadeDrive();
    driverGP.BarDriving();
    driverGP.Climber();
    operatorGP.Intake();
    
   // shooterSubsystem.tuneShooterFromDashboard();
   // driverGP.splitArcadeDrive();
   int servoRPM = (int)SmartDashboard.getNumber("servo_SPEED", 0);
   operatorGP.shooterHoodPosition(servoRPM);

  } 

  @Override
  public void robotPeriodic() {
    shooterSubsystem.runStateMachine();
    limelight.update();
    intakeSubsystem.updateIntake();
  }

  @Override
  public void disabledPeriodic() {
    individualLeds.changeAllColors(0,0,0);    
    //opetsetRumble​(GenericHID.RumbleType type, double value)
  }

}
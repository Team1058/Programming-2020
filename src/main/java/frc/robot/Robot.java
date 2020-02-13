package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SpinnerSubsystem;
import frc.robot.gamepads.Driver;
import frc.robot.gamepads.Operator;
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

  public static Operator operatorGP = new Operator();
  public static Driver driverGP = new Driver();



  @Override
  public void robotInit() {
    spinnerSubsystem.initialize();
  // driveTrainSubsystem.initialize();
    climberSubsystem.initialize();
    intakeSubsystem.initialize();
    CameraServer.getInstance().startAutomaticCapture();
    SmartDashboard.putNumber("SHOOTER_SPEED", 0);

  }

  @Override
  public void teleopInit() {
    intakeSubsystem.inferState();
  }
  public void disabledInit() {
    shooterSubsystem.disable();
  }

  @Override
  public void robotPeriodic() {
    limelight.update();
    intakeSubsystem.updateIntake();
    shooterSubsystem.runStateMachine();
  }

  @Override
  public void teleopPeriodic() {
    driverGP.splitArcadeDrive();
    driverGP.BarDriving();
    driverGP.Climber();
    operatorGP.Intake();
    operatorGP.shooterHoodPosition();
   // shooterSubsystem.tuneShooterFromDashboard();
   double shooterRPM = SmartDashboard.getNumber("SHOOTER_SPEED", 0);
   Robot.shooterSubsystem.setSpeed(shooterRPM);
  } 


  @Override
  public void disabledPeriodic() {
    individualLeds.changeAllColors(0,0,0);
  }

}
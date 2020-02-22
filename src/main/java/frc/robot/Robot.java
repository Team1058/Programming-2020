package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SpinnerSubsystem;
import frc.robot.gamepads.Driver;
import frc.robot.gamepads.Operator;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IndividualLeds;
import frc.robot.subsystems.ShooterSubsystem;

public class Robot extends TimedRobot {

  public static SpinnerSubsystem spinnerSubsystem = new SpinnerSubsystem();
  public static LEDSubsystem ledSubsystem = new LEDSubsystem();
  public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
  public static IndividualLeds individualLeds = new IndividualLeds();
  public static ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  public static ShooterSubsystem shooterSubsystem = new ShooterSubsystem(); 

  public static Operator operatorGP = new Operator();
  public static Driver driverGP = new Driver();

  @Override
  public void robotInit() {
    spinnerSubsystem.initialize();
  // driveTrainSubsystem.initialize();
    climberSubsystem.initialize();
    SmartDashboard.putNumber("SHOOTER_SPEED", 0);

  }

  @Override
  public void disabledInit() {
    shooterSubsystem.disable();
  }

  @Override
  public void teleopPeriodic() {
   // shooterSubsystem.tuneShooterFromDashboard();
   // driverGP.splitArcadeDrive();
   double shooterRPM = SmartDashboard.getNumber("SHOOTER_SPEED", 0);
   Robot.shooterSubsystem.setSpeed(shooterRPM);
   operatorGP.changeShooterState();
   operatorGP.shooterHoodPosition();
   operatorGP.Feed();

  } 

  @Override
  public void robotPeriodic() {
    shooterSubsystem.runStateMachine();
  }

  @Override
  public void disabledPeriodic() {
    individualLeds.changeAllColors(0,0,0);    
    //opetsetRumble​(GenericHID.RumbleType type, double value)
  }

}
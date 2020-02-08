package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
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
    driveTrainSubsystem.initialize();
    climberSubsystem.initialize();
  }

  @Override
  public void disabledInit() {
    shooterSubsystem.disable();
  }

  @Override
  public void teleopPeriodic() {
    shooterSubsystem.tuneShooterFromDashboard();
    driverGP.splitArcadeDrive();
    operatorGP.shooterHoodPosition();

    // TODO: Figure out how we want to dispatch commands

  } 

  @Override
  public void robotPeriodic() {
    shooterSubsystem.runStateMachine();
  }

  @Override
  public void disabledPeriodic() {
    individualLeds.changeAllColors(0,0,0);
  }

}
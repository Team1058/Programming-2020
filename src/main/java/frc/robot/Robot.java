package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SpinnerSubsystem;
import frc.robot.gamepads.Driver;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.gamepads.*;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IndividualLeds;

public class Robot extends TimedRobot {

  public static SpinnerSubsystem spinnerSubsystem = new SpinnerSubsystem();
  public static LEDSubsystem ledSubsystem = new LEDSubsystem();
  public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
  public static IndividualLeds individualLeds = new IndividualLeds();
  public static ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  public static Driver driverGP = new Driver();
  public static Operator operatorGP = new Operator();


  @Override
  public void robotInit() {
    spinnerSubsystem.initialize();
    driveTrainSubsystem.initialize();
    climberSubsystem.initialize();
    SmartDashboard.putNumber("R1", 0);
    SmartDashboard.putNumber("G1", 0);
    SmartDashboard.putNumber("B1", 0);
  }

  @Override
  public void teleopPeriodic() {
    int R1 = (int) SmartDashboard.getNumber("R1", 0);
    int G1 = (int) SmartDashboard.getNumber("G1", 0);
    int B1 = (int) SmartDashboard.getNumber("B1", 0);
    individualLeds.changeAllColors(R1,G1,B1);
    driverGP.splitArcadeDrive();
    driverGP.BarDriving();
    operatorGP.Climber();
    // TODO: Figure out how we want to dispatch commands
  } 

  @Override
  public void disabledPeriodic(){
    individualLeds.changeAllColors(0,0,0);
  }

}
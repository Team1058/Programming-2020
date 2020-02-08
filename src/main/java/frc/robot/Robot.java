package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
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
  }

  @Override
  public void teleopPeriodic() {
    
    driverGP.splitArcadeDrive();
    operatorGP.Climber();
    climberSubsystem.printFalconsPos();
    // TODO: Figure out how we want to dispatch commands
  } 

  @Override
  public void disabledPeriodic(){
    individualLeds.changeAllColors(0,0,0);
  }

}
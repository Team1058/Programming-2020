package frc.robot;

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

public class Robot extends TimedRobot {

  public static SpinnerSubsystem spinnerSubsystem = new SpinnerSubsystem();
  public static LEDSubsystem ledSubsystem = new LEDSubsystem();
  public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
  public static IndividualLeds individualLeds = new IndividualLeds();
  public static ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  public static Driver driverGP = new Driver();
  public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public static Operator operatorGP = new Operator();
  public XboxController gamepad = new XboxController(1);
  public boolean UpDown = true;

  @Override
  public void robotInit() {
    spinnerSubsystem.initialize();
    driveTrainSubsystem.initialize();
    climberSubsystem.initialize();
    intakeSubsystem.initialize();
  }

  @Override
  public void teleopPeriodic() {
    driverGP.splitArcadeDrive();
    driverGP.BarDriving();
    operatorGP.Climber();
    operatorGP.Intake();
    if (UpDown == true && gamepad.getBumper(Hand.kRight) && Robot.intakeSubsystem.intakeLift.isFwdLimitSwitchClosed() == 0 )
        {
            UpDown = false;
        }
        else if(UpDown == false && gamepad.getBumper(Hand.kRight) && Robot.intakeSubsystem.intakeLift.isRevLimitSwitchClosed() == 0)
        {
            UpDown = true;
        }
    System.out.println(UpDown);    
  } 

  @Override
  public void disabledPeriodic(){
    individualLeds.changeAllColors(0,0,0);
  }

}
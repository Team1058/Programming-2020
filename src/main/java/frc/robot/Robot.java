package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SpinnerSubsystem;
import frc.robot.gamepads.Driver;
import frc.robot.gamepads.Operator;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IndividualLeds;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.sensing.Limelight;

public class Robot extends TimedRobot {

  public static SpinnerSubsystem spinnerSubsystem = new SpinnerSubsystem();
  public static LEDSubsystem ledSubsystem = new LEDSubsystem();
  public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
  public static ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  public static ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public static Operator operatorGP = new Operator();
  public static Driver driverGP = new Driver();
  public static Limelight limelight = new Limelight();

  // TODO: Can add back in once LEDs are on robot
  //public static IndividualLeds individualLeds = new IndividualLeds();

  @Override
  public void robotInit() {
    spinnerSubsystem.initialize();
    driveTrainSubsystem.initialize();
    climberSubsystem.initialize();
    intakeSubsystem.initialize();
    SmartDashboard.putNumber("SHOOTER_SPEED", 2000);
    CameraServer.getInstance().startAutomaticCapture();
  }

  @Override
  public void disabledInit() {
    shooterSubsystem.disable();
  }

  @Override
  public void teleopPeriodic() {
   driverGP.splitArcadeDrive();
   //driverGP.BarDriving();
   driverGP.Climber();

   //shooterSubsystem.tuneShooterFromDashboard();
   double shooterRPM = SmartDashboard.getNumber("SHOOTER_SPEED", 0);
   Robot.shooterSubsystem.setSpeed(shooterRPM);
   operatorGP.Feed();
   operatorGP.Intake();
   operatorGP.changeShooterState();
   operatorGP.shooterHoodPosition();

  } 

  @Override
  public void robotPeriodic() {
    limelight.update();
    // TODO: Figure out why this auto turns on intake wheels
    //intakeSubsystem.updateIntake();
    shooterSubsystem.runStateMachine();
  }

  @Override
  public void teleopInit() {
    // TOOD: Might need to call this earlier
    intakeSubsystem.inferState();
  }

  @Override
  public void disabledPeriodic() {
    //TODO: Can add back in once LEDs are on robot
    //individualLeds.changeAllColors(0,0,0);
  }

}
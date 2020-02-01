package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SpinnerSubsystem;
import frc.robot.actuation.DifferentialDrive;
import frc.robot.autonomous.MotionPlanner;
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
  public static DriveTrainSubsystem driveTrainSubsystem;
  public static ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  public static ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public static Operator operatorGP = new Operator();
  public static Driver driverGP ;

  public static Limelight limelight = new Limelight();
  public static MotionPlanner motionPlanner;

  public boolean UpDown = true;


  // TODO: Can add back in once LEDs are on robot
  //public static IndividualLeds individualLeds = new IndividualLeds();

  @Override
  public void robotInit() {
    spinnerSubsystem.initialize();
    driveTrainSubsystem = new DriveTrainSubsystem(limelight);
    climberSubsystem.initialize();
    intakeSubsystem.initialize();
    SmartDashboard.putNumber("SHOOTER_SPEED", 2000);
    CameraServer.getInstance().startAutomaticCapture();
    motionPlanner = new MotionPlanner(driveTrainSubsystem.getDrivetrain());
    driverGP = new Driver(0, driveTrainSubsystem);
  }
  
  
  @Override
  public void teleopPeriodic() {
   driverGP.splitArcadeDrive();
   //driverGP.BarDriving();
   driverGP.Climber();
   motionPlanner.printNAVX();
   shooterSubsystem.tuneShooterFromDashboard();
   double shooterRPM = SmartDashboard.getNumber("SHOOTER_SPEED", 0);
   Robot.shooterSubsystem.setSpeed(shooterRPM);
   operatorGP.Feed();
   operatorGP.Intake();
   driverGP.update();
   operatorGP.changeShooterState();
   operatorGP.shooterHoodPosition();

  } 

  @Override
  public void autonomousInit() {
    super.autonomousInit();
    Robot.driveTrainSubsystem.getDrivetrain().resetOdometry();
    motionPlanner.resetNAVX();
    motionPlanner.moveTo(.5, 0, 0, false);
  }

  @Override
  public void autonomousPeriodic() {
    super.autonomousPeriodic();
    motionPlanner.reversePath();
    if (!Robot.motionPlanner.hasRun && Robot.driveTrainSubsystem.snapToTargetV2()) {
      System.out.println("READY TO SHOOT");
    }
  }

  @Override
  public void disabledInit() {
    shooterSubsystem.disable();
    motionPlanner.cancelPath();
  }

  @Override
  public void teleopInit() {
    // TOOD: Might need to call this earlier
    intakeSubsystem.inferState();

  }

  @Override
  public void robotPeriodic() {
    super.robotPeriodic();
    driveTrainSubsystem.update();
    limelight.update();
    shooterSubsystem.runStateMachine();
    intakeSubsystem.updateIntake();
  }

  @Override
  public void disabledPeriodic(){
    // individualLeds.changeAllColors(0,0,0);
    driveTrainSubsystem.getDrivetrain().setTargetVelocity(0, 0);
  }

}
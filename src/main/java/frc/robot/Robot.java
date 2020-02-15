package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SpinnerSubsystem;
import frc.robot.actuation.DifferentialDrive;
import frc.robot.autonomous.MotionPlanner;
import frc.robot.gamepads.Driver;
import frc.robot.gamepads.Operator;
import frc.robot.sensing.Limelight;
import frc.robot.gamepads.TestGP;
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
  public static DriveTrainSubsystem driveTrainSubsystem;
  public static IndividualLeds individualLeds = new IndividualLeds();
  public static ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

  public static Driver driverGP;
  public static Operator operatorGP = new Operator();
  public static TestGP testGP;

  public static Limelight limelight = new Limelight();
  public static MotionPlanner motionPlanner;

  public boolean UpDown = true;


  @Override
  public void robotInit() {
    spinnerSubsystem.initialize();
    driveTrainSubsystem = new DriveTrainSubsystem(limelight);
    climberSubsystem.initialize();
    intakeSubsystem.initialize();
    CameraServer.getInstance().startAutomaticCapture();
    motionPlanner = new MotionPlanner(driveTrainSubsystem.getDrivetrain());
    testGP = new TestGP(5, motionPlanner);
    driverGP = new Driver(0, driveTrainSubsystem);
  }
  
  @Override
  public void teleopPeriodic() {
    operatorGP.Climber();
    operatorGP.Intake();

    if (testGP.isTestGPEnabled()) {
      testGP.testDrive();
    } else {
      driverGP.update();
    }
    motionPlanner.printNAVX();
  }

  @Override
  public void robotPeriodic() {
    super.robotPeriodic();
    driveTrainSubsystem.update();
    limelight.update();
  }

  @Override
  public void disabledInit() {
    motionPlanner.cancelPath();
  }

  @Override
  public void disabledPeriodic(){
    individualLeds.changeAllColors(0,0,0);
    driveTrainSubsystem.getDrivetrain().setTargetVelocity(0, 0);
  }

}
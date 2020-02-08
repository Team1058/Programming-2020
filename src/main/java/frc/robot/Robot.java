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
  public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
  public static IndividualLeds individualLeds = new IndividualLeds();
  public static ClimberSubsystem climberSubsystem = new ClimberSubsystem();
<<<<<<< HEAD
  public static Driver driverGP = new Driver();
  public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public static Operator operatorGP = new Operator();
  public boolean UpDown = true;
  public static Limelight limelight = new Limelight();
  public static TestGP testGP = new TestGP();

=======
  public static Operator operatorGP = new Operator();
  public static TestGP testGP;
  public static Driver driverGP;
  public static MotionPlanner motionPlanner;
>>>>>>> Basic trajectory tracking functional

  @Override
  public void robotInit() {
    spinnerSubsystem.initialize();
    driveTrainSubsystem.initialize();
    climberSubsystem.initialize();
<<<<<<< HEAD
    intakeSubsystem.initialize();
    CameraServer.getInstance().startAutomaticCapture();
  }

  @Override
  public void robotPeriodic() {
    limelight.update();
=======
    motionPlanner = new MotionPlanner(driveTrainSubsystem.getDrivetrain());
    testGP = new TestGP(5, motionPlanner);
    driverGP = new Driver(0, driveTrainSubsystem.getDrivetrain());
>>>>>>> Basic trajectory tracking functional
  }
  
  @Override
  public void teleopPeriodic() {
<<<<<<< HEAD
    driverGP.splitArcadeDrive();
    driverGP.BarDriving();
    operatorGP.Climber();
    operatorGP.Intake();
    
    if (testGP.changeGamepadState()){
=======

    if (testGP.isTestGPEnabled()) {
      testGP.testDrive();
    } else {
>>>>>>> Basic trajectory tracking functional
      driverGP.splitArcadeDrive();
      driveTrainSubsystem.getDrivetrain().setOutputs();
    }
<<<<<<< HEAD
  } 
=======
    
    motionPlanner.printNAVX();
    ledSubsystem.setLEDColor(spinnerSubsystem.getSeenColor());
    
    individualLeds.climbBalance(climberSubsystem.balanceLED());

    // TODO: Figure out how we want to dispatch commands

  }

  @Override
  public void robotPeriodic() {
    super.robotPeriodic();
    // driveTrainSubsystem.getDrivetrain().setOutputs();
    driveTrainSubsystem.getDrivetrain().readInputs();
  }

  @Override
  public void disabledInit() {
    motionPlanner.cancelPath();
  }
>>>>>>> Basic trajectory tracking functional

  @Override
  public void disabledPeriodic(){
    individualLeds.changeAllColors(0,0,0);
    driveTrainSubsystem.getDrivetrain().setTargetVelocity(0, 0);
  }

}
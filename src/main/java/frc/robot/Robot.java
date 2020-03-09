package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SpinnerSubsystem;
import frc.robot.autonomous.MotionPlanner;
import frc.robot.gamepads.Driver;
import frc.robot.gamepads.Operator;
import frc.robot.sensing.Limelight;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.BallPathSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IndividualLeds;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class Robot extends TimedRobot {

  // public static LEDSubsystem ledSubsystem = new LEDSubsystem();
  public static DriveTrainSubsystem driveTrainSubsystem;
  public static IndividualLeds individualLeds = new IndividualLeds();
  public static ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  public static ShooterSubsystem shooterSubsystem = new ShooterSubsystem(); 
  public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public static Limelight limelight = new Limelight();
  public static Operator operatorGP = new Operator();
  public static BallPathSubsystem ballPath = new BallPathSubsystem();
  public static MotionPlanner motionPlanner;
  public static Driver driverGP;

  public static Timer autoTimer;

  @Override
  public void robotInit() {
    autoTimer = new Timer();
    climberSubsystem.initialize();
    driveTrainSubsystem = new DriveTrainSubsystem(limelight);
    intakeSubsystem.initialize();
    climberSubsystem.initialize();
    SmartDashboard.putNumber("SHOOTER_SPEED", 0);
    SmartDashboard.putNumber("RPM Offset", -50);
    motionPlanner = new MotionPlanner(driveTrainSubsystem.getDrivetrain());
    driverGP = new Driver(0, driveTrainSubsystem);
    intakeSubsystem.inferState();
    CameraServer.getInstance().startAutomaticCapture();
    
  }

  @Override
  public void robotPeriodic() {
    driveTrainSubsystem.update();
    limelight.update();
    shooterSubsystem.runStateMachine();
    intakeSubsystem.updateIntake();
    shooterSubsystem.updateHood();
  }

  @Override
  public void disabledInit() {
    shooterSubsystem.disable();
  }

  @Override
  public void disabledPeriodic() {
    motionPlanner.cancelPath();
    //individualLeds.changeAllColors(0,0,0);    
    driveTrainSubsystem.getDrivetrain().setTargetVelocity(0,0);
    //operatorGP.printGPValues();
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
   // double shooterRPM = SmartDashboard.getNumber("SHOOTER_SPEED", 0);
   // Robot.shooterSubsystem.setSpeed(shooterRPM);

    driverGP.climber();
    driverGP.update();

    operatorGP.toggleLed();

    operatorGP.changeShooterState();
    operatorGP.shooterHoodPosition();
    operatorGP.Feed();
    operatorGP.Intake();
    operatorGP.BallPath();
  }

  @Override
  public void autonomousInit() {
    driveTrainSubsystem.getDrivetrain().resetOdometry();
    motionPlanner.moveTo(1, 0, 0, false);
    climberSubsystem.resetClimberServo();
    limelight.turnOnLed();
    autoTimer.start();
  }

  @Override
  public void autonomousPeriodic() {
    if (autoTimer.get() < 5) {
      motionPlanner.reversePath();
    }

    if (!motionPlanner.running  && autoTimer.get() < 7 && driveTrainSubsystem.snapToTargetV2(0)) {
      shooterSubsystem.enable();
      shooterSubsystem.setSpeed(shooterSubsystem.distanceToRPMMaxHood(limelight.getSimpleDistance()) - SmartDashboard.getNumber("RPM Offset", -50));
      shooterSubsystem.autoFeed = true;
      ballPath.ballsToShooter();
    }
    
    if (autoTimer.get() >= 7 && autoTimer.get() <= 8) {
      shooterSubsystem.disable();
      ballPath.stopBalls();
      motionPlanner.moveTo(1, 0, 0, true);
    }

    // if (autoTimer.get() > 8 && autoTimer.get() < 12) {
    //   motionPlanner.forwardPath();
    // }

    // if (autoTimer.get() > 12 && autoTimer.get() < 13){
    //   motionPlanner.moveTo(1, -1, 35, false);
    // }

    // if (autoTimer.get() > 13){
    //   motionPlanner.reversePath();
    //   // TODO Test Intake code when intake is mounted
    //   // intakeSubsystem.intakeGoDown();
    //   // ballPath.ballsToShooter();
    // }
  }
}

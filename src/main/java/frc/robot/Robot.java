package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SpinnerSubsystem;
import frc.robot.autonomous.MotionPlanner;
import frc.robot.gamepads.Driver;
import frc.robot.gamepads.Operator;
import frc.robot.sensing.Limelight;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IndividualLeds;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class Robot extends TimedRobot {

  public static SpinnerSubsystem spinnerSubsystem = new SpinnerSubsystem();
  public static LEDSubsystem ledSubsystem = new LEDSubsystem();
  public static DriveTrainSubsystem driveTrainSubsystem;
  public static IndividualLeds individualLeds = new IndividualLeds();
  public static ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  public static ShooterSubsystem shooterSubsystem = new ShooterSubsystem(); 
  public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public static Limelight limelight = new Limelight();
  public static Operator operatorGP = new Operator();
  public static MotionPlanner motionPlanner;
  public static Driver driverGP;

  @Override
  public void robotInit() {
    spinnerSubsystem.initialize();
    climberSubsystem.initialize();
    driveTrainSubsystem = new DriveTrainSubsystem(limelight);
    intakeSubsystem.initialize();
    climberSubsystem.initialize();
    SmartDashboard.putNumber("SHOOTER_SPEED", 0);
    motionPlanner = new MotionPlanner(driveTrainSubsystem.getDrivetrain());
    driverGP = new Driver(0, driveTrainSubsystem);
  }

  @Override
  public void disabledInit() {
    shooterSubsystem.disable();
  }

  @Override
  public void teleopPeriodic() {
   // shooterSubsystem.tuneShooterFromDashboard();
   //driverGP.splitArcadeDrive();
   driverGP.Climber();
   driverGP.update();
   motionPlanner.printNAVX();
   double shooterRPM = SmartDashboard.getNumber("SHOOTER_SPEED", 0);
   Robot.shooterSubsystem.setSpeed(shooterRPM);
   operatorGP.changeShooterState();
   operatorGP.shooterHoodPosition();
   operatorGP.Feed();
   operatorGP.Intake();
  
  }

  @Override
  public void autonomousInit() {
    motionPlanner.resetNAVX();
    driveTrainSubsystem.getDrivetrain().resetOdometry();
    motionPlanner.moveTo(.5,0,0,false);
  }

  @Override
  public void autonomousPeriodic() {
    motionPlanner.reversePath();
    if (!Robot.motionPlanner.hasRun && Robot.driveTrainSubsystem.snapToTargetV2()) {
      System.out.println("READY TO SHOOT");
    }
  }

  @Override
  public void teleopInit() {
    intakeSubsystem.inferState();
  }

  @Override
  public void robotPeriodic() {
    driveTrainSubsystem.update();
    limelight.update();
    shooterSubsystem.runStateMachine();
    intakeSubsystem.updateIntake();
  }

  @Override
  public void disabledPeriodic() {
    motionPlanner.cancelPath();
    //individualLeds.changeAllColors(0,0,0);    
    //opetsetRumble​(GenericHID.RumbleType type, double value)
    driveTrainSubsystem.getDrivetrain().setTargetVelocity(0,0);
  }

}
package frc.robot.subsystems;

import frc.robot.actuation.DifferentialDrive;
import frc.robot.actuation.SparkMaxMotorSet;
import frc.robot.sensing.Limelight;
import frc.robot.Robot;
import frc.robot.RobotMap;

import com.revrobotics.CANSparkMax;

import java.util.Optional;

public class DriveTrainSubsystem {

  private SparkMaxMotorSet sparkMaxMotorSetLeft;
  private SparkMaxMotorSet sparkMaxMotorSetRight;
  public DifferentialDrive drivetrain;
  private final double maxWheelOmega = 73;
  private final double kP = 0;
  private final double kI = 0;
  private final double kD = 0;
  private final double kIz = 0;
  private final double kFF = 0.0137;
  private final double trackWidth = (12.657 * 2) * 0.0254;  // Wheel to wheel diagonal distance in meters
  private final double wheelRadius = 3 * 0.0254;
  private final double gearRatio = 8;
  private Limelight limelight;
  private final double RAMP_RATE = 0.5;

  public DriveTrainSubsystem(Limelight limelight) {
    int[] leftFollower = {RobotMap.CANIds.DRIVE_LEFT_2};
    int[] rightFollower = {RobotMap.CANIds.DRIVE_RIGHT_2};
    sparkMaxMotorSetLeft = new SparkMaxMotorSet(RobotMap.CANIds.DRIVE_LEFT_1, leftFollower);
    sparkMaxMotorSetRight = new SparkMaxMotorSet(RobotMap.CANIds.DRIVE_RIGHT_1, rightFollower);
    
    sparkMaxMotorSetLeft.setMotorRamp(RAMP_RATE);
    sparkMaxMotorSetRight.setMotorRamp(RAMP_RATE);
    //sparkMaxMotorSetRight.setInverted(true);
    sparkMaxMotorSetRight.setInverted(true);

    // Enables brake mode for all motors
    sparkMaxMotorSetLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
    sparkMaxMotorSetRight.setIdleMode(CANSparkMax.IdleMode.kBrake);

    sparkMaxMotorSetLeft.setPIDConstants(kP, kI, kD, kIz, kFF);
    sparkMaxMotorSetRight.setPIDConstants(kP, kI, kD, kIz, kFF);

    sparkMaxMotorSetLeft.setGearRatio(gearRatio);
    sparkMaxMotorSetRight.setGearRatio(gearRatio);

    sparkMaxMotorSetLeft.setMaxOmega(maxWheelOmega);
    sparkMaxMotorSetRight.setMaxOmega(maxWheelOmega);
    
    drivetrain = new DifferentialDrive(sparkMaxMotorSetRight, sparkMaxMotorSetLeft, trackWidth, wheelRadius, maxWheelOmega);
  }

  public enum DriveMode {
    ARCADE,
    SNAP_TO_TARGET,
    FOLLOW_PATH,
  }

  public DifferentialDrive getDrivetrain() {
    return drivetrain;
    
  }

  public Optional<Double> snapToTarget() {
    Optional<Double> targetAngle = limelight.getTargetAngle(); 
    if (targetAngle.isPresent()) {
      double angleError = targetAngle.get() - drivetrain.getPose().getYaw();
      drivetrain.setTargetVelocity(0, angleError * 2);
      System.out.println("Angle Error: "+angleError);
      return Optional.of(clampDeadband(angleError));
    } else {
      stopAll();
      return Optional.empty();
    }
  }

  public boolean snapToTargetV2(double forward) {
    double tx = Robot.limelight.getTX();
    boolean lookingAtTarget;

    if (Math.abs(tx) >= 13) {
      tx *= .5;
    }

    if (Math.abs(tx) > 1.25) {
      double multiplier = .2;
      if (Math.abs(tx) < 2) {
        multiplier = .4;  
      }else if (Math.abs(tx) < 3) {
        multiplier = .3;  
      } else if (Math.abs(tx) < 5) {
        multiplier = .2;
      }
      drivetrain.setTargetVelocity(forward, -tx * multiplier);
      lookingAtTarget = false;
    } else {
      drivetrain.setTargetVelocity(forward, 0);
      lookingAtTarget = true;
    }

    return lookingAtTarget;
  }

  private boolean outsideDeadband(double inputValue) {           
    return (Math.abs(inputValue) > .05);
  }

  private double clampDeadband(double inputValue) {
      if (outsideDeadband(inputValue)) {
          return inputValue;
      } else {
          return 0;
      }
  } 

  public void update() {
    drivetrain.getInputs();
    drivetrain.setOutputs();
  }
  
  //Stops all motors
  public void stopAll() { 
    drivetrain.setTargetVelocity(0, 0);
  }

  //Drives the robot with left being turning and right being forward/backward
  public void setArcadeDrive(double speed, double rotation) {
    speed *= drivetrain.getMaxVelocityX();
    rotation *= drivetrain.getMaxOmegaZ();
    drivetrain.setTargetVelocity(speed, rotation);
  }

  public void setTankDrive(double left, double right) {
    drivetrain.setPercentVelocity(left, right);
  }

  public void resetOdometry() {
    drivetrain.resetOdometry();
  }

}

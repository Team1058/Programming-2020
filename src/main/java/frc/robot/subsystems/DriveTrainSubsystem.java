package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.actuation.DifferentialDrive;
import frc.robot.actuation.SparkMaxMotorSet;
import frc.robot.sensing.Limelight;
import frc.robot.RobotMap;

import com.revrobotics.CANSparkMax;

import java.util.Optional;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

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
  private final double trackWidth = /*(12.657 * 2)*/ 18.5 * 0.0254; // Wheel to wheel diagonal distance in meters
  private final double wheelRadius = /*3*/ 2 * 0.0254; // In meters
  private final double gearRatio = /*8*/ 5;
  private DriveMode state = DriveMode.ARCADE;
  private Limelight limelight;

  public DriveTrainSubsystem(Limelight limelight) {
    int[] leftFollower = {/*2*/4};
    int[] rightFollower = {/*4*/2};
    sparkMaxMotorSetLeft = new SparkMaxMotorSet(/*1*/3, leftFollower);
    sparkMaxMotorSetRight = new SparkMaxMotorSet(/*3*/1, rightFollower);
    //sparkMaxMotorSetRight.setInverted(true);
    sparkMaxMotorSetLeft.setInverted(true);

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

  public void snapToTargetV2() {
    Optional<Double> y = limelight.getY();
    System.out.print("Y: " + y);
  }

  private boolean outsideDeadband(double inputValue){           
    return (Math.abs(inputValue) > .05);
  }

  private double clampDeadband(double inputValue){
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
  public void stopAll(){
    drivetrain.setTargetVelocity(0, 0);
  }

  //Drives the robot with left being turning and right being forward/backward
  public void setArcadeDrive(double speed, double rotation){
    speed *= drivetrain.getMaxVelocityX();
    rotation *= drivetrain.getMaxOmegaZ();
    drivetrain.setTargetVelocity(speed, rotation);
  }

  public void resetOdometry() {
    drivetrain.resetOdometry();
  }

}


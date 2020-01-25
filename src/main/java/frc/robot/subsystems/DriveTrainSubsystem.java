package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.actuation.DifferentialDrive;
import frc.robot.actuation.SparkMaxMotorSet;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrainSubsystem {

  private SparkMaxMotorSet sparkMaxMotorSetLeft;
  private SparkMaxMotorSet sparkMaxMotorSetRight;
  public DifferentialDrive drivetrain;
  private final double maxWheelOmega = 73;
  private final double kP = 0;
  private final double kI = 0;
  private final double kD = 0;
  private final double kIz = 0;
  private final double kFF = .0137;
  private final double trackWidth = 18.5;
  private final double wheelRadius = 3;
  private final double gearRatio = 8;

  public void initialize() {
    int[] leftFollower = {2};
    int[] rightFollower = {4};
    sparkMaxMotorSetLeft = new SparkMaxMotorSet(1, leftFollower);
    sparkMaxMotorSetRight = new SparkMaxMotorSet(3, rightFollower);
    drivetrain = new DifferentialDrive(sparkMaxMotorSetRight, sparkMaxMotorSetLeft, trackWidth, wheelRadius, maxWheelOmega);
    sparkMaxMotorSetRight.setInverted(true);

    // Enables brake mode for all motors
    sparkMaxMotorSetLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
    sparkMaxMotorSetRight.setIdleMode(CANSparkMax.IdleMode.kCoast);

    sparkMaxMotorSetLeft.setPIDConstants(kP, kI, kD, kIz, kFF);
    sparkMaxMotorSetRight.setPIDConstants(kP, kI, kD, kIz, kFF);

    sparkMaxMotorSetLeft.setGearRatio(gearRatio);
    sparkMaxMotorSetRight.setGearRatio(gearRatio);

    sparkMaxMotorSetLeft.setMaxOmega(maxWheelOmega);
    sparkMaxMotorSetRight.setMaxOmega(maxWheelOmega);
    
  }

  
  //Stops all motors
  public void stopAll(){
    drivetrain.setTargetVelocity(0, 0);
  }

  //Drives the robot with left being turning and right being forward/backward
  public void setDrive(double speed, double rotation){
    drivetrain.setTargetVelocity(speed, rotation);
  }

}


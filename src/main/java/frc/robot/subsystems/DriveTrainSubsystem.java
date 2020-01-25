package frc.robot.subsystems;

<<<<<<< HEAD
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.actuation.DifferentialDrive;
import frc.robot.actuation.SparkMaxMotorSet;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
<<<<<<< HEAD
/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
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
=======
=======
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
>>>>>>> Sams skeleton code work

public class DriveTrainSubsystem{

  private DifferentialDrive drivetrain;
  private CANSparkMax driveSparkL1;
  private CANSparkMax driveSparkL2;
  private CANSparkMax driveSparkR1;
  private CANSparkMax driveSparkR2;
  private CANPIDController m_pidController_CANPIDController;
  private CANEncoder m_encoder_CANEncoder;
  private SpeedControllerGroup  leftDrive, rightDrive;
>>>>>>> Added ClimberSubsystem and removed unnessesary comment

  public void initialize() {
<<<<<<< HEAD
    int[] leftFollower = {2};
    int[] rightFollower = {4};
    sparkMaxMotorSetLeft = new SparkMaxMotorSet(1, leftFollower);
    sparkMaxMotorSetRight = new SparkMaxMotorSet(3, rightFollower);
    drivetrain = new DifferentialDrive(sparkMaxMotorSetRight, sparkMaxMotorSetLeft, trackWidth, wheelRadius, maxWheelOmega);
    sparkMaxMotorSetRight.setInverted(true);
=======
    driveSparkL1 = new CANSparkMax(1, MotorType.kBrushless);
    driveSparkL2 = new CANSparkMax(2, MotorType.kBrushless);
    driveSparkR1 = new CANSparkMax(3, MotorType.kBrushless);
    driveSparkR2 = new CANSparkMax(4, MotorType.kBrushless);
>>>>>>> Finished Gyro code pushing to merge and work on combining with LEDs

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

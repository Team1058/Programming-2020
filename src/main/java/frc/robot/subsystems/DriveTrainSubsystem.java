package frc.robot.subsystems;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
import edu.wpi.first.wpilibj.SpeedController;
>>>>>>> attempt at Caden's failed drive
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.actuation.DifferentialDrive;
import frc.robot.actuation.SparkMaxMotorSet;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
>>>>>>> attempt at Caden's failed drive
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

=======
import edu.wpi.first.wpilibj.SpeedController;
>>>>>>> Push so I can PR gamepad
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
>>>>>>> Sams skeleton code work

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class DriveTrainSubsystem{

  private DifferentialDrive drivetrain;
  private CANSparkMax driveSparkL1;
  private CANSparkMax driveSparkL2;
  private CANSparkMax driveSparkR1;
  private CANSparkMax driveSparkR2;
  private CANPIDController m_pidController_CANPIDController;
  private CANEncoder m_encoder_CANEncoder;
<<<<<<< HEAD
<<<<<<< HEAD
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
=======
  public SpeedControllerGroup  leftDrive, rightDrive;
  private XboxController gamepad = new XboxController(1);
 private final double DEADBAND_VALUE = 0.075;
  

  public void initialize() {
    
    driveSparkL1 = new CANSparkMax(1, MotorType.kBrushless); 
>>>>>>> attempt at Caden's failed drive
=======
  public SpeedControllerGroup  leftDrive, rightDrive;
  private XboxController gamepad = new XboxController(1);
 private final double DEADBAND_VALUE = 0.075;
  

  public void initialize() {
    
    driveSparkL1 = new CANSparkMax(1, MotorType.kBrushless); 
>>>>>>> Push so I can PR gamepad
    driveSparkL2 = new CANSparkMax(2, MotorType.kBrushless);
    driveSparkR1 = new CANSparkMax(3, MotorType.kBrushless);
    driveSparkR2 = new CANSparkMax(4, MotorType.kBrushless);
>>>>>>> Finished Gyro code pushing to merge and work on combining with LEDs

    // Enables brake mode for all motors
<<<<<<< HEAD
    sparkMaxMotorSetLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
    sparkMaxMotorSetRight.setIdleMode(CANSparkMax.IdleMode.kCoast);

    sparkMaxMotorSetLeft.setPIDConstants(kP, kI, kD, kIz, kFF);
    sparkMaxMotorSetRight.setPIDConstants(kP, kI, kD, kIz, kFF);
=======
    driveSparkL1.setIdleMode(CANSparkMax.IdleMode.kBrake);
    driveSparkL2.setIdleMode(CANSparkMax.IdleMode.kBrake);
    driveSparkR1.setIdleMode(CANSparkMax.IdleMode.kBrake);
    driveSparkR2.setIdleMode(CANSparkMax.IdleMode.kBrake);
    

    leftDrive = new SpeedControllerGroup(driveSparkL1, driveSparkL2);
    rightDrive = new SpeedControllerGroup(driveSparkR1, driveSparkR2);

    rightDrive.setInverted(false);
    leftDrive.setInverted(true);
>>>>>>> attempt at Caden's failed drive

    sparkMaxMotorSetLeft.setGearRatio(gearRatio);
    sparkMaxMotorSetRight.setGearRatio(gearRatio);

    sparkMaxMotorSetLeft.setMaxOmega(maxWheelOmega);
    sparkMaxMotorSetRight.setMaxOmega(maxWheelOmega);
    
  }

  public void VideoGameDrive()
  {
      // Gets values of each joystick
      //double leftJoystick = gamepad.getX(Hand.kLeft);

      // Gets values of each trigger
      double rightTrigger = (gamepad.getTriggerAxis(Hand.kRight)/5) * 4;
      double leftTrigger = (gamepad.getTriggerAxis(Hand.kLeft)/ 5) * 4;
      double speed = rightTrigger - leftTrigger;

      double turnControl = gamepad.getX(Hand.kLeft)/5;

      if (Math.abs(rightTrigger) > DEADBAND_VALUE || Math.abs(leftTrigger) > DEADBAND_VALUE)
      {
          if (speed > 0)
          {
            drivetrain.tankDrive(speed - turnControl, speed + turnControl);
          }
          else
          {
            drivetrain.tankDrive(speed + turnControl, speed - turnControl);
          }
      }
      else{
          setDrive(0, 0);
      }
  }
  //Stops all motors
  public void stopAll(){
    drivetrain.setTargetVelocity(0, 0);
  }

  //Drives the robot with left being turning and right being forward/backward
  public void setDrive(double speed, double rotation){
    drivetrain.setTargetVelocity(speed, rotation);
  }
  
<<<<<<< HEAD
<<<<<<< HEAD
}
=======
}
>>>>>>> attempt at Caden's failed drive
=======
}
>>>>>>> Push so I can PR gamepad

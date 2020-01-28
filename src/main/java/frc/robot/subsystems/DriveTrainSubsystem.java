package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

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
  public SpeedControllerGroup  leftDrive, rightDrive;
  private XboxController gamepad = new XboxController(1);
 private final double DEADBAND_VALUE = 0.075;
  

  public void initialize() {
    
    driveSparkL1 = new CANSparkMax(1, MotorType.kBrushless); 
    driveSparkL2 = new CANSparkMax(2, MotorType.kBrushless);
    driveSparkR1 = new CANSparkMax(3, MotorType.kBrushless);
    driveSparkR2 = new CANSparkMax(4, MotorType.kBrushless);

    // Enables brake mode for all motors
    driveSparkL1.setIdleMode(CANSparkMax.IdleMode.kBrake);
    driveSparkL2.setIdleMode(CANSparkMax.IdleMode.kBrake);
    driveSparkR1.setIdleMode(CANSparkMax.IdleMode.kBrake);
    driveSparkR2.setIdleMode(CANSparkMax.IdleMode.kBrake);
    

    leftDrive = new SpeedControllerGroup(driveSparkL1, driveSparkL2);
    rightDrive = new SpeedControllerGroup(driveSparkR1, driveSparkR2);

    rightDrive.setInverted(false);
    leftDrive.setInverted(true);

    drivetrain = new DifferentialDrive(leftDrive, rightDrive);
    // Disables motors when output is not updated often
    drivetrain.setSafetyEnabled(false);

    /**
     * In order to use PID functionality for a controller, a CANPIDController object
     * is constructed by calling the getPIDController() method on an existing
     * CANSparkMax object
     */
    // Encoder object created to display position values
    m_encoder_CANEncoder = driveSparkL1.getEncoder();
    m_encoder_CANEncoder = driveSparkL2.getEncoder();
    m_encoder_CANEncoder = driveSparkR1.getEncoder();
    m_encoder_CANEncoder = driveSparkR2.getEncoder();
    
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
    leftDrive.stopMotor();
    rightDrive.stopMotor();
  }

  //Drives the robot with left being turning and right being forward/backward
  public void setDrive(double left, double right){
    drivetrain.arcadeDrive(left * .5, right);
  }
  
}
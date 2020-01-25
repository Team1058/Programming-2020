package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrainSubsystem{

  private DifferentialDrive drivetrain;
  private CANSparkMax driveSparkL1;
  private CANSparkMax driveSparkL2;
  private CANSparkMax driveSparkR1;
  private CANSparkMax driveSparkR2;
  private CANPIDController m_pidController_CANPIDController;
  private CANEncoder m_encoder_CANEncoder;
  private SpeedControllerGroup  leftDrive, rightDrive;

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

    leftDrive.setInverted(false);
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
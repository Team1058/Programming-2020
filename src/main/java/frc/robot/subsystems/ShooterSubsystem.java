package frc.robot.subsystems;

// code for our shooter
// prototype code

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXPIDSetConfiguration;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

import java.lang.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class ShooterSubsystem {

  
  private final double minTargetVelocity = 500;
  private final double boosterScaleFactor = -1 /*2*/;
  
  final XboxController controller = new XboxController(0);
  final TalonFX flywheel = new TalonFX(9);
  final TalonFX booster = new TalonFX(10);
  private State currentState = State.DISABLED;
  private double targetVelocity = minTargetVelocity;
  private double lastTargetVelocity = 0;
  private boolean enabled = false;
  private Servo servo = new Servo(1);
  private double servoPosition = 0;
  private double MAX_SERVO_POSITION = 1;
  private double MIN_SERVO_POSITION = 0;
  private double HOOD_MOVE_STEP_SIZE = 0.1;
  private boolean setManually = false;
  private double flywheelF = .12;
  private double flywheelP = 0;
  private double flywheelI = 0;
  private double flywheelD = 0;
  private double boosterF = 0;
  private double boosterP = 0;
  private double boosterI = 0;
  private double boosterD = 0;

  public BufferedWriter printwriter;
  long current_time = System.currentTimeMillis();

  public ShooterSubsystem() {
    flywheel.configFactoryDefault();
    booster.configFactoryDefault();
  //  creates input fields on the smart dashboard
    SmartDashboard.putNumber("flywheel Speed", 0);
    SmartDashboard.putNumber("booster Speed", 0);
   
    SmartDashboard.putBoolean("flywheel Enable", false);
    SmartDashboard.putBoolean("booster Enable", false);
    
    updatePIDValues();
    flywheel.setInverted(true);
    flywheel.configSelectedFeedbackCoefficient(1/2048, 0, 10);
    booster.configSelectedFeedbackCoefficient(1/2048, 0, 10);
    booster.follow(flywheel);
    booster.setInverted(TalonFXInvertType.OpposeMaster);
  }

  public void enable() {
    enabled = true;
  }

  public void disable() {
    enabled = false;
  }

  public void setSpeed(double rpm) {
    if (rpm < minTargetVelocity) {
      rpm = minTargetVelocity;
    }
    targetVelocity = rpm;
  }

  public void fireOnce() {

  }

  private boolean atVelocity() {
    return false;
  }

  private boolean updateVelocity() {

    if (targetVelocity != lastTargetVelocity){
      flywheel.set(ControlMode.Velocity, targetVelocity);
      //booster.set(ControlMode.Velocity, targetVelocity * boosterScaleFactor);
      lastTargetVelocity = targetVelocity;
      return true;
    } else {
      return false;
    }
  }

  private void fireAtCommand() {

  }

  private boolean isFiringComplete() {
    return true;
  }

  private void goDisabled() {
    currentState = State.DISABLED;
    flywheel.set(ControlMode.Velocity, 0);
    //booster.set(ControlMode.Velocity, 0);
    lastTargetVelocity = 0;
  }

  private void updateDashboard() {
    double rpm_flywheel = Math.abs((flywheel.getSelectedSensorVelocity() / 4096.0) * 600.0);
    double rpm_booster = Math.abs((booster.getSelectedSensorVelocity() / 4096.0) * 600.0);
    SmartDashboard.putNumber("Actual RPM Flywheel", rpm_flywheel);
    SmartDashboard.putNumber("Actual RPM Booster", rpm_booster);
    SmartDashboard.putString("Shooter State", currentState.toString());
  }

  private void updatePIDValues() {
       flywheelF = SmartDashboard.getNumber("flywheelF", flywheelF);
       flywheel.config_kF(0, flywheelF, 30);
       flywheelP = SmartDashboard.getNumber("flywheelP", 0);
       flywheel.config_kP(0, flywheelP, 30);
       flywheelI = SmartDashboard.getNumber("flywheelI", 0);
       flywheel.config_kI(0, flywheelI, 30);
       flywheelD = SmartDashboard.getNumber("flywheelD", 0);
       flywheel.config_kD(0, flywheelD, 30);
   
      //  boosterF = SmartDashboard.getNumber("boosterF", 0);
      //  booster.config_kF(0, boosterF, 30);
      //  boosterP = SmartDashboard.getNumber("boosterP", 0);
      //  booster.config_kP(0, boosterP, 30);
      //  boosterI = SmartDashboard.getNumber("boosterI", 0);
      //  booster.config_kI(0, boosterI, 30);
      //  boosterD = SmartDashboard.getNumber("boosterD", 0);
      //  booster.config_kD(0, boosterD, 30);
  }

  public void runStateMachine() {
    switch (currentState) {
      case DISABLED:
        if (enabled) {
          updateVelocity();
          currentState = State.SPINNING_UP;
        }
        break;
      case SPINNING_UP:
        if (!enabled) {
          goDisabled();
        } else if (updateVelocity()) {
          break;
        } else if (atVelocity()) {
          currentState = State.READY;
        }
        break;
      case READY:
        if (!enabled) {
          goDisabled();
        } else if (updateVelocity()) {
          currentState = State.SPINNING_UP;
        } else {
          fireAtCommand();
        }
        break;
      case FIRING:
        if (isFiringComplete()) {
          currentState = State.SPINNING_UP;
        }
        break;
    }
    updateDashboard();
  }

  public enum State {
    DISABLED,
    SPINNING_UP,
    READY,
    FIRING,
  }

  public void filecreate(){
    try {
      printwriter = new BufferedWriter(new FileWriter("/tmp/RPM_Values"+current_time+".csv"));  
       
      printwriter.write("Time, RPM1, RPM2, RPM3, Motor_Voltage1, Motor_Voltage2, Motor_Voltage3, \n");
      printwriter.close();

    } catch (Exception e) {
      System.out.println("error");
      e.printStackTrace();
      
    }
  }

  public void shooterHoodExtend() {
    servoPosition = servo.get();
    servoPosition += HOOD_MOVE_STEP_SIZE;
    if (servoPosition > MAX_SERVO_POSITION) {
      servoPosition = MAX_SERVO_POSITION;
    }
    System.out.println("set servo to: " + servoPosition);
    servo.set(servoPosition);
  }

  public void shooterHoodRetract() {
    servoPosition = servo.get();
    servoPosition -= HOOD_MOVE_STEP_SIZE;
    if (servoPosition < MIN_SERVO_POSITION) {
      servoPosition = MIN_SERVO_POSITION;
    }
    System.out.println("set servo to: " + servoPosition);
    servo.set(servoPosition);
  }

  public void shooterFullExtend() {
    servo.set(MAX_SERVO_POSITION);
  }

  public void shooterFullRetract() {
    servo.set(MIN_SERVO_POSITION);
  }

  public void shooterSetToPosition(double position) {
    servo.set(position);
  }

  public void tuneShooterFromDashboard() {
    try {
      printwriter = new BufferedWriter(new FileWriter("/tmp/RPM_Values"+current_time+".csv", true));
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    // values can be -1.00 to 1.00
    // 0.15 is logical minimum
    // pos is clockwise
    // neg is counter clockwise

    double flywheelRPMSetPoint = SmartDashboard.getNumber("Motor_1_Speed", 0);
   // double boosterPMSetPoint = SmartDashboard.getNumber("Motor_2_Speed", 0);
    SmartDashboard.putNumber("CurrentServo Position", servo.get());

   // motor 1 PID controll code
    double motor_1F = SmartDashboard.getNumber("Motor_1F", 0);
    flywheel.config_kF(0, motor_1F, 30);
    double motor_1P = SmartDashboard.getNumber("Motor_1P", 0);
    flywheel.config_kP(0, motor_1P, 30);
    double motor_1I = SmartDashboard.getNumber("Motor_1I", 0);
    flywheel.config_kI(0, motor_1I, 30);
    double motor_1D = SmartDashboard.getNumber("Motor_1D", 0);
    flywheel.config_kD(0, motor_1D, 30);

    // motor 2 PID control code
    double motor_2F = SmartDashboard.getNumber("Motor_2F", 0);
   // booster.config_kF(0, motor_2F, 30);
    double motor_2P = SmartDashboard.getNumber("Motor_2P", 0);
    //booster.config_kP(0, motor_2P, 30);
    double motor_2I = SmartDashboard.getNumber("Motor_2I", 0);
   // booster.config_kI(0, motor_2I, 30);
    double motor_2D = SmartDashboard.getNumber("Motor_2D", 0);
   // booster.config_kD(0, motor_2D, 30);

   
    boolean flywheelEnable = SmartDashboard.getBoolean("Motor_1_Enable", false);
    boolean boosterEnable = SmartDashboard.getBoolean("Motor_2_Enable", false);
    if(flywheelEnable == false){
      flywheelRPMSetPoint = 0.0;
    }

    flywheel.set(ControlMode.Velocity, 4096 * flywheelRPMSetPoint / 600);
   // booster.set(ControlMode.Velocity, 4096 * boosterPMSetPoint / 600);
   
    // motor3.set(ControlMode.PercentOutput, 1 );


    // equation for converting to rpm --- rpm = (units/ 100 ms) / (units in 1
    // revolution) / 100 milli seconds to 1 minute
    // these are the variables for motor 1
    double rpm_flywheel = Math.abs((flywheel.getSelectedSensorVelocity() / 4096.0) * 600.0);
    int motornumber_flywheel = flywheel.getDeviceID();
    double motorvoltage_flywheel = flywheel.getMotorOutputVoltage();

    // motor 2 variables
    double rpm_booster = Math.abs((booster.getSelectedSensorVelocity() / 4096.0) * 600.0);
    int motornumber_booster = booster.getDeviceID();
    double motorvoltage_booster = booster.getMotorOutputVoltage();
    long time = System.currentTimeMillis();
    // contents_flywheel = motornumber_flywheel + "," + rpm_flywheel + "," + motorvoltage_flywheel + "," + intbeambreak1 + "," + time + "\n";
    // contents_booster = motornumber_booster + "," + rpm_booster + "," + motorvoltage_booster + "," + intbeambreak2 + "," + time + "\n";
    // contents_motor3 = motornumber_motor3 + "," + rpm_motor3 + "," + motorvoltage_motor3 + "," + intbeambreak3 + "," + time + "\n";
    
    String contents = (
      time + "," +  
      rpm_flywheel + "," +
      rpm_booster + "," +
      motorvoltage_flywheel + "," +
      motorvoltage_booster + "," +
      0 + ","
    ) + "\n";
    // graphs the RPM of each motor, as well as the status of the beam break
    SmartDashboard.putNumber("RPM_1", rpm_flywheel);
    SmartDashboard.putNumber("RPM_2", rpm_booster);
    try {
      printwriter.write(contents);
      // printwriter.write(contents_booster);
      // printwriter.write(contents_motor3);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
       try {
      printwriter.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
   
        
    }

}  

package frc.robot.subsystems;

// code for our shooter
// prototype code

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXPIDSetConfiguration;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.gamepads.Operator;

import java.lang.*;
import java.util.Optional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class ShooterSubsystem {

  
  private final double minTargetVelocity = 500;
  final TalonFX flywheel = new TalonFX(RobotMap.CANIds.SHOOTER_FLYWHEEL);
  final TalonFX booster = new TalonFX(RobotMap.CANIds.SHOOTER_BOOSTER);
  private final VictorSPX feeder = new VictorSPX(RobotMap.CANIds.SHOOTER_FEEDER);
  private State currentState = State.DISABLED;
  private double targetVelocity = minTargetVelocity;
  private double lastTargetVelocity = 0;
  private boolean enabled = false;
  private Servo servo = new Servo(RobotMap.PWMIds.HOOD_SERVO);
  private double MAX_SERVO_POSITION = 0;
  private double MIN_SERVO_POSITION = 1;
  private double flywheelF = 0.06;
  private double flywheelP = 0.375;
  private double flywheelI = 0;
  private double flywheelD = 0;
  boolean isHoodUp = true;
  public boolean autoFeed = false;
  double returnRPM = 0.0;
  double fallbackMinRPM = 2300.0;
  double fallbackMaxRPM = 2700.0;
  final boolean tuningFromDashboard = false;

  public ShooterSubsystem() {
    if (tuningFromDashboard) {
      initializeSmartDashboad();
    }
    flywheel.configFactoryDefault();
    booster.configFactoryDefault();
    flywheel.setNeutralMode(NeutralMode.Coast);
    booster.setNeutralMode(NeutralMode.Coast);
    flywheel.configClosedloopRamp(1);
    booster.configClosedloopRamp(1);

    flywheel.setInverted(true);
    flywheel.configSelectedFeedbackCoefficient(1/2048, 0, 10);
    booster.configSelectedFeedbackCoefficient(1/2048, 0, 10);
    booster.follow(flywheel);
    booster.setInverted(TalonFXInvertType.OpposeMaster);
    
    flywheel.config_kF(0, flywheelF, 30);
    flywheel.config_kP(0, flywheelP, 30);
    flywheel.config_kI(0, flywheelI, 30);
    flywheel.config_kD(0, flywheelD, 30);
  }

  public void initializeSmartDashboad() {
    // creates input fields on the smart dashboard
    SmartDashboard.putNumber("flywheel Speed", 0);
    SmartDashboard.putNumber("booster Speed", 0);
    SmartDashboard.putBoolean("flywheel Enable", false);
    SmartDashboard.putBoolean("booster Enable", false);
    SmartDashboard.putNumber("flywheelP", flywheelP);
    SmartDashboard.putNumber("flywheelI", flywheelI);
    SmartDashboard.putNumber("flywheelD", flywheelD);
    SmartDashboard.putNumber("flywheelF", flywheelF);
  }

  public void enable() {
    enabled = true;
  }

  public void disable() {
    enabled = false;
    flywheel.set(ControlMode.PercentOutput, 0);
    feeder.set(ControlMode.PercentOutput, 0);
  }

  public void manualDisableStateMachine(){
    enabled = false;
  }

  public void setSpeed(double rpm) {
    rpm = rpm * 2048 / 600;
    if (rpm < minTargetVelocity) {
      rpm = minTargetVelocity;
    }
    targetVelocity = rpm;
  }

  private boolean atVelocity() {
    boolean atVelocity = false;
    double rpm_flywheel = Math.abs((flywheel.getSelectedSensorVelocity() / 2048.0) * 600.0);
    targetVelocity = targetVelocity / 2048 * 600;
    System.out.println("rpm: " + rpm_flywheel + "\ttarget" + targetVelocity);
    if (rpm_flywheel > targetVelocity - 50 && rpm_flywheel < targetVelocity + 50) {
      atVelocity = true;
    } else {
      atVelocity = false;
    }

    return atVelocity;
  }

  private boolean updateVelocity() {
    if (targetVelocity != lastTargetVelocity) {
      flywheel.set(ControlMode.Velocity, targetVelocity);
      lastTargetVelocity = targetVelocity;
      return true;
    } else {
      return false;
    }
  }

  public void fireAtCommand() {
    feeder.set(ControlMode.PercentOutput, -1);
  }

  public void reverseFeeder() {
    feeder.set(ControlMode.PercentOutput, 1);
  }

  private void goDisabled() {
    currentState = State.DISABLED;
    flywheel.set(ControlMode.Velocity, 0);
    lastTargetVelocity = 0;
  }

  private void updateDashboard() {
    double rpm_flywheel = Math.abs((flywheel.getSelectedSensorVelocity() / 2048.0) * 600.0);
    double motorvoltage_flywheel = flywheel.getMotorOutputPercent();
    SmartDashboard.putNumber("MotorVoltage", motorvoltage_flywheel);
    SmartDashboard.putNumber("Actual RPM Flywheel", rpm_flywheel);
    SmartDashboard.putString("Shooter State", currentState.toString());
  
  }

  public void updatePIDValues() {
    flywheelF = SmartDashboard.getNumber("flywheelF", flywheelF);
    flywheel.config_kF(0, flywheelF, 30);
    flywheelP = SmartDashboard.getNumber("flywheelP", flywheelP);
    flywheel.config_kP(0, flywheelP, 30);
    flywheelI = SmartDashboard.getNumber("flywheelI", flywheelI);
    flywheel.config_kI(0, flywheelI, 30);
    flywheelD = SmartDashboard.getNumber("flywheelD", flywheelD);
    flywheel.config_kD(0, flywheelD, 30);
  }

  public void runStateMachine() {
    if (tuningFromDashboard) {
      updatePIDValues();
    }

    switch (currentState) {
      case DISABLED:
        if (enabled) {
          updateVelocity();
          currentState = State.SPINNING_UP;
        }
        Robot.individualLeds.red();
        break;
      case SPINNING_UP:
        if (!enabled) {
          goDisabled();
        } else if (updateVelocity()) {
          Robot.individualLeds.scrollColor(0, 0, 255);
          break;
        } else if (atVelocity()) {
          currentState = State.READY;
        }
        break;
      case READY:
        Robot.individualLeds.green();
        if (!enabled) {
          goDisabled();
        } else if (!atVelocity()) {
          currentState = State.FIRING;
        } else if (autoFeed){
          fireAtCommand();
        }
        break;
      case FIRING:
        if(!atVelocity()){
          feeder.set(ControlMode.PercentOutput, 0);
          Robot.ballPath.stopBalls();
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

  public void shooterHoodExtend() {
    isHoodUp = true;
  }

  public void shooterHoodRetract() {
    isHoodUp = false;
  }

  public void updateHood() {
    if (isHoodUp) {
      servo.set(MAX_SERVO_POSITION);
    } else {
      servo.set(MIN_SERVO_POSITION);
    }
  }

  public double distanceToRPMMaxHood(double distance) {
    if (distance != 0) {
      returnRPM = ((-5.787 * Math.pow(10,-5)) * Math.pow(distance,3)) + (0.0764 * Math.pow(distance,2)) - 
                  (22.7083 * distance) + 4550 + SmartDashboard.getNumber("RPM Offset", 0);
      return returnRPM;
    } else {
      return fallbackMaxRPM;
    }
  }

  public double distanceToRPMMinHood(double distance){
    if (distance != 0) {
      returnRPM = ((-3.858 * Math.pow(10,-4)) * Math.pow(distance,3)) + (0.2222 * Math.pow(distance,2)) - 
                  (37.7778 * distance) + (4.2 * Math.pow(10,3));
      return returnRPM;
    } else {
      return fallbackMinRPM;
    }
  }

  public boolean hoodAtMax() {
    System.out.println("Servo Pos: " + servo.get());
    if (servo.get() < .5) {
      return true;
    } else {
      return false;
    }
  }

  public void manualFlywheel(double rpm) {
    rpm = rpm * 2048 / 600;
    flywheel.set(ControlMode.Velocity, rpm);
  }
}

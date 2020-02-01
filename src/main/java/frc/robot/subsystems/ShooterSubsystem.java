package frc.robot.subsystems;

// code for our shooter
// prototype code

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXPIDSetConfiguration;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.lang.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class ShooterSubsystem {

  final XboxController controller = new XboxController(0);
  final TalonSRX flyWheel = new TalonSRX(1);
  final TalonSRX booster = new TalonSRX(2);
  final TalonSRX loader = new TalonSRX(3);
  public final DigitalInput shooterBeamBreak2 = new DigitalInput(1);
  public final DigitalInput shooterBeamBreak3 = new DigitalInput(3);
  public BufferedWriter printwriter;
  String contents_flyWheel = "";
  String contents_booster = "";
  String contents_loader = "";
  String contents = "";
  long current_time = System.currentTimeMillis();
  private double flyWheelRPMSetPoint;
  private double boosterRPMSetPoint;
  private double loaderRPMSetPoint;
  public ShooterSubsystem() {
    
    // creates input fields on the smart dashboard
    // SmartDashboard.putNumber("Motor_1_Speed", 0);
    // SmartDashboard.putNumber("Motor_2_Speed", 0);
    // SmartDashboard.putNumber("Motor_3_Speed", 0);
    // SmartDashboard.putBoolean("Motor_1_Enable", false);
    // SmartDashboard.putBoolean("Motor_2_Enable", false);
    // SmartDashboard.putBoolean("Motor_3_Enable", false);
    // SmartDashboard.putNumber("Motor_1F", 0);
    // SmartDashboard.putNumber("motor_1P", 0);
    // SmartDashboard.putNumber("Motor_1I", 0);
    // SmartDashboard.putNumber("Motor_1D", 0);
    SmartDashboard.putNumber("Motor_3F", 0);
    SmartDashboard.putNumber("motor_3P", 0);
    SmartDashboard.putNumber("Motor_3I", 0);
    SmartDashboard.putNumber("Motor_3D", 0);

    //  SmartDashboard.putNumber("Motor_2F", 0);
    //  SmartDashboard.putNumber("motor_2P", 0);
    //  SmartDashboard.putNumber("Motor_2I", 0);
    //  SmartDashboard.putNumber("Motor_2D", 0);
    

    // flyWheel.config_kF(0, 0, 30);
		// flyWheel.config_kP(0, 0, 30);
		// flyWheel.config_kI(0, 0, 30);
    // flyWheel.config_kD(0, 0, 30);

    booster.config_kF(0, 0, 30);
		booster.config_kP(0, 0, 30);
		booster.config_kI(0, 0, 30);
    booster.config_kD(0, 0, 30);

    loader.config_kF(0, 0, 30);
		loader.config_kP(0, 0, 30);
		loader.config_kI(0, 0, 30);
    loader.config_kD(0, 0, 30);

  }

  public void setHeadPosition(double angle) {

  }

  public void setSpeed(double rpm) {
    flyWheelRPMSetPoint = rpm;
    boosterRPMSetPoint = rpm;
    loaderRPMSetPoint = rpm;

  }

  public void fire() {

  }

  public void filecreate() {
    try {
      printwriter = new BufferedWriter(new FileWriter("/tmp/RPM_Values"+current_time+".csv"));  
       
      printwriter.write("Time, RPM1, RPM2, RPM3, Motor_Voltage1, Motor_Voltage2, Motor_Voltage3, Beam_Break1, Beam_Break2, Beam_Break3\n");
      printwriter.close();

    } catch (Exception e) {
      System.out.println("error");
      e.printStackTrace();
      
    }
  }
  public void Encoder() {
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
		
    
    flyWheel.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    booster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    loader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

   // motor 1 PID control code
    // double motor_1F = SmartDashboard.getNumber("Motor_1F", 0);
    // flyWheel.config_kF(0, motor_1F, 30);
    // double motor_1P = SmartDashboard.getNumber("Motor_1P", 0);
    // flyWheel.config_kP(0, motor_1P, 30);
    // double motor_1I = SmartDashboard.getNumber("Motor_1I", 0);
    // flyWheel.config_kI(0, motor_1I, 30);
    // double motor_1D = SmartDashboard.getNumber("Motor_1D", 0);
    // flyWheel.config_kD(0, motor_1D, 30);

    // motor 2 PID control code
    double motor_2F = SmartDashboard.getNumber("Motor_2F", 0);
    booster.config_kF(0, motor_2F, 30);
    double motor_2P = SmartDashboard.getNumber("Motor_2P", 0);
    booster.config_kP(0, motor_2P, 30);
    double motor_2I = SmartDashboard.getNumber("Motor_2I", 0);
    booster.config_kI(0, motor_2I, 30);
    double motor_2D = SmartDashboard.getNumber("Motor_2D", 0);
    booster.config_kD(0, motor_2D, 30);

    //motor 3 PID control code
    double motor_3F = SmartDashboard.getNumber("Motor_3F", 0);
    loader.config_kF(0, motor_3F, 30);
    double motor_3P = SmartDashboard.getNumber("Motor_3P", 0);
    loader.config_kP(0, motor_3P, 30);
    double motor_3I = SmartDashboard.getNumber("Motor_3I", 0);
    loader.config_kI(0, motor_3I, 30);
   double motor_3D = SmartDashboard.getNumber("Motor_3D", 0);
   loader.config_kD(0, motor_3D, 30);


    boolean flyWheelEnable = SmartDashboard.getBoolean("Motor_1_Enable", false);
    boolean boosterEnable = SmartDashboard.getBoolean("Motor_2_Enable", false);
    boolean loaderEnable = SmartDashboard.getBoolean("Motor_3_Enable", false);
    if(!controller.getAButton()) {
      flyWheelRPMSetPoint = 0.0;
    }
    if(boosterEnable == false) {
      boosterRPMSetPoint = 0.0;
    }
    if(loaderEnable == false) {
      loaderRPMSetPoint = 0.0;
    }
    // flyWheelSpeed = flyWheelSpeed * 4096 / 600;
    // flyWheel.set(ControlMode.Velocity,flyWheelSpeed);
    if (controller.getAButton()) {
      flyWheelRPMSetPoint = 1;
    } else {
      flyWheelRPMSetPoint = 0;
    }
    flyWheel.set(ControlMode.PercentOutput,flyWheelRPMSetPoint);
    booster.set(ControlMode.Velocity, 4096 * boosterRPMSetPoint / 600);
    loader.set(ControlMode.Velocity, 4096 * loaderRPMSetPoint / 600);
    // loader.set(ControlMode.PercentOutput, 1 );


    // equation for converting to rpm --- rpm = (units/ 100 ms) / (units in 1
    // revolution) / 100 milli seconds to 1 minute
    // these are the variables for motor 1
    double rpm_flyWheel = Math.abs((flyWheel.getSelectedSensorVelocity() / 4096.0) * 600.0);
    int motornumber_flyWheel = flyWheel.getDeviceID();
    double motorvoltage_flyWheel = flyWheel.getMotorOutputVoltage();

    // motor 2 variables
    double rpm_booster = Math.abs((booster.getSelectedSensorVelocity() / 4096.0) * 600.0);
    int motornumber_booster = booster.getDeviceID();
    double motorvoltage_booster = booster.getMotorOutputVoltage();
    boolean booleanbeambreak2 = shooterBeamBreak2.get();
    int intbeambreak2 = 0;
    if(booleanbeambreak2 == true) {
      intbeambreak2 = 10000;
    }
    // motor 3 variables
    double rpm_loader = Math.abs((loader.getSelectedSensorVelocity() / 4096.0) * 600.0);
    int motornumber_loader = loader.getDeviceID();
    double motorvoltage_loader = loader.getMotorOutputVoltage();
    boolean booleanbeambreak3 = shooterBeamBreak3.get();
    int intbeambreak3 = 0;
    if(booleanbeambreak3 == true) {
      intbeambreak3 = 10000;
    }
    long time = System.currentTimeMillis();
    // contents_flyWheel = motornumber_flyWheel + "," + rpm_flyWheel + "," + motorvoltage_flyWheel + "," + intbeambreak1 + "," + time + "\n";
    // contents_booster = motornumber_booster + "," + rpm_booster + "," + motorvoltage_booster + "," + intbeambreak2 + "," + time + "\n";
    // contents_loader = motornumber_loader + "," + rpm_loader + "," + motorvoltage_loader + "," + intbeambreak3 + "," + time + "\n";
    
    contents = (
      time + "," +  
      rpm_flyWheel + "," +
      rpm_booster + "," +
      rpm_loader + "," +
      motorvoltage_flyWheel + "," +
      motorvoltage_booster + "," +
      motorvoltage_loader + "," +
      0 + "," +
      intbeambreak2 + "," +
      intbeambreak3
    ) + "\n";
    // graphs the RPM of each motor, as well as the status of the beam break
    SmartDashboard.putNumber("RPM_1", rpm_flyWheel);
    SmartDashboard.putNumber("RPM_2", rpm_booster);
    SmartDashboard.putNumber("RPM_3", rpm_loader);
    SmartDashboard.putNumber("Beam_Break2", intbeambreak2);
    SmartDashboard.putNumber("Beam_Break3", intbeambreak3);
    try {
      printwriter.write(contents);
      // printwriter.write(contents_booster);
      // printwriter.write(contents_loader);
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

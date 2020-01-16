package frc.robot.subsystems;



// code for our shooter
// prototype code


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import java.lang.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class ShooterSubsystem {

  final TalonSRX motor1 = new TalonSRX(1);
  final TalonSRX motor2 = new TalonSRX(2);
  final TalonSRX motor3 = new TalonSRX(3);
  final TalonSRX motor4 = new TalonSRX(4);
  public BufferedWriter printwriter;
  String contents_motor1 = "";
  String contents_motor2 = "";
  String contents_motor3 = "";

  public ShooterSubsystem() {
  //  try {
  //     printwriter = new BufferedWriter(new FileWriter("/tmp/RPM_Values2.csv"));  
       
  //     printwriter.write("Motor_Number, RPM, Motor_Voltage, Time\n");
  //     printwriter.close();
  //     // printwriter.close();

  //   } catch (Exception e) {
  //     System.out.println("error");
  //     e.printStackTrace();
      
  //   }
  }
  public void filecreate(){
    try {
      printwriter = new BufferedWriter(new FileWriter("/tmp/RPM_Values2.csv"));  
       
      printwriter.write("Motor_Number, RPM, Motor_Voltage, Time\n");
      printwriter.close();
      // printwriter.close();

    } catch (Exception e) {
      System.out.println("error");
      e.printStackTrace();
      
    }
  }
  public void Encoder() {
    try {
      printwriter = new BufferedWriter(new FileWriter("/tmp/RPM_Values2.csv", true));
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    // values can be -1.00 to 1.00
    // 0.15 is logical minimum
    // pos is clockwise
    // neg is counter clockwise
    motor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 
    10);
    motor2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0,10);
    motor3.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 
    10);
    motor1.set(ControlMode.PercentOutput, .15);
    motor2.set(ControlMode.PercentOutput, .25);
    motor3.set(ControlMode.PercentOutput, .36);
    // equation for converting to rpm --- rpm = (units/ 100 ms) / (units in 1
    // revolution) / 100 milli seconds to 1 minute
    //these are the variables for motor 1
    double rpm_motor1 = Math.abs((motor1.getSelectedSensorVelocity() / 4096.0) * 600.0);
    int motornumber_motor1 = motor1.getDeviceID();
    double motorvoltage_motor1 = motor1.getMotorOutputVoltage();
    // motor 2 variables
    double rpm_motor2 = Math.abs((motor2.getSelectedSensorVelocity() / 4096.0) * 600.0);
    int motornumber_motor2 = motor2.getDeviceID();
    double motorvoltage_motor2 = motor2.getMotorOutputVoltage();
    //motor 3 variables
    double rpm_motor3 = Math.abs((motor2.getSelectedSensorVelocity() / 4096.0) * 600.0);
    int motornumber_motor3 = motor3.getDeviceID();
    double motorvoltage_motor3 = motor2.getMotorOutputVoltage();
    long time = System.currentTimeMillis();
    contents_motor1 = motornumber_motor1 + "," + rpm_motor1 + "," + motorvoltage_motor1 +  "," + time + "\n";
    contents_motor2 = motornumber_motor2 + "," + rpm_motor2 + "," + motorvoltage_motor2 +  "," + time + "\n";
    contents_motor3 = motornumber_motor3 + "," + rpm_motor3 + "," + motorvoltage_motor3 +  "," + time + "\n";
    try {
      printwriter.write(contents_motor1);
      printwriter.write(contents_motor2);
      printwriter.write(contents_motor3);
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

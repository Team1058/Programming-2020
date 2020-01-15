package frc.robot.subsystems;



// code for our shooter
// prototype code


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

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
  String contents = "";

  public ShooterSubsystem() {
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
    motor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    motor1.set(ControlMode.PercentOutput, .15);
    // equation for converting to rpm --- rpm = (units/ 100 ms) / (units in 1
    // revolution) / 100 milli seconds to 1 minute
    double rpm = (motor1.getSelectedSensorVelocity() / 4096.0) * 600.0;
    int motornumber = motor1.getDeviceID();
    double motorvoltage = motor1.getMotorOutputVoltage();
    long time = System.currentTimeMillis();
    contents = motornumber + "," + rpm + "," + motorvoltage +  "," + time + "\n";
    try {
      printwriter.write(contents);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
       // printwriter.flush();    
        System.out.println("RPM: " + rpm);
        System.out.println("motornumber" + motornumber);
        System.out.println("motorvoltage" + motorvoltage);
       // System.out.println(contents);

       try {
      printwriter.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
   
        
    }

    public void closefile(){
      System.out.println(contents.length());
      try {
      printwriter.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    }

}  

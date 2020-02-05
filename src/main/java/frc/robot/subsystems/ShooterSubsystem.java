package frc.robot.subsystems;

// code for our shooter
// prototype code

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
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
  final TalonFX motor1 = new TalonFX(9);
  final TalonFX motor2 = new TalonFX(10);
  

  public BufferedWriter printwriter;
  String contents_motor1 = "";
  String contents_motor2 = "";
  String contents = "";
  long current_time = System.currentTimeMillis();
  public ShooterSubsystem() {
    
  //  creates input fields on the smart dashboard
    SmartDashboard.putNumber("Motor_1_Speed", 0);
    SmartDashboard.putNumber("Motor_2_Speed", 0);
   
    SmartDashboard.putBoolean("Motor_1_Enable", false);
    SmartDashboard.putBoolean("Motor_2_Enable", false);
    
    SmartDashboard.putNumber("Motor_1F", 0);
    SmartDashboard.putNumber("motor_1P", 0);
    SmartDashboard.putNumber("Motor_1I", 0);
    SmartDashboard.putNumber("Motor_1D", 0);
   

     SmartDashboard.putNumber("Motor_2F", 0);
     SmartDashboard.putNumber("motor_2P", 0);
     SmartDashboard.putNumber("Motor_2I", 0);
     SmartDashboard.putNumber("Motor_2D", 0);
    

    motor1.config_kF(0, 0, 30);
    motor1.config_kP(0, 0, 30);
	motor1.config_kI(0, 0, 30);
    motor1.config_kD(0, 0, 30);

    motor2.config_kF(0, 0, 30);
	motor2.config_kP(0, 0, 30);
	motor2.config_kI(0, 0, 30);
    motor2.config_kD(0, 0, 30);

  

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
		
    
    motor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    motor2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    
    double motor1RPMSetPoint = SmartDashboard.getNumber("Motor_1_Speed", 0);
    double motor2RPMSetPoint = SmartDashboard.getNumber("Motor_2_Speed", 0);
 
   // motor 1 PID controll code
    double motor_1F = SmartDashboard.getNumber("Motor_1F", 0);
    motor1.config_kF(0, motor_1F, 30);
    double motor_1P = SmartDashboard.getNumber("Motor_1P", 0);
    motor1.config_kP(0, motor_1P, 30);
    double motor_1I = SmartDashboard.getNumber("Motor_1I", 0);
    motor1.config_kI(0, motor_1I, 30);
    double motor_1D = SmartDashboard.getNumber("Motor_1D", 0);
    motor1.config_kD(0, motor_1D, 30);

    // motor 2 PID control code
    double motor_2F = SmartDashboard.getNumber("Motor_2F", 0);
    motor2.config_kF(0, motor_2F, 30);
    double motor_2P = SmartDashboard.getNumber("Motor_2P", 0);
    motor2.config_kP(0, motor_2P, 30);
    double motor_2I = SmartDashboard.getNumber("Motor_2I", 0);
    motor2.config_kI(0, motor_2I, 30);
    double motor_2D = SmartDashboard.getNumber("Motor_2D", 0);
    motor2.config_kD(0, motor_2D, 30);

   
    boolean motor1Enable = SmartDashboard.getBoolean("Motor_1_Enable", false);
    boolean motor2Enable = SmartDashboard.getBoolean("Motor_2_Enable", false);
    if(motor1Enable == false){
      motor1RPMSetPoint = 0.0;
    }
    if(motor2Enable == false){
      motor2RPMSetPoint = 0.0;
    }
   
    // motor1Speed = motor1Speed * 4096 / 600;
    // motor1.set(ControlMode.Velocity,motor1Speed);
    if (controller.getAButton()){
      motor1RPMSetPoint = 1;
    }else{
      motor1RPMSetPoint = 0;
    }
    motor1.set(ControlMode.Velocity, 4096 * motor1RPMSetPoint / 600);
    motor2.set(ControlMode.Velocity, 4096 * motor2RPMSetPoint / 600);
   
    // motor3.set(ControlMode.PercentOutput, 1 );


    // equation for converting to rpm --- rpm = (units/ 100 ms) / (units in 1
    // revolution) / 100 milli seconds to 1 minute
    // these are the variables for motor 1
    double rpm_motor1 = Math.abs((motor1.getSelectedSensorVelocity() / 4096.0) * 600.0);
    int motornumber_motor1 = motor1.getDeviceID();
    double motorvoltage_motor1 = motor1.getMotorOutputVoltage();

    // motor 2 variables
    double rpm_motor2 = Math.abs((motor2.getSelectedSensorVelocity() / 4096.0) * 600.0);
    int motornumber_motor2 = motor2.getDeviceID();
    double motorvoltage_motor2 = motor2.getMotorOutputVoltage();
    long time = System.currentTimeMillis();
    // contents_motor1 = motornumber_motor1 + "," + rpm_motor1 + "," + motorvoltage_motor1 + "," + intbeambreak1 + "," + time + "\n";
    // contents_motor2 = motornumber_motor2 + "," + rpm_motor2 + "," + motorvoltage_motor2 + "," + intbeambreak2 + "," + time + "\n";
    // contents_motor3 = motornumber_motor3 + "," + rpm_motor3 + "," + motorvoltage_motor3 + "," + intbeambreak3 + "," + time + "\n";
    
    contents = (
      time + "," +  
      rpm_motor1 + "," +
      rpm_motor2 + "," +
      motorvoltage_motor1 + "," +
      motorvoltage_motor2 + "," +
      0 + ","
    ) + "\n";
    // graphs the RPM of each motor, as well as the status of the beam break
    SmartDashboard.putNumber("RPM_1", rpm_motor1);
    SmartDashboard.putNumber("RPM_2", rpm_motor2);
    try {
      printwriter.write(contents);
      // printwriter.write(contents_motor2);
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

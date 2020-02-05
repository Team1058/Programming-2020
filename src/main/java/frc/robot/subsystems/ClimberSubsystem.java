/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.ADXL345_SPI;

public class ClimberSubsystem {

    private TalonFX falcon1 = new TalonFX(RobotMap.CANIds.CLIMBER_FALCON_1);
    private TalonFX falcon2 = new TalonFX(RobotMap.CANIds.CLIMBER_FALCON_2);
    private TalonSRX climberTalon  = new TalonSRX(RobotMap.CANIds.CLIMBER_TALON);

    double angleInit;

    private AnalogGyro climberGyro;

    public void initialize(){
        initializeGyro();
    }

    private void initializeGyro(){
        climberGyro = new AnalogGyro(0, 0, -.13);
        climberGyro.initGyro();
        climberGyro.calibrate(); 
        climberGyro.reset();
        angleInit = climberGyro.getAngle();
    }

    public void printGyroPos(){
        System.out.println("Init Pos: " + angleInit);
        System.out.println("Cur Pos : " + climberGyro.getAngle());
    }

    public void climberExtend(){

    }
    
    public void climberRetract(){

    }

    public void driveLeft(){

    }

    public void driveRight(){

    }

}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.ADXL345_SPI;

public class ClimberSubsystem {

    private TalonFX falcon1 = new TalonFX(RobotMap.CANIds.CLIMBER_FALCON_1);
    private TalonFX falcon2 = new TalonFX(RobotMap.CANIds.CLIMBER_FALCON_2);
    private TalonSRX skootyTalon  = new TalonSRX(RobotMap.CANIds.CLIMBER_TALON);

    private int FALCON_REVERSE_SOFT_LIMIT = 100;
    private int FALCON_FORWARD_SOFT_LIMIT = 180000;
    double angleInit;

    private AnalogGyro climberGyro;

    public void initialize(){
        initializeGyro();
        falcon2.follow(falcon1);
        falcon1.setSelectedSensorPosition(0);
        falcon2.setSelectedSensorPosition(0);
        falcon1.configReverseSoftLimitEnable(true);
        falcon1.configForwardSoftLimitEnable(true);
        falcon1.configReverseSoftLimitThreshold(FALCON_REVERSE_SOFT_LIMIT);
        falcon1.configForwardSoftLimitThreshold(FALCON_FORWARD_SOFT_LIMIT);
        skootyTalon.setSelectedSensorPosition(1);
        skootyTalon.configForwardSoftLimitEnable(false);
        skootyTalon.configReverseSoftLimitEnable(false);
    }

    private void initializeGyro(){
        climberGyro = new AnalogGyro(0, 0, -.13);
        climberGyro.initGyro();
        climberGyro.calibrate(); 
        climberGyro.reset();
        angleInit = climberGyro.getAngle();
    }

    public void printFalconsPos(){
        System.out.println("falcon1: " + falcon1.getSelectedSensorPosition());
        System.out.println("falcon2: " + falcon2.getSelectedSensorPosition());
    }

    public void printGyroPos(){
        System.out.println("Init Pos: " + angleInit);
        System.out.println("Cur Pos : " + climberGyro.getAngle());
    }

    public void climberExtend(){
        falcon1.set(ControlMode.PercentOutput, .25);
    }
    
    public void climberRetract(){
        falcon1.set(ControlMode.PercentOutput, -.25);
    }
    
    public void climberStop(){
        falcon1.set(ControlMode.PercentOutput, 0);
    }

    public void DriveBar(double speed)
    {
        skootyTalon.set(ControlMode.PercentOutput,speed);
    }

    public void driveStop()
    {
        skootyTalon.set(ControlMode.PercentOutput,0);
    }

}

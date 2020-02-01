/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.ADXL345_SPI;

public class ClimberSubsystem {

    public TalonFX falcon1;
    private TalonFX falcon2;
    private TalonSRX climberTalon;
    double angleInit;
    double gyroDeadband = 3;

    private AnalogGyro climberGyro;

    public void initialize(){

    climberGyro = new AnalogGyro(0, 0, -.13);
    climberGyro.initGyro();
    climberGyro.calibrate();
    
    climberGyro.reset();
    angleInit = climberGyro.getAngle();
    System.out.println("GYRO ANGLE INIT: " + angleInit);
    falcon1 = new TalonFX(RobotMap.CANIds.FALCON_1);
    falcon2 = new TalonFX(RobotMap.CANIds.FALCON_2);
    climberTalon = new TalonSRX(RobotMap.CANIds.CLIMBER_TALON);
    }
   //filler code
   public TalonFX falcon1(){
        return falcon1;
    }   


    public TalonFX falcon2(){
        return falcon2;
    }



    // autonomous balancing
    // not applicable yet
    public void balanceRobot(){
        if (Math.abs(climberGyro.getAngle()) > gyroDeadband){
            //Moves robot to center balance
            if (climberGyro.getAngle() > gyroDeadband)
            {
                while (climberGyro.getAngle() > gyroDeadband)
                {
                    falcon1.set(ControlMode.PercentOutput, -.07);
                    falcon2.set(ControlMode.PercentOutput, -.07);
                }
            }
            else{
                while (climberGyro.getAngle() < gyroDeadband)
                {
                    falcon1.set(ControlMode.PercentOutput, .07);
                    falcon2.set(ControlMode.PercentOutput, .07);
                }
            }
        }
    } 

    String outputString = "";

    public String balanceLED(){

        System.out.println(climberGyro.getAngle());
        // check for gyro
        if(Math.abs(climberGyro.getAngle()) > gyroDeadband){
            // + is right, - is left
            if (climberGyro.getAngle() > gyroDeadband){
                outputString = "TooFarRight";
            }else if(climberGyro.getAngle() < gyroDeadband){
                outputString = "TooFarLeft";
            }
            // gyro is balanced
        }else{
            outputString = "InDeadband";
        }

        return outputString;
    }

    public void climberExtend(int r){
        r = r * 1000;
        falcon1.set(TalonFXControlMode.Position, r);
    }
    
    public void climberRetract(){

    }

    public void driveLeft(){

    }

    public void driveRight(){

    }

}

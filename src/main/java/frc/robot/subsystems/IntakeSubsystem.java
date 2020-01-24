/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class IntakeSubsystem {

    private CANSparkMax intakeLift;

    private VictorSPX intakeWheels;


    public void initialize(){

        intakeLift = new CANSparkMax(RobotMap.CANIds.INTAKE_SPARK, MotorType.kBrushless);

        intakeLift.setIdleMode(CANSparkMax.IdleMode.kBrake);

        intakeWheels = new VictorSPX(RobotMap.CANIds.INTAKE_VICTOR);
    }

    public void intakeOn(){
        intakeWheels.set(ControlMode.PercentOutput, -1);
    }

    public void intakeOff(){
        intakeWheels.set(ControlMode.PercentOutput, 0);
    }

    public void liftIntake(){

    }

    public void dropIntake(){
        
    }
}

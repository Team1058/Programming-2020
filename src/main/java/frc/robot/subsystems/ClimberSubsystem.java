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

public class ClimberSubsystem {

    private TalonFX falcon1;
    private TalonFX falcon2;
    private TalonSRX climberTalon;

    public void initialize(){

    falcon1 = new TalonFX(RobotMap.CANIds.FALCON_1);
    falcon2 = new TalonFX(RobotMap.CANIds.FALCON_2);
    climberTalon = new TalonSRX(RobotMap.CANIds.CLIMBER_TALON);

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

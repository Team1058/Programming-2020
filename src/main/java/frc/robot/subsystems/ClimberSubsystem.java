/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.ADXL345_SPI;

public class ClimberSubsystem {

    private TalonFX falcon1 = new TalonFX(RobotMap.CANIds.CLIMBER_FALCON_1);
    //private TalonFX falcon2 = new TalonFX(RobotMap.CANIds.CLIMBER_FALCON_2);
    private VictorSPX skootyVictor  = new VictorSPX(RobotMap.CANIds.SPINNER_SKOOTY);
    // private AnalogGyro climberGyro;

    private final int FALCON_REVERSE_SOFT_LIMIT = -10000;
    private final int FALCON_FORWARD_SOFT_LIMIT = 180000;
    // private double angleInit;

    public void initialize() {
        // initializeGyro();
        //falcon2.follow(falcon1);
        falcon1.setSelectedSensorPosition(0);
        //falcon2.setSelectedSensorPosition(0);
        falcon1.configReverseSoftLimitEnable(false);
        falcon1.configForwardSoftLimitEnable(false);
        falcon1.configReverseSoftLimitThreshold(FALCON_REVERSE_SOFT_LIMIT);
        falcon1.configForwardSoftLimitThreshold(FALCON_FORWARD_SOFT_LIMIT);
        falcon1.setNeutralMode(NeutralMode.Brake);
        //falcon2.setNeutralMode(NeutralMode.Brake);
        skootyVictor.setSelectedSensorPosition(1);
        skootyVictor.configForwardSoftLimitEnable(true);
        skootyVictor.configReverseSoftLimitEnable(true);
        // limits for skooty/spinner
        // skootyVictor.configForwardSoftLimitThreshold(forwardSensorLimit);
        // skootyVictor.configReverseSoftLimitThreshold(reverseSensorLimit);
    }

    // private void initializeGyro(){
    //     climberGyro = new AnalogGyro(0, 0, -.13);
    //     climberGyro.initGyro();
    //     climberGyro.calibrate(); 
    //     climberGyro.reset();
    //     angleInit = climberGyro.getAngle();
    // }
    public void climberRetract(double multiplier) {
        falcon1.set(ControlMode.PercentOutput, 1 * multiplier);
    }

    public void climberStop() {
        falcon1.set(ControlMode.PercentOutput, 0);
    }

    public void driveBar(double speed) {
        skootyVictor.set(ControlMode.PercentOutput,speed);
    }

    public void driveStop() {
        skootyVictor.set(ControlMode.PercentOutput,0);
    }
}

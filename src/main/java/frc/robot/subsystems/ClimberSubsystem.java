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

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.ADXL345_SPI;

public class ClimberSubsystem {

    private TalonFX falcon1 = new TalonFX(RobotMap.CANIds.CLIMBER_FALCON_1);
    // private TalonFX falcon2 = new TalonFX(RobotMap.CANIds.CLIMBER_FALCON_2);
    // private TalonSRX skootyTalon  = new TalonSRX(RobotMap.CANIds.CLIMBER_TALON);
    private Servo climberServo = new Servo(RobotMap.PWMIds.CLIMBER_SERVO);
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
        // falcon1.configReverseSoftLimitThreshold(FALCON_REVERSE_SOFT_LIMIT);
        // falcon1.configForwardSoftLimitThreshold(FALCON_FORWARD_SOFT_LIMIT);
        falcon1.setNeutralMode(NeutralMode.Brake);
        // falcon2.setNeutralMode(NeutralMode.Brake);
        // skootyTalon.setSelectedSensorPosition(1);
        // skootyTalon.configForwardSoftLimitEnable(false);
        // skootyTalon.configReverseSoftLimitEnable(false);
    }

    // private void initializeGyro(){
    //     climberGyro = new AnalogGyro(0, 0, -.13);
    //     climberGyro.initGyro();
    //     climberGyro.calibrate(); 
    //     climberGyro.reset();
    //     angleInit = climberGyro.getAngle();
    // }

    public void climberExtend(double multiplier) {
        falcon1.set(ControlMode.PercentOutput, -1 * multiplier);
    }

    public void climberRetract(double multiplier) {
        falcon1.set(ControlMode.PercentOutput, 1 * multiplier);
    }

    public void climberStop() {
        falcon1.set(ControlMode.PercentOutput, 0);
    }

    public void driveBar(double speed) {
        // skootyTalon.set(ControlMode.PercentOutput,speed);
    }

    public void resetClimberServo() {
        climberServo.set(.8);
    }

    public void lockRatchet() {
        climberServo.set(.2);
    }

    public void driveStop() {
        // skootyTalon.set(ControlMode.PercentOutput,0);
    }
}


package frc.robot.subsystems;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class IntakeSubsystem {

    public TalonSRX intakeLift = new TalonSRX(RobotMap.CANIds.INTAKE_TALON_1);

    private VictorSPX intakeWheels = new VictorSPX(RobotMap.CANIds.INTAKE_VICTOR_WHEELS);

    //Variables Initialized
    double motorPercentSpeed = .5;

    public void initialize(){
       // intakeLift.set(CANSparkMax.IdleMode.kBrake);
        intakeLift.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.Disabled, 30);
        intakeLift.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.Disabled, 30);
        intakeLift.configReverseSoftLimitEnable(false);
        intakeLift.configForwardSoftLimitEnable(false);
    }

    public void intakeBalls(double speed){
        intakeWheels.set(ControlMode.PercentOutput, speed);
    }

    public void intakeSpit(double speed){
        intakeWheels.set(ControlMode.PercentOutput, speed);
    }

    public void intakeOff(){
        intakeWheels.set(ControlMode.PercentOutput, 0);
        intakeLift.set(ControlMode.PercentOutput, 0);

    }
    public void liftIntake(){
        intakeLift.set(ControlMode.PercentOutput, 0.1);
    }

    public void liftIntakeFast(){
        intakeLift.set(ControlMode.PercentOutput, 0.5);
    }    

    public void dropIntake(){
        intakeLift.set(ControlMode.PercentOutput, -0.1);
    }

    public void dropIntakeFast(){
        intakeLift.set(ControlMode.PercentOutput, -0.5);
    }
}

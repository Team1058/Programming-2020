package frc.robot.subsystems;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class BallPathSubsystem {

    public TalonSRX bpTalon = new TalonSRX(RobotMap.CANIds.BALL_PATH_TALON);

    public void ballsToShooter() {
        bpTalon.set(ControlMode.PercentOutput, .5);
    }

    public void ballsToIntake() {
        bpTalon.set(ControlMode.PercentOutput, -.5);
    }

    public void stopBalls() {
        bpTalon.set(ControlMode.PercentOutput, 0);
    }

}

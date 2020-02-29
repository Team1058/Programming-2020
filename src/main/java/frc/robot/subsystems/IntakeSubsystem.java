package frc.robot.subsystems;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeSubsystem {

    TalonSRX intakeLift = new TalonSRX(RobotMap.CANIds.INTAKE_TALON);
    VictorSPX intakeWheels = new VictorSPX(RobotMap.CANIds.INTAKE_VICTOR_WHEELS);
    boolean isUp = true;
    double ballSpeed = -1;
    double liftSlow = 0.1;
    double liftFast = 0.5;
    double dropSlow = -0.00;
    double dropFast = -0.2;

    public void initialize() {
        intakeLift.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.Disabled, 30);
        intakeLift.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.Disabled, 30);
        intakeLift.configReverseSoftLimitEnable(false);
        intakeLift.configForwardSoftLimitEnable(false);
    }

    public void updateIntake() {  
        // limit switches default to 0 when not pressed 
        // fwd = green wire = left bumper = makes it go down
        // rev = white wire = right bumper = makes it go up
        if (intakeLift.isFwdLimitSwitchClosed() == 1 && isUp == true) {
            intakeLift.set(ControlMode.PercentOutput, liftSlow);
            intakeWheels.set(ControlMode.PercentOutput, 0); 
        } else if (intakeLift.isFwdLimitSwitchClosed() == 0 && isUp == true) {
            intakeLift.set(ControlMode.PercentOutput, liftFast);
            intakeWheels.set(ControlMode.PercentOutput, 0); 
        } else if (intakeLift.isRevLimitSwitchClosed() == 1 && isUp == false) {
            intakeLift.set(ControlMode.PercentOutput, dropSlow);
            intakeWheels.set(ControlMode.PercentOutput, ballSpeed);
        } else if (intakeLift.isRevLimitSwitchClosed() == 0 && isUp == false) { 
            intakeLift.set(ControlMode.PercentOutput, dropFast);
            intakeWheels.set(ControlMode.PercentOutput, ballSpeed);
        } else {    
            intakeWheels.set(ControlMode.PercentOutput, 0);
            intakeLift.set(ControlMode.PercentOutput, 0);
        }      
    } 

    public void inferState() {
        if (intakeLift.isFwdLimitSwitchClosed() == 1) {
            isUp = true;
        } else {
            isUp = false;
        }
    }

	public void intakeGoUp() {
        isUp = true;
    }
    
    public void intakeGoDown() {
        isUp = false;
    }
}


package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Robot;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class IntakeSubsystem {
      //Initializes the victor
    private final VictorSPX stateVictor = new VictorSPX(RobotMap.CANIds.INTAKE_VICTOR_1);

    private VictorSPX intakeLift = new VictorSPX(RobotMap.CANIds.INTAKE_VICTOR_1);

    private VictorSPX intakeWheels = new VictorSPX(RobotMap.CANIds.INTAKE_VICTOR_WHEELS);

    //Variables Initialized
    double motorPercentSpeed = .5;

    public void initialize(){
       // intakeLift.set(CANSparkMax.IdleMode.kBrake);
    }

    public void intakeBalls(double speed){
        intakeWheels.set(ControlMode.PercentOutput, speed);
    }

    public void intakeSpit(double speed){
        intakeWheels.set(ControlMode.PercentOutput, speed);
    }

    public void intakeOff(){
        intakeWheels.set(ControlMode.PercentOutput, 0);
    }

    public void liftIntake(){
        intakeLift.set(ControlMode.PercentOutput, 0.2);    }

    public void dropIntake(){
        intakeLift.set(ControlMode.PercentOutput, -0.2);    }
}

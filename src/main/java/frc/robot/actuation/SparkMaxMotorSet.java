package frc.robot.actuation;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.interfaces.Spinnable;

public class SparkMaxMotorSet implements Spinnable {

    private CANSparkMax masterMotor;
    private CANSparkMax followerMotors[];
    private double maxOmega;
    private double gearRatio;
    private CANPIDController pidController;
    private CANEncoder masterEncoder;

    public SparkMaxMotorSet(int masterID, int followerIDs[]) {
        masterMotor = new CANSparkMax(masterID, MotorType.kBrushless);
        masterMotor.restoreFactoryDefaults();
        masterEncoder = masterMotor.getEncoder();
        followerMotors = new CANSparkMax[followerIDs.length];
        for (int i = 0; i < followerIDs.length; i++) {
            followerMotors[i] = new CANSparkMax(followerIDs[i], MotorType.kBrushless);
            followerMotors[i].restoreFactoryDefaults();
            followerMotors[i].follow(masterMotor, false);
            followerMotors[i].getEncoder();
        }
        pidController = masterMotor.getPIDController();
    }

    public void setGearRatio(double gearRatio) {
        this.gearRatio = gearRatio;
        masterEncoder.setVelocityConversionFactor((2 * Math.PI)/(60 * gearRatio));
         masterEncoder.setPositionConversionFactor((2 * Math.PI)/(gearRatio));
    }
    
    public SparkMaxMotorSet setPIDConstants(double kP, double kI, double kD, double kIz, double kFF) {
        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(kIz);
        pidController.setFF(kFF);
        
        return this;
    }

    public void setTargetVelocity(double omega) {
        pidController.setReference(omega, ControlType.kVelocity);
        SmartDashboard.putNumber(String.format("Applied Output-%d", masterMotor.getDeviceId()), masterMotor.getAppliedOutput());
    }

    public double getActualVelocity() {
        return masterEncoder.getVelocity();
    }

    public void setMaxOmega(double maxOmega) {
        this.maxOmega = maxOmega;
        pidController.setOutputRange(-maxOmega, maxOmega);
    }

    public SparkMaxMotorSet setIdleMode(CANSparkMax.IdleMode idleMode) {
        masterMotor.setIdleMode(idleMode);
        for (int i = 0; i < followerMotors.length; i++) {
            followerMotors[i].setIdleMode(idleMode);
        }

        return this;
    }

    public void setInverted(boolean inverted) {
        masterMotor.setInverted(inverted);
    }

    public double getPosition() {
        return masterEncoder.getPosition();
    }

}
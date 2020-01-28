package frc.robot.actuation;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.interfaces.Spinnable;

public class DifferentialDrive {

    private Spinnable rightDrive;
    private Spinnable leftDrive;
    private double trackWidth;
    private double wheelRadius;
    private double maxWheelOmega;
    private double x;
    private double y;
    private double theta;
    private double previousLeftPosition;
    private double previousRightPosition;

    public DifferentialDrive(Spinnable rightDrive, Spinnable leftDrive, double trackWidth, double wheelRadius, double maxWheelOmega) {
        this.rightDrive = rightDrive;
        this.leftDrive = leftDrive;
        this.trackWidth = trackWidth;
        this.wheelRadius = wheelRadius;
        this.maxWheelOmega = maxWheelOmega;
        previousLeftPosition = leftDrive.getPosition();
        previousRightPosition = rightDrive.getPosition();
        theta = 0;
        x = 0;
        y = 0;
    }

    public void setTargetVelocity(double vX, double omegaZ) {
        // vR is the velocity of the right motor set, vL is the velocity of the left motor set.
        double vR = vX + (omegaZ * (trackWidth/2));
        double vL = vX - (omegaZ * (trackWidth/2));
        double omegaR = vR/wheelRadius;
        double omegaL = vL/wheelRadius;
        double fastestOmega = Math.max(omegaR, omegaL);
        if (fastestOmega > maxWheelOmega) {
            omegaR *= maxWheelOmega/fastestOmega;
            omegaL *= maxWheelOmega/fastestOmega;
        }
        rightDrive.setTargetVelocity(omegaR);
        leftDrive.setTargetVelocity(omegaL);
        SmartDashboard.putNumber("omegaR", omegaR);
        SmartDashboard.putNumber("omegaL", omegaL);
        SmartDashboard.putNumber("rightDrive ActVel", rightDrive.getActualVelocity());
        SmartDashboard.putNumber("leftDrive ActVel", leftDrive.getActualVelocity());
    }

    public double getMaxOmegaZ() {
        double maxOmegaZ = ((2 * wheelRadius) * maxWheelOmega)/trackWidth;
        return maxOmegaZ;
    }

    public double getMaxVelocityX() {
        double maxVelocityX = wheelRadius * maxWheelOmega;
        return maxVelocityX;
    }

    public void updatePosition() {
        double leftPosition = leftDrive.getPosition();
        double rightPosition = rightDrive.getPosition();

        double deltaLeft = leftPosition - previousLeftPosition;
        double deltaRight = rightPosition - previousRightPosition;
        double deltaTheta = (deltaRight - deltaLeft)/trackWidth;

        theta += deltaTheta;
        x += ((deltaRight + deltaLeft) / 2) * Math.cos(theta + (deltaTheta/2));
        y += ((deltaRight + deltaLeft) / 2) * Math.sin(theta + (deltaTheta/2));

        previousLeftPosition = leftPosition;
        previousRightPosition = rightPosition;
    }
    
} 

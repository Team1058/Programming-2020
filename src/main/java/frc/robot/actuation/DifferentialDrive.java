package frc.robot.actuation;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.geometry.Pose;
import frc.robot.interfaces.Spinnable;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class DifferentialDrive {

    private Spinnable rightDrive;
    private Spinnable leftDrive;
    private double trackWidth;
    private double wheelRadius;
    private double maxWheelOmega;
    private double omegaR;
    private double omegaL;
    private double x;
    private double y;
    // theta: angle of the robot
    private double theta;
    private double previousLeftPosition;
    private double previousRightPosition;
    double highestSpeed;
    AHRS navx;

    public DifferentialDrive(Spinnable rightDrive, Spinnable leftDrive, double trackWidth, 
                             double wheelRadius, double maxWheelOmega) {
        this.rightDrive = rightDrive;
        this.leftDrive = leftDrive;
        this.trackWidth = trackWidth;
        this.wheelRadius = wheelRadius;
        this.maxWheelOmega = maxWheelOmega;
        this.omegaR = 0;
        this.omegaL = 0;
        x = 0;
        y = 0;
        theta = 0;
        previousLeftPosition = leftDrive.getPosition() * wheelRadius;
        previousRightPosition = rightDrive.getPosition() * wheelRadius;
        //resetOdometry();
        navx = new AHRS(SPI.Port.kMXP);
    }

    // omega: angular velocity
    // omegaZ: angular velocity about Z
    public void setTargetVelocity(double vX, double omegaZ) {
        // vR: is the linear velocity of the right motor set
        // vL: is the linear velocity of the left motor set
        double vR = vX + (omegaZ * (trackWidth/2));
        double vL = vX - (omegaZ * (trackWidth/2));
        double newOmegaR = vR/wheelRadius;
        double newOmegaL = vL/wheelRadius;
        double fastestOmega = Math.max(newOmegaR, newOmegaL);
        if (fastestOmega > maxWheelOmega) {
            newOmegaR *= maxWheelOmega/fastestOmega;
            newOmegaL *= maxWheelOmega/fastestOmega;
        }
        this.omegaR = newOmegaR;
        this.omegaL = newOmegaL;
        //SmartDashboard.putNumber("vX", vX);
        //SmartDashboard.putNumber("omegaZ", omegaZ);
    }

    public double getMaxOmegaZ() {
        double maxOmegaZ = ((2 * wheelRadius) * maxWheelOmega)/trackWidth;
        return maxOmegaZ;
    }

    public double getMaxVelocityX() {
        double maxVelocityX = wheelRadius * maxWheelOmega;
        return maxVelocityX;
    }

    
    public double getTrackWidth() {
        return trackWidth;
    }

    public double getWheelRadius() {
        return wheelRadius;
    }

    public double normalizeTheta(double theta) {
        theta += Math.PI;
        theta %= (2 * Math.PI);
        theta -= Math.PI;

        return theta;
    }

    public void updatePosition() {
        double leftPosition = leftDrive.getPosition() * wheelRadius;
        double rightPosition = rightDrive.getPosition() * wheelRadius;
        // delta: change since last update
        double deltaLeft = leftPosition - previousLeftPosition;
        double deltaRight = rightPosition - previousRightPosition;

        theta = -navx.getYaw() / 180 * Math.PI;
        x += ((deltaRight + deltaLeft)/2) * Math.cos(theta);
        y += ((deltaRight + deltaLeft)/2) * Math.sin(theta);

        previousLeftPosition = leftPosition;
        previousRightPosition = rightPosition;

        //SmartDashboard.putNumber("rightDrive ActVel", rightDrive.getActualVelocity());
        //SmartDashboard.putNumber("leftDrive ActVel", leftDrive.getActualVelocity());
        //SmartDashboard.putNumber("driveTrain X", x);
        //SmartDashboard.putNumber("driveTrain Y", y);
        //SmartDashboard.putNumber("driveTrain Theta", theta);
    }
    
    public Pose getPose() {
        return new Pose(x, y, 0, theta, 0, 0);
    }

    public void resetOdometry() {
        x = 0;
        y = 0;
        theta = 0;
        previousLeftPosition = leftDrive.getPosition() * wheelRadius;
        previousRightPosition = rightDrive.getPosition() * wheelRadius;
        navx.reset();
    }

    public void getInputs() {
        updatePosition();
    }

    public void setOutputs() {
        rightDrive.setTargetVelocity(omegaR);
        leftDrive.setTargetVelocity(omegaL);
        //SmartDashboard.putNumber("rightDrive SetVel", omegaR);
        //SmartDashboard.putNumber("leftDrive SetVel", omegaL);
    }

    public int getLeftEncoderTicks() {
        int leftEncoderTicks =  (int) (leftDrive.getPosition() / (2 *Math.PI) * 42);

        return leftEncoderTicks;
    }

    public int getRightEncoderTicks() {
        int rightEncoderTicks = (int) (rightDrive.getPosition() / (2 * Math.PI) * 42);

        return rightEncoderTicks;
    }

    public void setPercentVelocity(double left, double right) {
        omegaL = left * maxWheelOmega;
        omegaR = right * maxWheelOmega;
    }
} 
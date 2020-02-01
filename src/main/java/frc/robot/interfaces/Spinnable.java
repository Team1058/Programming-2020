package frc.robot.interfaces;

public interface Spinnable {

    /**
     * Set target angular velocity of an underlying motor or device.
     * @param omega Angular velocity in rad/s
     */
    void setTargetVelocity(double omega);

    double getActualVelocity();

    double getPosition();

}

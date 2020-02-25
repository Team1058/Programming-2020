package frc.robot.geometry;

public class Pose {

    final double x;
    final double y;
    final double z;
    final double yaw;
    final double pitch;
    final double roll;

    public Pose(double x, double y, double z, double yaw, double pitch, double roll) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getYaw() {
        return yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public double getRoll() {
        return roll;
    }
}
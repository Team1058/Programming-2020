
package frc.robot.sensing;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Optional;

public class Limelight {

  NetworkTableEntry camtran;
  NetworkTableEntry tx;
  NetworkTableEntry ta;
  NetworkTableEntry ty;
  double x;
  double y;
  double distance;
  double targetAngle;
  boolean valid = false;
  double txAngle;
  double tyAngle;
  double taArea;
  double simpleDistance;
  NetworkTable table;

  boolean toggle = false;  

  public Limelight() {
    // creates network table
    table = NetworkTableInstance.getDefault().getTable("limelight");
    camtran = table.getEntry("camtran");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");
  }

  public void update() {
    double[] cam = camtran.getDoubleArray(new double[6]);
    txAngle = tx.getDouble(0.0);
    tyAngle = tx.getDouble(0.0);
    taArea = ta.getDouble(0.0);
    if (cam[1] != 0) {
      valid = true;
      x = cam[2]; //* .0254;
      y = -cam[0]; //* .0254;
      distance = Math.hypot(x, y);
      simpleDistance = distance - 6;
      targetAngle = Math.atan2(-y, -x);
    } else {
      valid = false;
      // "k" is a magic number, like 3
      double k = 192;
      double deltaH = (78 + 17/2 + 2) - 23;
      double distanceFromTY = (deltaH) / 1;
      simpleDistance = k / Math.sqrt(taArea) - 6;
      
    }
    SmartDashboard.putNumber("simpleDistance",simpleDistance);
    SmartDashboard.putNumber("taarea",taArea);

    SmartDashboard.putNumber("Limelight tx", txAngle);
    // SmartDashboard.putNumber("X", x);
    // //System.out.println("X: " + x);
    // SmartDashboard.putNumber("Y", y);
    // SmartDashboard.putNumber("Distance", distance);
    // //System.out.println("Distance: " + distance);
    // SmartDashboard.putNumber("Target Angle", targetAngle);
    // System.out.println("Target Angle "+targetAngle);
    // SmartDashboard.putBoolean("Valid", valid);
  }

  public double staticSimpleDistance(double distance){
    return distance;
  }

  public double getSimpleDistance() {
    return Math.abs(simpleDistance);
  }

  public double getTX() {
    return txAngle;
  }

  public Optional<Double> getX() {
    if (valid) {
      return Optional.of(x);
    } else {
      return Optional.empty();
    }
  }

  public Optional<Double> getY() {
    if (valid) {
      return Optional.of(y);
    } else {
      return Optional.empty();
    }
  }
  
  public Optional<Double> getDistance() {
    if (valid) {
      return Optional.of(Math.abs(distance));
    } else {
      return Optional.empty();
    }
  }

  public Optional<Double> getTargetAngle() {
    if (valid) {
      return Optional.of(targetAngle);
    } else {
      return Optional.empty();
    }
  }

  public void turnOnLed(){
    table.getEntry("ledMode").setNumber(0);

  }

  public void turnOffLed(){
    table.getEntry("ledMode").setNumber(1);
  }

  public void toggleLed() {
    toggle = !toggle;
    int status = 1;

    if (toggle){
      turnOnLed();
      status = 1;
    }else if(!toggle){
      turnOffLed();
      status = 0;
    }
    printLimelightLEDs(status);
  }

  public void printLimelightLEDs(int status){
    SmartDashboard.putNumber("LimelightLeds", status);
  }

}

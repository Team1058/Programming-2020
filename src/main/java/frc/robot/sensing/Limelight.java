
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
    tyAngle = ty.getDouble(0.0);
    taArea = ta.getDouble(0.0);
    if (cam[1] != 0) {
      valid = true;
      x = cam[2]; //* .0254;
      y = -cam[0]; //* .0254;
      distance = Math.hypot(x, y);
      simpleDistance = distance - 6;
      targetAngle = Math.atan2(-y, -x);

      double k = 192;
      double llPitch = 21.7;
      double deltaH = 89.75;
      double distanceFromTY = ((deltaH) / Math.tan(Math.toRadians(llPitch + tyAngle))) - 6;
      double distanceFromTA = k / Math.sqrt(taArea) - 6;
      double taDistance = distanceFromTA;
      double tyDistance = distanceFromTY;
      double avgDistance = ((distanceFromTA + distanceFromTY) / 2);
      SmartDashboard.putNumber("ty", tyAngle);
      SmartDashboard.putNumber("Limelight ta Distance", taDistance);
      SmartDashboard.putNumber("Limelight ty Distance", tyDistance);
      SmartDashboard.putNumber("Limelight avg Distance", avgDistance);
    } else {
      valid = false;
      // "k" is a magic number, like 3
      double k = 192;
      double llPitch = 21.7;
      double deltaH = 89.75 - 21;
      double distanceFromTY = (deltaH) / Math.tan(Math.toRadians(llPitch + tyAngle));
      simpleDistance = k / Math.sqrt(taArea) - 6;
      double taDistance = simpleDistance;
      double tyDistance = distanceFromTY;
      double avgDistance = (simpleDistance + distanceFromTY) / 2;
      SmartDashboard.putNumber("ty", tyAngle);
      SmartDashboard.putNumber("Limelight ta Distance", taDistance);
      SmartDashboard.putNumber("Limelight ty Distance", tyDistance);
      SmartDashboard.putNumber("Limelight avg Distance", avgDistance);
    }

    SmartDashboard.putNumber("Limelight simple Distance",simpleDistance);
    SmartDashboard.putNumber("Limelight ta",taArea);
    SmartDashboard.putNumber("Limelight tx", txAngle);
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

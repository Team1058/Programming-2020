/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotMap;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;

import java.util.Hashtable;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.ColorMatch;

public class SpinnerSubsystem {

  private final double THROTTLE_VAULE = .5;

  //Initializes the victor
  private final VictorSPX spinnerVictor = new VictorSPX(RobotMap.CANIds.SPINNER);

  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  //Hashtables are for conversions
  Hashtable<String,String> fieldObjToRobotObj = new Hashtable<String,String>();
  Hashtable<String,Integer> desiredDirection = new Hashtable<String,Integer>();

  //variables initialized
  Boolean stageThreeColorChecked = false;
  Boolean stageTwoColorChecked = false;
  String trackedColor = "Nothing";
  Integer totalTimesSeen = 0;
  Integer intDirection = 1;
  double motorPercentSpeed = .5;

  /**
   * A Rev Color Sensor V3 object is constructed with an I2C port as a 
   * parameter. The device will be automatically initialized with default 
   * parameters.
   */
  private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);

  /**
   * A Rev Color Match object is used to register and detect known colors. This can 
   * be calibrated ahead of time or during operation.
   * 
   * This object uses a simple euclidian distance to estimate the closest match
   * with given confidence range.
   */
  private final ColorMatch colorMatcher = new ColorMatch();
  
  /**
   * Note: Any example colors should be calibrated as the user needs, these
   * are here as a basic example.
   */
  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
  private final Color kDefaultColor = ColorMatch.makeColor(0.327, 0.469, 0.203);

  public void initialize () {

    //Adds the colors to the colorMatcher
    colorMatcher.addColorMatch(kBlueTarget);
    colorMatcher.addColorMatch(kGreenTarget);
    colorMatcher.addColorMatch(kRedTarget);
    colorMatcher.addColorMatch(kYellowTarget);
    colorMatcher.addColorMatch(kDefaultColor);

    /**
     * Converts the color the field requires into the color the robot
     * needs to spin to.
     */
    fieldObjToRobotObj.put("G","Yellow");
    fieldObjToRobotObj.put("R","Blue");
    fieldObjToRobotObj.put("Y","Green");
    fieldObjToRobotObj.put("B","Red");

    /** 
     * Creates a value which sets the motor to spin clockwise or counter-clockwise
     * based on the color the robot sees and what it needs to spin to.
     */ 
    desiredDirection.put("OnGreenFindRed",-1);
    desiredDirection.put("OnGreenFindYellow",1);
    desiredDirection.put("OnGreenFindBlue",1);
    desiredDirection.put("OnRedFindGreen",1);
    desiredDirection.put("OnRedFindYellow",-1);
    desiredDirection.put("OnRedFindBlue",1);
    desiredDirection.put("OnYellowFindGreen",1);
    desiredDirection.put("OnYellowFindRed",1);
    desiredDirection.put("OnYellowFindBlue",-1);
    desiredDirection.put("OnBlueFindGreen",-1);
    desiredDirection.put("OnBlueFindRed",1);
    desiredDirection.put("OnBlueFindYellow",1);

  } 

  public String getSeenColor () {
    /*
     * The method GetColor() returns a normalized color value from the sensor and can be
     * useful if outputting the color to an RGB LED or similar. To
     * read the raw color, use GetRawColor().
     * 
     * The color sensor works best when within a few inches from an object in
     * well lit conditions (the built in LED is a big help here!). The farther
     * an object is the more light from the surroundings will bleed into the 
     * measurements and make it difficult to accurately determine its color.
     */
    Color detectedColor = colorSensor.getColor();

    /**
     * Run the color match algorithm on our detected color
     */
    
    ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

    String colorString = "";

    if (match.color == kBlueTarget) {
        colorString = "Blue";
    } else if (match.color == kRedTarget) {
        colorString = "Red";
    } else if (match.color == kGreenTarget) {
        colorString = "Green";
    } else if (match.color == kYellowTarget) {
        colorString = "Yellow";
    } else {
        colorString = "Unknown";
    }
    
    return colorString;
  }

  // Gets the FMS code from the driver station and converts it to what the robot should see when the field sees the FMS color
  public String getRobotObj() {

    String gameData;
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    String robotObj = "";

    if (gameData.length() > 0) {
      String gameDataFMS = gameData.substring(0, 1);
      robotObj = fieldObjToRobotObj.getOrDefault(gameDataFMS, "");
    }

    return robotObj;
  }

  //Stage 2 Has a bug where if you drive up to the spinner while holding X button and color sensor is Unknown the number of times is inconsistent
  //This could be because it's using unkown as the wanted color
  public void spinForStageTwo() {
    String currentColor = this.getSeenColor();

    if (trackedColor.equals("Nothing")) {
      trackedColor = currentColor;
    }
    
    if (stageTwoColorChecked == false) {
      if (currentColor.equals(this.trackedColor)) {
        totalTimesSeen = totalTimesSeen + 1;
        stageTwoColorChecked = true;
      }
    }

    //6 times done for 3 full rotations
    if (totalTimesSeen < 7) {
      spinnerVictor.set(ControlMode.PercentOutput, motorPercentSpeed);
    } else if (totalTimesSeen >= 7) {
      this.stopMotor();
    }

    //When it has seen the color it waits until it no longer sees the color to reset the check
    if (stageTwoColorChecked == true) {
      if (!currentColor.equals(this.trackedColor)) {
        stageTwoColorChecked = false;
      }
    }

  }

  public void spinForStageThree() {

    String robotObj = getRobotObj();
    String seenColor = this.getSeenColor();
    String spinInstructions = "On" + seenColor + "Find" + robotObj;

    if (stageThreeColorChecked == false) {
      intDirection = desiredDirection.get(spinInstructions);
  
      if (intDirection == null) {
        intDirection = 1;
      }
      stageThreeColorChecked = true;
    }
  
    //if the seen color isn't the color we should see it rotates when it is the color we should see it resets
    // the color check and stops the motor
    if (!seenColor.equals(robotObj)) {
      spinnerVictor.set(ControlMode.PercentOutput, intDirection * motorPercentSpeed);
    } else if (seenColor.equals(robotObj)) {
      this.stopMotor();
      stageThreeColorChecked = false;
    }
    
  }

  public void stopMotor() {
    spinnerVictor.set(ControlMode.PercentOutput, 0);
  }

  public void resetColorChecks() {
    stageTwoColorChecked = false;
    stageThreeColorChecked = false;
    totalTimesSeen = 0;
  }

  public void setTrackedColor() {
    trackedColor = (this.getSeenColor());
  }

}
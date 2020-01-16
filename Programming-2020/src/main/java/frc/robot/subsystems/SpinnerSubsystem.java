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
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotMap;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;

import java.util.Hashtable;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.ColorMatch;
/**
 * Add your docs here.
 */
public class SpinnerSubsystem {

  private final double THROTTLE_VAULE = .5;

  private final VictorSPX spinnerVictor = new VictorSPX(RobotMap.CANIds.SPINNER);

  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  Hashtable<String,String> fieldObjToRobotObj = new Hashtable<String,String>();
  Hashtable<String,Integer> desiredDirection = new Hashtable<String,Integer>();

  Boolean stageThreeColorChecked = false;
  Boolean stageTwoColorChecked = false;
  String trackedColor = "Nothing";
  Integer totalTimesSeen = 0;
  Integer intDirection = 1;

  /**
   * A Rev Color Sensor V3 object is constructed with an I2C port as a 
   * parameter. The device will be automatically initialized with default 
   * parameters.
   */
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  /**
   * A Rev Color Match object is used to register and detect known colors. This can 
   * be calibrated ahead of time or during operation.
   * 
   * This object uses a simple euclidian distance to estimate the closest match
   * with given confidence range.
   */
  private final ColorMatch m_colorMatcher = new ColorMatch();
  
  /**
   * Note: Any example colors should be calibrated as the user needs, these
   * are here as a basic example.
   */
  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
  private final Color kDefaultColor = ColorMatch.makeColor(0.327, 0.469, 0.203);

  public void initialize (){

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
    m_colorMatcher.addColorMatch(kDefaultColor);

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

  public String getSeenColor (){
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
    Color detectedColor = m_colorSensor.getColor();

    /**
     * Run the color match algorithm on our detected color
     */
    
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

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

    /**
    * Open Smart Dashboard or Shuffleboard to see the color detected by the 
    * sensor.
    */
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);
    
    return colorString;
  }

  public String getRobotObj(){

    String gameData;
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    String robotObj = "";

    if(gameData.length() > 0){
      switch(gameData.charAt(0)){
        case 'B':
          robotObj = fieldObjToRobotObj.get("B");
          break;
        case 'G':
          robotObj = fieldObjToRobotObj.get("G");
          break;
        case 'R':
          robotObj = fieldObjToRobotObj.get("R");
          break;
        case 'Y':
          robotObj = fieldObjToRobotObj.get("Y");
          break;
        default: 
          break;
      }
    }

    return robotObj;
  }
  public void spinForStageTwo(){
    String currentColor = this.getSeenColor();

    if(trackedColor.equals("Nothing")){
      trackedColor = currentColor;
    }
    
    if(stageTwoColorChecked == false){
      if(currentColor.equals(trackedColor)){
        totalTimesSeen = totalTimesSeen + 1;
        stageTwoColorChecked = true;
      }else{
        stageTwoColorChecked = false;
      }
    }

    if(totalTimesSeen < 6){
      spinnerVictor.set(ControlMode.PercentOutput, .5);
    }else if(totalTimesSeen == 6){
      this.stopMotor();
    }
  }

  public void spinForStageThree(){
    String robotObj = getRobotObj();
    String seenColor = this.getSeenColor();
    String spinInstructions = "On" + seenColor + "Find" + robotObj;

    System.out.println(spinInstructions);
    if(stageThreeColorChecked == false){
      intDirection = desiredDirection.get(spinInstructions);
      System.out.println("desiredDirection" + desiredDirection.get(spinInstructions));
  
      if(intDirection == null){
        intDirection = 1;
      }
      stageThreeColorChecked = true;
    }
  
    System.out.println("motor direction: " + intDirection);
    System.out.println("seenColor: " + seenColor);
    System.out.println("robotObj:      " + robotObj);
  
    if(!seenColor.equals(robotObj)){
      spinnerVictor.set(ControlMode.PercentOutput, intDirection * .5);
    }else if(seenColor.equals(robotObj)){
      this.stopMotor();
      stageThreeColorChecked = false;
    }
    
  }

  public void stopMotor(){
    spinnerVictor.set(ControlMode.PercentOutput, 0);
  }

}
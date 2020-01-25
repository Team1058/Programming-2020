/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.SpinnerSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.IndividualLeds;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

  public static SpinnerSubsystem spinnerSubsystem = new SpinnerSubsystem();
  LEDSubsystem ledSubsystem = new LEDSubsystem();
  public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
  IndividualLeds individualLeds = new IndividualLeds();
  public static ClimberSubsystem climberSubsystem = new ClimberSubsystem();

  @Override
  public void robotInit() {
    spinnerSubsystem.initialize();
    driveTrainSubsystem.initialize();
    climberSubsystem.initialize();
  }

  @Override
  public void teleopPeriodic() {
<<<<<<< HEAD:src/main/java/frc/robot/Robot.java
    ledSubsystem.setLEDColor(spinnerSubsystem.getSeenColor());
=======
    int R1 = (int) SmartDashboard.getNumber("R1", 0);
    int G1 = (int) SmartDashboard.getNumber("G1", 0);
    int B1 = (int) SmartDashboard.getNumber("B1", 0);
    int R2 = (int) SmartDashboard.getNumber("R2", 0);
    int G2 = (int) SmartDashboard.getNumber("G2", 0);
    int B2 = (int) SmartDashboard.getNumber("B2", 0);
    int R3 = (int) SmartDashboard.getNumber("R3", 0);
    int G3 = (int) SmartDashboard.getNumber("G3", 0);
    int B3 = (int) SmartDashboard.getNumber("B3", 0);
    int percentOn = (int) SmartDashboard.getNumber("leds with rgb1 color", 0);
    int x = (int) SmartDashboard.getNumber("x", 0);
    int y = (int) SmartDashboard.getNumber("y", 0);
    individualLeds.climbLeds(R1, G1, B1, R2, G2, B2, percentOn);;
    ledSubsystem.setLEDColor(spinnerSubsystem.getColor());
>>>>>>> made Leds individually addressable:Programming-2020/src/main/java/frc/robot/Robot.java
    // TODO: Figure out how we want to dispatch commands
<<<<<<< HEAD
=======
    gamepad.turnToColor();
    gamepad.splitArcadeDrive();
    climberSubsystem.printGyroPos();
>>>>>>> Sams skeleton code work
  } 

  @Override
  public void disabledPeriodic(){
    individualLeds.changeAllColors(0,0,0);
  }

}
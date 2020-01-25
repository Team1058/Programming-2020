/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.SpinnerSubsystem;
import frc.robot.gamepads.Gamepad;
import frc.robot.subsystems.IndividualLeds;
import frc.robot.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

  public static SpinnerSubsystem spinnerSubsystem = new SpinnerSubsystem();
  Gamepad gamepad = new Gamepad();
  LEDSubsystem ledSubsystem = new LEDSubsystem();
  IndividualLeds individualLeds = new IndividualLeds();

  @Override
  public void robotInit() {
    SmartDashboard.putNumber("R1", 0);
    SmartDashboard.putNumber("G1", 0);
    SmartDashboard.putNumber("B1", 0);
    SmartDashboard.putNumber("R2", 0);
    SmartDashboard.putNumber("B2", 0);
    SmartDashboard.putNumber("G2", 0);
    SmartDashboard.putNumber("R3", 0);
    SmartDashboard.putNumber("B3", 0);
    SmartDashboard.putNumber("G3", 0);
    SmartDashboard.putNumber("#ofMovingLEDS", 0);
    SmartDashboard.putNumber("x", 0);
    SmartDashboard.putNumber("y", 0);
    spinnerSubsystem.initialize();
   
  }

  @Override
  public void teleopPeriodic() {
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
    // TODO: Figure out how we want to dispatch commands
    gamepad.turnToColor();
  }

  @Override
  public void disabledPeriodic(){
    individualLeds.changeAllColors(0,0,0);
  }

}
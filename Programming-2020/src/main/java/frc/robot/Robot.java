/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.ColorSubsystem;
import frc.robot.subsystems.LEDSubsystem;

public class Robot extends TimedRobot {

  ColorSubsystem colorSubsystem = new ColorSubsystem();
  LEDSubsystem ledSubsystem = new LEDSubsystem();
  @Override
  public void robotInit() {
   
    colorSubsystem.initialize();
   
  }

  @Override
  public void robotPeriodic() {
    System.out.println(colorSubsystem.getColor());
    ledSubsystem.setLEDColor(colorSubsystem.getColor());

  }
}
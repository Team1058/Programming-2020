/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.ColorSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
// import sun.tools.asm.CatchData;

public class Robot extends TimedRobot {

  ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  ColorSubsystem colorSubsystem = new ColorSubsystem();
  LEDSubsystem ledSubsystem = new LEDSubsystem();
  // PrintWriter writer = new PrintWriter("/tmp/RPM_Values.csv");
  @Override
  public void teleopInit() {
    shooterSubsystem.filecreate();
  }

  @Override
  public void robotPeriodic() {
  }
 
  @Override
  public void teleopPeriodic()
  {
    // System.out.println(colorSubsystem.getColor());
    // ledSubsystem.setLEDColor(colorSubsystem.getColor());
    shooterSubsystem.Encoder();
    
  }
 
  
}
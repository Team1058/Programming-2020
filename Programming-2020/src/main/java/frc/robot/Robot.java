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
import java.io.BufferedWriter;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.ColorSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
// import sun.tools.asm.CatchData;

public class Robot extends TimedRobot {

  ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  ColorSubsystem colorSubsystem = new ColorSubsystem();
  LEDSubsystem ledSubsystem = new LEDSubsystem();
  @Override
  public void teleopInit() {
   
    try {
      File file = new File ("/tmp/RPM_Values.csv");
    if(file.createNewFile()){
      System.out.println("created");

    }
    else{
      System.out.println("already exists");
    }
    PrintWriter writer = new PrintWriter("/tmp/RPM_Values.csv");
    writer.print("");
    writer.print("Device ID");
    writer.print(",");
    writer.print("RPM");
    writer.print(",");
    writer.print("Percent Output");
    writer.print(",");
    writer.print("Time");
    writer.close();

    } 
    catch (Exception e) {
      System.out.println("error");
      e.printStackTrace();
    }

   // colorSubsystem.initialize();
   
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
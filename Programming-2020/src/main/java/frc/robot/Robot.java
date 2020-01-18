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
import frc.robot.subsystems.SpinnerSubsystem;
import frc.robot.gamepads.Gamepad;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
//import frc.robot.subsystems.ShooterSubsystem;
// import sun.tools.asm.CatchData;

public class Robot extends TimedRobot {

  public static SpinnerSubsystem spinnerSubsystem = new SpinnerSubsystem();
  Gamepad gamepad = new Gamepad();
  LEDSubsystem ledSubsystem = new LEDSubsystem();
  public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();

  @Override
  public void robotInit() {
   
    spinnerSubsystem.initialize();
    driveTrainSubsystem.initialize();
   
  }

  @Override
  public void teleopPeriodic() {
    ledSubsystem.setLEDColor(spinnerSubsystem.getColor());
    // TODO: Figure out how we want to dispatch commands
    gamepad.turnToColor();
    gamepad.ArcadeDrive();
  }

}
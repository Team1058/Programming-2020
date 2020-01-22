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

public class Robot extends TimedRobot {

  public static SpinnerSubsystem spinnerSubsystem = new SpinnerSubsystem();
  Gamepad gamepad = new Gamepad();
  LEDSubsystem ledSubsystem = new LEDSubsystem();
  IndividualLeds individualLeds = new IndividualLeds();

  @Override
  public void robotInit() {
   
    spinnerSubsystem.initialize();
   
  }

  @Override
  public void teleopPeriodic() {
    individualLeds.scrollNColorsWithBackround(255,0,0,255,255,255,20);
    ledSubsystem.setLEDColor(spinnerSubsystem.getColor());
    // TODO: Figure out how we want to dispatch commands
    gamepad.turnToColor();
  }

  @Override
  public void disabledPeriodic(){
    individualLeds.changeAllColors(0,0,0);
  }

}
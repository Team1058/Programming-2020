/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.SpinnerSubsystem;
import frc.robot.gamepads.Driver;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IndividualLeds;

public class Robot extends TimedRobot {

  public static SpinnerSubsystem spinnerSubsystem = new SpinnerSubsystem();
<<<<<<< HEAD
  LEDSubsystem ledSubsystem = new LEDSubsystem();
=======
  Gamepad gamepad = new Gamepad();
  public static LEDSubsystem ledSubsystem = new LEDSubsystem();
  public static IndividualLeds individualLeds = new IndividualLeds();
>>>>>>> climberLEDs
  public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
  public static ClimberSubsystem climberSubsystem = new ClimberSubsystem();

  @Override
  public void robotInit() {
    spinnerSubsystem.initialize();
    driveTrainSubsystem.initialize();
    climberSubsystem.initialize();
  }

  @Override
  public void teleopPeriodic() {
    //individualLeds.climbLeds( 255, 255, 255, 255, 0, 0);
    individualLeds.climbBalance(climberSubsystem.balanceLED());
    // TODO: Figure out how we want to dispatch commands
  } 

}
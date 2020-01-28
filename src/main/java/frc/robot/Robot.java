/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.SpinnerSubsystem;
<<<<<<< HEAD
<<<<<<< HEAD
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.IndividualLeds;
=======
import frc.robot.gamepads.Driver;
>>>>>>> attempt at Caden's failed drive
=======
import frc.robot.gamepads.Gamepad;
>>>>>>> Push so I can PR gamepad
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IndividualLeds;

public class Robot extends TimedRobot {

  public static SpinnerSubsystem spinnerSubsystem = new SpinnerSubsystem();
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
  //Gamepad gamepad = new Gamepad();
>>>>>>> attempt at Caden's failed drive
=======
>>>>>>> Getting ready for merge
  LEDSubsystem ledSubsystem = new LEDSubsystem();
=======
  Gamepad gamepad = new Gamepad();
  public static LEDSubsystem ledSubsystem = new LEDSubsystem();
  public static IndividualLeds individualLeds = new IndividualLeds();
>>>>>>> Push so I can PR gamepad
=======
  public static LEDSubsystem ledSubsystem = new LEDSubsystem();
  public static IndividualLeds individualLeds = new IndividualLeds();
>>>>>>> Sorry Matt but Sree gave permission
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
<<<<<<< HEAD
<<<<<<< HEAD
    ledSubsystem.setLEDColor(spinnerSubsystem.getSeenColor());
    
    individualLeds.climbBalance(climberSubsystem.balanceLED());

    // TODO: Figure out how we want to dispatch commands

=======
    // ledSubsystem.setLEDColor(spinnerSubsystem.getSeenColor());
=======
    //individualLeds.climbLeds( 255, 255, 255, 255, 0, 0);
    individualLeds.climbBalance(climberSubsystem.balanceLED());
>>>>>>> Push so I can PR gamepad
    // TODO: Figure out how we want to dispatch commands
<<<<<<< HEAD
<<<<<<< HEAD
    gamepad.turnToColor();
    gamepad.splitArcadeDrive();
<<<<<<< HEAD
    individualLeds.climbBalance(climberSubsystem.balanceLED());
>>>>>>> Fixed issues before final commit
=======
    driveTrainSubsystem.VideoGameDrive();
    //gamepad.turnToColor();
    //gamepad.splitArcadeDrive();
>>>>>>> attempt at Caden's failed drive
=======
>>>>>>> Push so I can PR gamepad
=======
>>>>>>> Getting ready for merge
  } 

}
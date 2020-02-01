/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SpinnerSubsystem;
import frc.robot.gamepads.Driver;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IndividualLeds;
import frc.robot.gamepads.Driver;

public class Robot extends TimedRobot {

  public static SpinnerSubsystem spinnerSubsystem = new SpinnerSubsystem();
  public static LEDSubsystem ledSubsystem = new LEDSubsystem();
  public static IndividualLeds individualLeds = new IndividualLeds();
  public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
  public static ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  public static Driver driver = new Driver();

  @Override
  public void robotInit() {
    spinnerSubsystem.initialize();
    driveTrainSubsystem.initialize();
    climberSubsystem.initialize();
    SmartDashboard.putNumber("Speeeed",0);
  }

  @Override
  public void teleopPeriodic() {
    int d = climberSubsystem.falcon1.getSelectedSensorPosition();
    int r = (int) SmartDashboard.getNumber("Speeeed",0);
    climberSubsystem.climberExtend(r);
    
    System.out.println("Falcon"+d);
    individualLeds.teloscipsLeds1( 255, 0, 0, 0, 0, 0, d);
    // individualLeds.climbBalance(climberSubsystem.balanceLED());
    // climberSubsystem.balanceLED(), climberSubsystem.falcon1(), climberSubsystem.falcon2();
    // TODO: Figure out how we want to dispatch commands
  } 

}
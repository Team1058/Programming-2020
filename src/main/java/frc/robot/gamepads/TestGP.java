/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.gamepads;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.autonomous.MotionPlanner;

public class TestGP {

    private boolean testGPEnabled = false;
    private XboxController gamepad;
    private final double DEADBAND_VALUE = 0.025;
    private MotionPlanner motionPlanner;

    public TestGP(int gamepadNum, MotionPlanner motionPlanner) {
        this.motionPlanner = motionPlanner;
        gamepad = new XboxController(gamepadNum);
    }

    public void testDrive() {

        if (gamepad.getXButtonPressed()){
            motionPlanner.moveTo(1, 1, Math.PI/2);
        }

        if (gamepad.getBumperPressed(Hand.kRight)) {
            Robot.driveTrainSubsystem.getDrivetrain().resetOdometry();
        }

        motionPlanner.followPath();
    }

    public boolean isTestGPEnabled() {
        if (gamepad.getStartButtonPressed()) {
            System.out.println("start button pressed");
            testGPEnabled = true;
        } else if (gamepad.getBackButtonPressed()){
            System.out.println("back button pressed");
            testGPEnabled = false;
        }
        SmartDashboard.putBoolean("TestGPEnabled", testGPEnabled);

        return testGPEnabled;
    }
    
}

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
    boolean forward;

    public TestGP(int gamepadNum, MotionPlanner motionPlanner) {
        this.motionPlanner = motionPlanner;
        gamepad = new XboxController(gamepadNum);
        forward = true;
    }

    public void testDrive() {
        
        if (gamepad.getXButtonPressed()){
            motionPlanner.moveTo(1, -1.5, -Math.PI / 4,forward);
        }

        if (gamepad.getBumperPressed(Hand.kRight)) {
            Robot.driveTrainSubsystem.getDrivetrain().resetOdometry();
            motionPlanner.resetNAVX();
        }

        if (gamepad.getAButtonPressed()){
            forward = true;
        }

        if (gamepad.getBButtonPressed()){
            forward = false;
        }
        //motionPlanner.followPath();
        if (forward){
            motionPlanner.forwardPath();
        }else if (!forward){
            motionPlanner.reversePath();
        }
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

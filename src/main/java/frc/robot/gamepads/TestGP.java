/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.gamepads;

import edu.wpi.first.wpilibj.XboxController;

public class TestGP {

    private boolean enable = false;
    private XboxController gamepad = new XboxController(0);
    private final double DEADBAND_VALUE = 0.025;

    public void enablePathfinder(){
        
    }

    public boolean changeGamepadState() {
        if (gamepad.getBackButtonPressed()) {
            enable = true;
        } else if (gamepad.getStartButtonPressed()) {
            enable = false;
        }

        return enable;
    }

}

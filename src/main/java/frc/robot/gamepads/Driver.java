package frc.robot.gamepads;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;
import frc.robot.subsystems.DriveTrainSubsystem;

/* Driver Controls
    - Split Arcade Mode (Left Joy - Y  Right Joy - X)
    - Moving on bar (Lt - left Rt - Right) - hold 
    - Climber Up (Y) - hold
    - Climber Down (A) - hold */
    
public class Driver {
    private final double DEADBAND_VALUE = 0.05;

    private XboxController gamepad;
    private DriveTrainSubsystem drivetrain;

    public Driver(int gamepadNum, DriveTrainSubsystem drivetrain) {
        this.drivetrain = drivetrain;
        gamepad = new XboxController(gamepadNum);
    }
   
    public void tankDrive() {
        double leftJoystickY = clampDeadband(gamepad.getY(Hand.kLeft));
        double rightJoystickY = clampDeadband(gamepad.getY(Hand.kRight));

        /* Drive stright if L2 is pressed */
        if (outsideDeadband(gamepad.getTriggerAxis(Hand.kLeft))){
            leftJoystickY = (rightJoystickY + leftJoystickY) / 2;
            rightJoystickY = leftJoystickY;
        }

        if (gamepad.getBumper(Hand.kRight)) {
            leftJoystickY *= .5;
            rightJoystickY *= .5;
        } else if (gamepad.getBumper(Hand.kLeft)) {
            leftJoystickY *= .25;
            rightJoystickY *= .25;
        }

        drivetrain.setTankDrive(leftJoystickY,  rightJoystickY);
    }

    public void videoGameDrive() {

        double rightTrigger = clampTriggerDeadband(gamepad.getTriggerAxis(Hand.kRight));
        double leftTrigger = clampTriggerDeadband(gamepad.getTriggerAxis(Hand.kLeft));
        double speed = rightTrigger - leftTrigger;
        double turn = -clampDeadband(gamepad.getX(Hand.kLeft));

        drivetrain.setArcadeDrive(speed, turn * .25);

    }
    
    public void splitArcadeDrive() {
        double vX = -clampDeadband(gamepad.getY(Hand.kLeft));
        double omegaZ = -clampDeadband(gamepad.getX(Hand.kRight));

        SmartDashboard.putNumber("Left Joystick", vX);
        SmartDashboard.putNumber("Right Joystick", omegaZ);

        if (gamepad.getBumper(Hand.kRight)) {
            vX *= 0.5;
            omegaZ *= 0.5;
        }else if (gamepad.getBumper(Hand.kLeft)){
            vX *= 0.25;
            omegaZ *= 0.25;
        }
    
        drivetrain.setArcadeDrive(vX, omegaZ);
    }

    public void driveOnBar() {
        // Gets values of each trigger
        double right = gamepad.getTriggerAxis(Hand.kRight);
        double left = gamepad.getTriggerAxis(Hand.kLeft);
        double difference=right-left;

        if (Math.abs(difference) > .05) {
            //climber.DriveBar(difference);
        } else {
            //climber.driveStop();
        }
    }

    
    public void climber() {
        double multiplier = 1.0;

        if (gamepad.getBumper(Hand.kLeft)){
            multiplier = .25;
        }else if(gamepad.getBumper(Hand.kRight)){
            multiplier = .5;
        }

        if (gamepad.getYButton()) {
            Robot.climberSubsystem.telescopeExtend(1 * multiplier);
        } else if (gamepad.getAButton()) {
            Robot.climberSubsystem.climberRetract(1 * multiplier);
        } else {
            Robot.climberSubsystem.climberStop();
        }

    }

    public void update() {
        if (gamepad.getXButton()) {
            double forward;

            double rightTrigger = clampTriggerDeadband(gamepad.getTriggerAxis(Hand.kRight));
            double leftTrigger = clampTriggerDeadband(gamepad.getTriggerAxis(Hand.kLeft));

            forward = rightTrigger - leftTrigger;

            if (drivetrain.snapToTargetV2(forward)){
                rumbleOn();
                Robot.operatorGP.rumbleOn();
            }else{   
                rumbleOff();
                Robot.operatorGP.rumbleOff();
            }
        } else {
            rumbleOff();
            Robot.operatorGP.rumbleOff();
            videoGameDrive();
            //splitArcadeDrive();
            //tankDrive();
        }

        if (gamepad.getBackButtonPressed()) {
            drivetrain.resetOdometry();
        }
    }

    public void rumbleOn(){
        gamepad.setRumble(RumbleType.kLeftRumble, .5);
        gamepad.setRumble(RumbleType.kRightRumble, .5);
    }

    public void rumbleOff(){
        gamepad.setRumble(RumbleType.kLeftRumble, 0);
        gamepad.setRumble(RumbleType.kRightRumble, 0);
    }

    private boolean triggerDeadband(double inputValue){
        return (Math.abs(inputValue) > 0);
    }

    private double clampTriggerDeadband(double inputValue){
        if (triggerDeadband(inputValue)){
            return inputValue;
        }else {
            return 0;
        }
    }

    private boolean outsideDeadband(double inputValue) {
        return (Math.abs(inputValue) > DEADBAND_VALUE);
    }

    private double clampDeadband(double inputValue){
        if (outsideDeadband(inputValue)) {
            return inputValue;
        } else {
            return 0;
        }
    }

}
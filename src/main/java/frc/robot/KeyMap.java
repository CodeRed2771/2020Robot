package frc.robot;

import frc.robot.libs.HID.*;
import frc.robot.libs.HID.HID.Axis;

public class KeyMap {

    // GAMEPADS
    private final HID gp1 = new HID(0);
    private final HID gp2 = new HID(1);
    //private final HID gp3 = new HID(2);
    private final int gamepad1 = 0;
    private final int gamepad2 = 1;

    // MANAGEMENT BOOLEANS
    private boolean singleControllerMode = false;

    // CONTROLLER 1
    private final HID.Axis swerveXAxis = LogitechExtreme3D.STICK_X;
    private final HID.Axis swerveYAxis = LogitechExtreme3D.STICK_Y;
    private final HID.Axis swerveRotAxis = LogitechExtreme3D.STICK_ROT;
    private final HID.Button switchToRobotCentric = LogitechExtreme3D.Thumb;
    //private final HID.Button autoAlignToTarget = LogitechExtreme3D.Button7;
    //private final HID.Button turnToBackTarget = LogitechExtreme3D.Button5;
    //private final HID.Button autoPlaceHatch = LogitechExtreme3D.Button7;
    

    private final HID.Button zeroGyro = LogitechExtreme3D.Button8;

    // private final HID.Button driveOffPlatform = LogitechF310.BUMPER_LEFT;

    // Climbing
    private final HID.Button climb = LogitechExtreme3D.Button11;
    private final HID.Button driveModifier = LogitechExtreme3D.Thumb;
    private final HID.Button undoClimb = LogitechExtreme3D.Button12;
    
    // Auto Programs
    private final HID.Button autoEpicStartButton = LogitechExtreme3D.Button9;
   // private final HID.Button shipMoveLeft = LogitechF310.DPAD_LEFT;
   // private final HID.Button shipMoveRight = LogitechF310.DPAD_RIGHT;
    private final HID.Button findRocketTarget = LogitechExtreme3D.Button3;
    private final HID.Button findShipTarget = LogitechExtreme3D.Button4;
    private final HID.Button findFeedStation = LogitechExtreme3D.Button6;

    // CONTROLLER 2
    private final HID.Button intakeForwards = LogitechF310.DPAD_RIGHT;
    private final HID.Button intakeBackwards= LogitechF310.DPAD_LEFT;
    private final HID.Button stopIntake = LogitechF310.DPAD_DOWN;
    private final HID.Button gamePieceOverride = LogitechF310.DPAD_DOWN;
    private final HID.Axis ejectGamePiece = LogitechF310.TRIGGER_RIGHT_AXIS;
    //private final HID.Button fingerUp = LogitechF310.START;
    private final HID.Button extendLinkage = LogitechF310.BACK;

    // Hatch Placement
    private final HID.Button goToLvl1 = LogitechF310.A;
    private final HID.Button goToLvl2 = LogitechF310.B;
    private final HID.Button goToLvl3 = LogitechF310.Y;
    private final HID.Button goToShipCargo = LogitechF310.BUMPER_RIGHT;
    private final HID.Button goToTravelPosition = LogitechF310.X;
    private final HID.Button modifier = LogitechF310.BUMPER_LEFT;

    private final Axis manualLift = LogitechF310.STICK_LEFT_Y;
    private final HID.Axis manualClimbDrive = LogitechF310.STICK_RIGHT_Y; // Not used in robot Java
    

    // TEST CONTROLLER
    private final HID.Button startShooting = LogitechF310.A;
    private final HID.Button stopShooting = LogitechF310.B;
    private final HID.Button startIntake = LogitechF310.TRIGGER_RIGHT;
    private final HID.Button reverseIntake = LogitechF310.TRIGGER_LEFT;
    //private final HID.Button stopIntake = LogitechF310.DPAD_DOWN;
    private final HID.Button intakeUp = LogitechF310.DPAD_UP;
    private final HID.Button intakeDown = LogitechF310.DPAD_DOWN;


    public HID getHID(int gamepad) {
        if (!singleControllerMode) {
            switch (gamepad) {
            case gamepad1:
                return gp1;
            case gamepad2:
                return gp2;
            // case gamepad3:
            //     return gp3;
            default:
                return null;
            }
        } else {
            return gp1;
        }
    }

    public double getSwerveXAxis() {
        return getHID(gamepad1).axis(swerveXAxis);
    }

    public double getSwerveYAxis() {
        return getHID(gamepad1).axis(swerveYAxis);
    }

    public boolean getRobotCentricModifier() {
        return getHID(gamepad1).button(switchToRobotCentric);
    }

    public boolean startShooter() {
        return getHID(gamepad2).button(startShooting);
    }

    public boolean stopShooter() {
        return getHID(gamepad2).button(stopShooting);
    }

    // public boolean startIntake() {
    //     return getHID(gamepad2).button(startIntake);
    // }

    // public boolean reverseIntake() {
    //     return getHID(gamepad2).button(reverseIntake);
    // }

    // public boolean intakeUp() {
    //     return getHID(gamepad2).button(intakeUp);
    // }

    // public boolean intakeDown() {
    //     return getHID(gamepad2).button(intakeDown);
    // }

    // public boolean stopIntake() {
    //     return getHID(gamepad2).button(stopIntake);
    // }
    
    // old stuff

    //public boolean getAutoPlaceHatch() {
        //return getHID(gamepad1).button(autoPlaceHatch);
    //}

    public boolean getZeroGyro() {
        return getHID(gamepad1).button(zeroGyro);
    }

    public double getSwerveRotAxis() {
        return getHID(gamepad1).axis(swerveRotAxis);
    }

    public boolean getAutoEpicStart() {
        return getHID(gamepad1).button(autoEpicStartButton);
    }

    public boolean startIntakeForwards() {
        return getHID(gamepad2).button(intakeForwards);
    }
    public boolean startIntakeBackwards() {
        return getHID(gamepad2).button(intakeBackwards);
    }
    public boolean stopIntake() {
        return getHID(gamepad2).button(stopIntake);
    }



    public boolean linkageUp() {
        return getHID(gamepad2).button(goToTravelPosition) && getHID(gamepad2).button(modifier);
    }

    public boolean linkageDown() {
        return getHID(gamepad2).button(extendLinkage);
    }

    // public boolean turnIntakeOff() {
    //     return getHID(gamepad2).button(stopIntake);
    // }

    public boolean gamePieceOverride() {
        return getHID(gamepad2).button(gamePieceOverride);
    }

    public boolean ejectGamePiece() {
        return getHID(gamepad2).axis(ejectGamePiece) > 0.8;
    }

    public double getManualLift () {
        return getHID(gamepad2).axis(manualLift);
    }
    public boolean goToTravelPosition() {
        return getHID(gamepad1).button(goToTravelPosition) || getHID(gamepad2).button(goToTravelPosition);
    }

    public boolean findRocketTarget() {
        return getHID(gamepad1).button(findRocketTarget); 
    }

    public boolean findShipTarget() {
        return getHID(gamepad1).button(findShipTarget);
    }

    public boolean findFeedStation() {
        return getHID(gamepad1).button(findFeedStation);
    }

   public boolean shipMoveLeft() {
        //return getHID(gamepad1).button(shipMoveLeft) && !getHID(gamepad1).button(driveModifier);
        return false;
    }

    public boolean shipMoveRight() {
        //return getHID(gamepad1).button(shipMoveRight) && !getHID(gamepad1).button(driveModifier);
        return false;
    }
    
    public boolean getClimb() {
        return getHID(gamepad1).button(driveModifier) && getHID(gamepad1).button(climb);
    }

    public boolean getUndoClimb() {
        return getHID(gamepad1).button(driveModifier) && getHID(gamepad1).button(undoClimb);
    }

    public boolean getAutoAlignToTarget() {
        //return getHID(gamepad1).axis(autoAlignToTarget) > 0.8;
        return false;
    }

    public boolean getTurnToBackTarget() {
        //return getHID(gamepad1).axis(turnToBackTarget) > 0.8;
        return false;
    }

    public double getClimbDrive() {
        return getHID(gamepad2).axis(manualClimbDrive);
    }

    //public boolean getFingerUp() {
        //return getHID(gamepad2).button(fingerUp);
    //}

    // public boolean getHatchOverride() {
    //     return getHID(gamepad2).button(intakeHatch) && getHID(gamepad2).button(modifier);
    // }

    public boolean getDriveModifier() {
        return getHID(gamepad1).button(driveModifier);
    }

    // public boolean driveOffPlatform() {
    //     return getHID(gamepad1).button(driveOffPlatform);
    // }

    //TEST METHODS


     /*
     * 
     * Provide some direct access to the commonly used F310 gamepad features
     * 
     */
    // public boolean getBumperLeft(int gamePadNumber) {
    //     return getHID(gamePadNumber).button(LogitechF310.BUMPER_LEFT);
    // }

    public boolean getButtonA(int gamePadNumber) {
        return getHID(gamePadNumber).button(LogitechF310.A);
    }

    public boolean getButtonB(int gamePadNumber) {
        return getHID(gamePadNumber).button(LogitechF310.B);
    }

    // public boolean getButtonX(int gamePadNumber) {
    //     return getHID(gamePadNumber).button(LogitechF310.X);
    // }

    // public boolean getButtonY(int gamePadNumber) {
    //     return getHID(gamePadNumber).button(LogitechF310.Y);
    // }

    // public boolean getButtonTriggerRight(int gamePadNumber) {
    //     return getHID(gamePadNumber).button(LogitechF310.TRIGGER_RIGHT);
    // }

    // public boolean getButtonTriggerLeft(int gamePadNumber) {
    //     return getHID(gamePadNumber).button(LogitechF310.TRIGGER_LEFT);
    // }

    // public boolean getButtonBumperLeft(int gamePadNumber) {
    //     return getHID(gamePadNumber).button(LogitechF310.BUMPER_LEFT);
    // }

    // public boolean getButtonDpadUp(int gamePadNumber) {
    //     return getHID(gamePadNumber).button(LogitechF310.DPAD_UP);
    // }

    // public boolean getButtonDpadDown(int gamePadNumber) {
    //     return getHID(gamePadNumber).button(LogitechF310.DPAD_DOWN);
    // }

    // private final HID.Button startIntake = LogitechF310.TRIGGER_RIGHT;
    // private final HID.Button reverseIntake = LogitechF310.TRIGGER_LEFT;
    // private final HID.Button stopIntake = LogitechF310.BUMPER_LEFT;
    // private final HID.Button intakeUp = LogitechF310.DPAD_UP;
    // private final HID.Button intakeDown = LogitechF310.DPAD_DOWN;

    // public boolean getStartButton(int gamePadNumber) {
    //     return getHID(gamePadNumber).button(LogitechF310.START);
    // }

    // public boolean getDpadLeft(int gamePadNumber) {
    //     return getHID(gamePadNumber).button(LogitechF310.DPAD_LEFT);
    // }

    // public boolean getDpadRight(int gamePadNumber) {
    //     return getHID(gamePadNumber).button(LogitechF310.DPAD_RIGHT);
    // }

    // public boolean getDpadUp(int gamePadNumber) {
    //     return getHID(gamePadNumber).button(LogitechF310.DPAD_UP);
    // }

    // public boolean getDpadDown(int gamePadNumber) {
    //     return getHID(gamePadNumber).button(LogitechF310.DPAD_DOWN);
    // }

    // public double getLeftStickY(int gamePadNumber) {
    //     return getHID(gamePadNumber).axis(LogitechF310.STICK_LEFT_Y);
    // }

    // public double getRightStickY(int gamePadNumber) {
    //     return getHID(gamePadNumber).axis(LogitechF310.STICK_RIGHT_Y);
    // }

}

package frc.robot;

import frc.robot.libs.HID.*;

public class KeyMap extends DirectAccessKeyMap {

    // GAMEPADS
    private final HID gp1 = new HID(0);
    private final HID gp2 = new HID(1);
    private final int gamepad1 = 0;
    private final int gamepad2 = 1;

    // MANAGEMENT BOOLEANS
    private boolean singleControllerMode = false;

    // CONTROLLER 1
    private final HID.Axis swerveXAxis = LogitechExtreme3D.STICK_X;
    private final HID.Axis swerveYAxis = LogitechExtreme3D.STICK_Y;
    private final HID.Axis shooterAdjuster = LogitechExtreme3D.SLIDER;
    private final HID.Axis swerveRotAxis = LogitechExtreme3D.STICK_ROT;
    private final HID.Button switchToRobotCentric = LogitechExtreme3D.Thumb;
    private final HID.Button startVision = LogitechExtreme3D.Button7;

    private final HID.Button zeroGyro = LogitechExtreme3D.Button8;

    // Climbing
    private final HID.Button driveModifier = LogitechExtreme3D.Thumb;

    // CONTROLLER 2
    private final HID.Button startShooting = LogitechF310.A;
    private final HID.Button stopShooting = LogitechF310.B;
    private final HID.Button spinWheel3To5Times = LogitechF310.X;
    private final HID.Button matchColor = LogitechF310.Y;
    private final HID.Button intakeForwards = LogitechF310.TRIGGER_LEFT;
    private final HID.Button intakeBackwards = LogitechF310.TRIGGER_RIGHT;
    private final HID.Button stopIntake = LogitechF310.BUMPER_LEFT;

    public HID getHID(int gamepad) {
        if (!singleControllerMode) {
            switch (gamepad) {
            case gamepad1:
                return gp1;
            case gamepad2:
                return gp2;
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

    public boolean startVision () {
        return getHID(gamepad1).button(startVision);
    }

    public boolean startShooter() {
        return getHID(gamepad2).button(startShooting);
    }

    public boolean stopShooter() {
        return getHID(gamepad2).button(stopShooting);
    }

    public double getShooterAdjustment () {
        return getHID(gamepad1).axis(shooterAdjuster);
    }

    public boolean startIntake() {
        return getHID(gamepad2).button(intakeForwards);
    }

    public boolean reverseIntake() {
        return getHID(gamepad2).button(intakeBackwards);
    }

    public boolean stopIntake() {
        return getHID(gamepad2).button(stopIntake);
    }

    public boolean spinWheel() {
        return getHID(gamepad2).button(spinWheel3To5Times);
    }

    public boolean matchColor() {
        return getHID(gamepad2).button(matchColor);
    }

    public boolean getZeroGyro() {
        return getHID(gamepad1).button(zeroGyro);
    }

    public double getSwerveRotAxis() {
        return getHID(gamepad1).axis(swerveRotAxis);
    }

    public boolean startIntakeForwards() {
        return getHID(gamepad2).button(intakeForwards);
    }

    public boolean startIntakeBackwards() {
        return getHID(gamepad2).button(intakeBackwards);
    }

    public boolean getDriveModifier() {
        return getHID(gamepad1).button(driveModifier);
    }
}

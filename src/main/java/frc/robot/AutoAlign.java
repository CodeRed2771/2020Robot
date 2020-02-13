package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//The purpose of this class is to turn the robot until we are on target.

public class AutoAlign extends AutoBaseClass {

    private double angleOffset = 0;

    public AutoAlign () {

    }

    public void start() {
        super.start();
        Vision.getInstance();
        Vision.setVisionTrackingMode();
        Vision.setTargetForShooting();
    }

    public void stop() {
        super.stop();
    }

    @Override
    public void tick() {
        if (isRunning()) {
            DriveAuto.tick();
            SmartDashboard.putNumber("Auto Step", getCurrentStep());
            switch (getCurrentStep()) {
            case 0:
                Vision.setLED(true);
                angleOffset = Vision.getDistanceAdjustedAngle();
                if (Vision.seesTarget() == true) {
                    advanceStep();
                } else if (Vision.seesTarget() == false) {
                    setStep(10);
                }
                break;

            case 1:
                DriveAuto.turnDegrees(angleOffset, 1);
                setTimerAndAdvanceStep(2000);
                break;

            case 2:
                if (DriveAuto.hasArrived()) {
                    advanceStep();
                }
                break;
            case 3:
                angleOffset = Vision.getDistanceAdjustedAngle();
                SmartDashboard.putNumber("Angle Offset", angleOffset);
                SmartDashboard.putBoolean("Sees Target", Vision.seesTarget());
                if (Vision.onTarget()) {
                    System.out.println("On Target!");
                    Vision.setDriverMode();
                    Vision.setLED(false);
                    stop();
                } else {
                    setStep(0);
                }
                break;
            case 10:
                turnDegrees(60, 1);
                if (Vision.seesTarget()) {
                    setStep(0);
                }
                break;
            }
        }
    }
}
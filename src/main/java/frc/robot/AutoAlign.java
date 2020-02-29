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
                Vision.setTargetForShooting();
                angleOffset = Vision.getDistanceAdjustedAngle();
                if (Vision.seesTarget()) {
                    advanceStep();
                }
                break;
            case 1:
                turnDegrees(angleOffset, 1);
                setTimerAndAdvanceStep(10000);
                break;

            case 2:
                if (driveCompleted()) {
                    advanceStep();
                }
                break;
            case 3:
                angleOffset = Vision.getDistanceAdjustedAngle();
                SmartDashboard.putNumber("Adj Angle Offset", angleOffset);
                SmartDashboard.putNumber("Angle Offset", Vision.getAngleOffset());
                SmartDashboard.putBoolean("Sees Target", Vision.seesTarget());
                if (Vision.onTarget()) {
                    System.out.println("On Target!");
                    stop();
                } else {
                    setStep(0);
                }
                break;
            //     ShooterPivoter.setDesiredShootPosition(Vision.getShooterPivoterDesiredShaftLocation());
            //     setTimerAndAdvanceStep(1000);
            //     break;
            // case 5:
            //     if (ShooterPivoter.shooterAtPosition()){
            //         advanceStep();
            //     }
            //     break;
            // case 6:
            //     stop();
            //     break;
            }
        }
    }
}
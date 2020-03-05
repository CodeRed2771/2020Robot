package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//The purpose of this class is to turn the robot until we are on target.

public class AutoAlign extends AutoBaseClass {

    private double angleOffset = 0;

    public AutoAlign () {

    }

    public void start() {
        this.start(false);
    }

    public void start(boolean autoShoot){
        super.start(autoShoot);
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
                Intake.moveIntakeDown();
                Vision.setTargetForShooting();
                angleOffset = Vision.getDistanceAdjustedAngle();
                if (Vision.seesTarget()) {
                    advanceStep();
                }
                if (autoShoot()){
                    Shooter.StartShooter();
                }
                break;
            case 1:
                DriveAuto.turnDegrees(angleOffset, 1);
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
                    advanceStep();
                } else {
                    setStep(0);
                }
                break;
            case 4:
                if (autoShoot()){
                    Shooter.oneShot();
                }
                advanceStep();
                break;
            case 5:
                stop();
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
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonAllTheWayRight8Ball extends AutoBaseClass {

    private AutoAlign mAutoAlign = new AutoAlign();

    public void start() {
        super.start();
    }

    public void stop() {
        super.stop();
    }

    @Override
    public void tick() {
        if (isRunning()) {
            Vision.setTargetForShooting();
            DriveAuto.tick(); // Find if DriveAuto is Ticked somewhere else
            SmartDashboard.putNumber("Auto Step", getCurrentStep());
            switch (getCurrentStep()) {
            case 0:
                driveInches(40, 180, 1);
                setTimerAndAdvanceStep(2000);
                break;
            case 1:
                if (driveCompleted()) {
                    advanceStep();
                }
                break;
            case 2:
                mAutoAlign.start();
                advanceStep();
                break;
            case 3:
                mAutoAlign.tick();
                if (Vision.onTarget()) {
                    advanceStep();
                }
                break;
            case 4:
                Shooter.StartShooter();
                setTimerAndAdvanceStep(2000);
                break;
            case 5:

                break;
            case 6:
                Shooter.StopShooter();
                turnToHeading(180, 1);
                setTimerAndAdvanceStep(2000);
                break;
            case 7:
                if (driveCompleted()) {
                    advanceStep();
                }
                break;
            case 8:
                Intake.moveIntakeDown();
                Intake.runIntakeForwards();
                advanceStep();
                break;
            case 9:
                driveInches(195, 180, 1, false, true);
                setTimerAndAdvanceStep(5000);
                break;
            case 10:
                if (driveCompleted()) {
                    advanceStep();
                }
                break;
            case 11:
                turnToHeading(210, 1);
                setTimerAndAdvanceStep(1000);
                break;
            case 12:
                if (driveCompleted()) {
                    advanceStep();
                }
                break;
            case 13:
                driveInches(45, 160, 1, false, true);
                setTimerAndAdvanceStep(1000);
                break;
            case 14:
                if (driveCompleted()) {
                    advanceStep();
                }
            case 15:
                driveInches(20, 80, 1);
                setTimerAndAdvanceStep(1000);
                break;
            case 16:
                if (driveCompleted()) {
                    advanceStep();
                }
            case 17:
                driveInches(60, 0, 1, false, true);
            // case 12:
            //     setTimer(1000);
            //     Intake.stopIntake();
            //     advanceStep();
            //     break;
            // case 13:
            //     turnDegrees(180, 1);
            //     setTimerAndAdvanceStep(2000);
            //     break;
            // case 14:
            //     if (driveCompleted()) {
            //         advanceStep();
            //     }
            //     break;
            // case 15:
            //     driveInches(45, 0, 1, false, true);
            //     setTimerAndAdvanceStep(2000);
            //     break;
            // case 16:
            //     if (driveCompleted()) {
            //         advanceStep();
            //     }
            //     break;
            // case 17:
            //     mAutoAlign.start();
            //     advanceStep();
            //     break;
            // case 18:
            //     mAutoAlign.tick();
            //     if (Vision.onTarget()) {
            //         advanceStep();
            //     }
            //     break;
            // case 19:
            //     Shooter.StartShooter();
            //     setTimerAndAdvanceStep(2000);
            //     break;
            // case 20:
            //     break;
            // case 21:
            //     Shooter.StopShooter();
            //     stop();
            //     break;
            }
        }
    }
}

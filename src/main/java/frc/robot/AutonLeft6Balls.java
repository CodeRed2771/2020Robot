/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonLeft6Balls extends AutoBaseClass{

    private AutoAlign mAutoAlign = new AutoAlign();
    private double turnAngle = 0;

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
            DriveAuto.tick();
            SmartDashboard.putNumber("Auto Step", getCurrentStep());
            switch (getCurrentStep()) {
                case 0:
                    turnDegrees(35, 1);
                    setTimerAndAdvanceStep(1000);
                    break;
                case 1:
                    if (driveCompleted()) {
                        advanceStep();
                    }
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
                    setTimerAndAdvanceStep(3000);
                    break;
                case 5:
                    Shooter.tick();
                    break;
                case 6:
                    Shooter.StopShooter();
                    // Intake.moveIntakeDown();
                    // Intake.runIntakeForwards();
                    advanceStep();
                    break;
                case 7:
                    turnAngle = RobotGyro.getRelativeAngle() - 35;
                    turnDegrees(-270 - turnAngle, 1);
                    setTimerAndAdvanceStep(2000);
                    break;
                case 8:
                    break;
                case 9:
                    driveInches(100, 225, 1, false, true);
                    setTimerAndAdvanceStep(1500);
                    break;
                case 10:
                    break;
                case 11:
                    driveInches(72, 180, 0.6, false, true);
                    setTimerAndAdvanceStep(2000);
                    break;
                case 12:
                    break;
                case 13:
                    driveInches(60, 67.5, 0.5, false, true);
                    setTimerAndAdvanceStep(1000);
                    break;
                case 14:
                    Intake.moveIntakeUp();
                    Intake.stopIntake();
                    break;
                case 15:
                    turnDegrees(-130, 0.8);
                    setTimerAndAdvanceStep(1000);
                    break;
                case 16:
                    mAutoAlign.start();
                    mAutoAlign.tick();
                    advanceStep();
                    break;
                case 17:
                    if (Vision.onTarget()) {
                        advanceStep();
                    }
                    break;
                case 18: 
                    Shooter.StartShooter();
                    setTimerAndAdvanceStep(4000);
                    break;
                case 19:
                    break;
                case 20:
                    Shooter.StopShooter();
                    stop();
                    break;
            }
        }
    }
}

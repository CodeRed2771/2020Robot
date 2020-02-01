/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonLeftBacwards6Balls extends AutoBaseClass {

    private AutoAlign mAutoAlign = new AutoAlign();

    public void start(){
        super.start();
    }

    public void stop(){
        super.stop();
    }

    @Override
    public void tick() {
        if (isRunning()) {
            DriveAuto.tick();
            SmartDashboard.putNumber("Auto Step", getCurrentStep());
            switch (getCurrentStep()) {
                case 0:
                    turnDegrees(45, 1);
                    advanceStep();
                    break;
                case 1:
                    if (turnCompleted()) {
                        mAutoAlign.start();
                        advanceStep();
                    }
                    break;
                case 2:
                    mAutoAlign.tick();
                    if (Vision.onTarget()) {
                        advanceStep();
                    }
                    break;
                case 3:
                    ShooterSRX.StartShooter();
                    setTimerAndAdvanceStep(3000);
                    break;
                case 4:
                    advanceStep();
                    break;
                case 5:
                    ShooterSRX.StopShooter();
                    turnDegrees(90 + secondTurnDegreesCalculator(), 1);
                    advanceStep();
                    break;
                case 6:
                    Intake.moveIntakeDown();
                    driveInches(75, 150, 0.8);
                    advanceStep();
                    break;
                case 7:
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 8:
                    Intake.runIntakeForwards();
                    advanceStep();
                    break;
                case 9:
                    // DRIVE ALONG THE RONDEVOUS POINT
                    advanceStep();
                    break;
                case 10:
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 11: 
                    turnDegrees(-(RobotGyro.getRelativeAngle()), 1);
                    advanceStep();
                    break;
                case 12:
                    if (turnCompleted()) {
                        mAutoAlign.start();
                        advanceStep();
                    }
                    break;
                case 13:
                    mAutoAlign.tick();
                    if (Vision.onTarget()) {
                        advanceStep();
                    }
                    break;
                case 14:
                    ShooterSRX.StartShooter();
                    setTimerAndAdvanceStep(3000);
                    break;
                case 15:
                    advanceStep();
                    break;
                case 16:
                    ShooterSRX.StopShooter();
                    stop();
                    break;
            }
        }
    }

    private double secondTurnDegreesCalculator () {
        double gyroRelative = RobotGyro.getRelativeAngle();
        double actualTurnDegrees = 0;
        if (gyroRelative > 45) {
            actualTurnDegrees = 45 - gyroRelative;
        } else if (gyroRelative < 45) {
            actualTurnDegrees = 45 - gyroRelative;
        } else {
            actualTurnDegrees = gyroRelative;
        }
        return actualTurnDegrees;
    }
}

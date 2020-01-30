/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonAllTheWayRight8Ball extends AutoBaseClass{

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
                    mAutoAlign.start();
                    advanceStep();
                    break;
                case 1:
                    mAutoAlign.tick();
                    if (Vision.onTarget() == true) {
                        advanceStep();
                    }
                    break;
                case 2:
                    ShooterSRX.StartShooter();
                    setTimer(2000);
                    advanceStep();
                    break;
                case 3:
                    advanceStep();
                    break;
                case 4:
                    turnDegrees(90 + RobotGyro.getAngle(), 1);
                    setTimerAndAdvanceStep(2000);
                    break;
                case 5:
                    if (turnCompleted() == true) {
                        advanceStep();
                    }
                    break;
                case 6:
                    Intake.moveIntakeDown();
                    advanceStep();
                    break;
                case 7:
                    Intake.runIntakeForwards();
                    advanceStep();
                    break;
                case 8:
                    driveInches(265, 180, 1);
                    setTimerAndAdvanceStep(5000);
                    break;
                case 9:
                    if (driveCompleted() == true) {
                        advanceStep();
                    }
                    break;
                case 10:
                    setTimer(1000);
                    Intake.stopIntake();
                    advanceStep();
                    break;
                case 11:
                    turnDegrees(-90, 1);
                    setTimerAndAdvanceStep(2000);
                    break;
                case 12:
                    if (turnCompleted() == true) {
                        advanceStep();
                    }
                    break;
                case 13:
                    driveInches(45, 0, 1);
                    setTimerAndAdvanceStep(2000);
                    break;
                case 14:
                    if (driveCompleted() == true) {
                        advanceStep();
                    }
                    break;
                case 15:
                    mAutoAlign.start();
                    advanceStep();
                    break;
                case 16: 
                    mAutoAlign.tick();
                    if (Vision.onTarget() == true) {
                        advanceStep();
                    }
                    break;
                case 17:
                    ShooterSRX.StartShooter();
                    setTimer(2000);
                    advanceStep();
                    break;
                case 18:
                    advanceStep();
                    break;
                case 19:
                    ShooterSRX.StopShooter();
                    stop();
                    break;
            }
        }
    }
}

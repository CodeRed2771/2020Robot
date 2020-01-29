/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonAllTheWayRight8Ball extends AutoBaseClass{

    public void start(){
        super.start();
    }

    public void stop(){
        super.stop();
    }
    
    @Override
    public void tick() {
        if (isRunning() == true) {
            DriveAuto.tick();
            SmartDashboard.putNumber("Auto Step", getCurrentStep());
            switch (getCurrentStep()) {
                case 0:
                    // Start Vision
                    advanceStep();
                    break;
                case 1:
                    if (Vision.onTarget() == true) {
                        advanceStep();
                    } else {
                        setStep(0);
                    }
                    break;
                case 2:
                    ShooterSRX.StartShooter();
                    setTimer(3000);
                    advanceStep();
                    break;
                case 3:
                    turnDegrees(90 + RobotGyro.getAngle(), 1);
                    advanceStep();
                    break;
                case 4:
                    if (turnCompleted() == true) {
                        advanceStep();
                    }
                    break;
                case 5:
                    Intake.moveIntakeDown();
                    advanceStep();
                    break;
                case 6:
                    Intake.runIntakeForwards();
                    advanceStep();
                    break;
                case 7:
                    driveInches(265, 180, 1);
                    advanceStep();
                    break;
                case 8:
                    if (driveCompleted() == true) {
                        advanceStep();
                    }
                    break;
                case 9:
                    setTimer(1000);
                    Intake.stopIntake();
                    advanceStep();
                    break;
                case 10:
                    turnDegrees(-90, 1);
                    advanceStep();
                    break;
                case 11:
                    if (turnCompleted() == true) {
                        advanceStep();
                    }
                    break;
                case 12:
                    driveInches(45, 0, 1);
                    advanceStep();
                    break;
                case 13:
                    if (driveCompleted() == true) {
                        advanceStep();
                    }
                    break;
                case 14:
                    // Vision Lines Up
                    advanceStep();
                    break;
                case 15: 
                    if (Vision.onTarget() == true) {
                        advanceStep();
                    } else {
                        setStep(14);
                    }
                case 16:
                    ShooterSRX.StartShooter();
                    setTimer(2000);
                    advanceStep();
                    break;
                case 17:
                    ShooterSRX.StopShooter();
                    stop();
                    break;
            }
        }
    }
}

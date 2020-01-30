/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonMiddle3Balls extends AutoBaseClass{

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
                    setTimerAndAdvanceStep(1500);
                    break;
                case 1:
                    if (turnCompleted() == true) {
                        advanceStep();
                    }
                    break;
                case 2:
                    mAutoAlign.start();
                    advanceStep();
                    break;
                case 3:
                    if (Vision.onTarget() == true) {
                        advanceStep();
                    } 
                    break;
                case 4:
                    ShooterSRX.StartShooter();
                    setTimer(2000);
                    advanceStep();
                    break;
                case 5:
                    advanceStep();
                    break;
                case 6:
                    ShooterSRX.StopShooter();
                    advanceStep();
                    break;
                case 7:
                    Intake.moveIntakeDown();
                    advanceStep();
                    break;
                case 8: 
                    driveInches(36, 180, 1);
                    setTimerAndAdvanceStep(3000);
                    break;
                case 9:
                    if (driveCompleted() == true) {
                        advanceStep();
                    }
                    break;
                case 10:
                    stop();
                    break;
            }
        }
    }
}

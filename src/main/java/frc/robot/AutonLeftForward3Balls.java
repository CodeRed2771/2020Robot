/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonLeftForward3Balls extends AutoBaseClass{

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
                    driveInches(100, 45, 1);
                    advanceStep();
                    break;
                case 1:
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 2: 
                    driveInches(100, 90, 1);
                    advanceStep();
                    break;
                case 3: 
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 4:
                    mAutoAlign.start();
                    advanceStep();
                    break;
                case 5: 
                    mAutoAlign.tick();
                    if (Vision.onTarget()) {
                        advanceStep();
                    }
                    break;
                case 6:
                    ShooterSRX.StartShooter();
                    setTimerAndAdvanceStep(3000);
                    break;
                case 7: 
                    ShooterSRX.StopShooter();
                    stop();
                    break;
            }
        }
    }
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonLeftAlongLine3Ball extends AutoBaseClass {

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
                    driveInches(176, 90, 1);
                    setTimerAndAdvanceStep(4000);
                    break;
                case 1:
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 2:
                    mAutoAlign.start();
                    setTimerAndAdvanceStep(3000);
                    advanceStep();
                    break;
                case 3:
                    advanceStep();
                    break;
                case 4:
                    if (Vision.onTarget() == true) {
                        advanceStep();
                    } else {
                        setStep(2);
                    }
                    break;
                case 5:
                    ShooterSRX.StartShooter();
                    setTimer(2000);
                    advanceStep();
                    break;
                case 6:
                    advanceStep();
                    break;
                case 7:
                    ShooterSRX.StopShooter();
                    advanceStep();
                    break;
                case 8:
                    driveInches(80, 255, 1);
                    setTimerAndAdvanceStep(4000);
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

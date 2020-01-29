/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonLeftAlongLine3Ball extends AutoBaseClass {

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
                    advanceStep();
                    break;
                case 1:
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 2:
                    // VISION LINES UP
                    advanceStep();
                    break;
                case 3:
                    if (Vision.onTarget() == true) {
                        advanceStep();
                    } else {
                        setStep(2);
                    }
                    break;
                case 4:
                    ShooterSRX.StartShooter();
                    setTimer(2000);
                    advanceStep();
                    break;
                case 5:
                    ShooterSRX.StopShooter();
                    advanceStep();
                    break;
                case 6:
                    driveInches(190.5, 247.473, 1);
                    advanceStep();
                    break;
                case 7:
                    if (driveCompleted() == true) {
                        advanceStep();
                    }
                    break;
                case 8:
                    stop();
                    break;
                    
                    
            }
        }

    }

}

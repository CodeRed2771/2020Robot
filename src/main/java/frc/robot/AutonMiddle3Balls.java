/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonMiddle3Balls extends AutoBaseClass{

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
                    turnDegrees(45, 1);
                    advanceStep();
                    break;
                case 1:
                    if (turnCompleted() == true) {
                        advanceStep();
                    }
                    break;
                case 2:
                    // Vision Read
                    advanceStep();
                    break;
                case 3:
                    if (Vision.onTarget() == true) {
                        advanceStep();
                    } else {
                        setStep(2);
                    }
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
                    Intake.moveIntakeDown();
                    advanceStep();
                    break;
                case 7: 
                    driveInches(36, 180, 1);
                    advanceStep();
                    break;
                case 8:
                    if (driveCompleted() == true) {
                        advanceStep();
                    }
                    break;
                case 9:
                    stop();
                    break;
            }
        }
    }
}

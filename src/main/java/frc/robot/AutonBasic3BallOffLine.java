/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonBasic3BallOffLine extends AutoBaseClass{

    private AutoAlign mAutoAlign = new AutoAlign();

    public void start(char robotPosition) {
        super.start(robotPosition);
    }

    public void stop(){
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
                    if (robotPosition() == Position.LEFT) {
                        turnDegrees(37, 1);
                    } else if (robotPosition() == Position.CENTER) {
                        setStep(2);
                    } else if (robotPosition() == Position.RIGHT) {                        
                        turnDegrees(-37, 1);
                    } else {
                        System.out.println("AUTON NOT RUNNING");
                        stop();
                    }
                    setTimerAndAdvanceStep(1000);
                    break;
                case 1:
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 2:
                    driveInches(36, 180, 1, false, true);
                    setTimerAndAdvanceStep(1500);
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
                    Shooter.StartShooter();
                    setTimerAndAdvanceStep(3000);
                    break;
                case 7:
                    break;
                case 8:
                    Shooter.StopShooter();
                    stop();
                    break;
            }
        }
    }
}

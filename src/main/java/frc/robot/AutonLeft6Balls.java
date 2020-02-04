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

    public void start() {
        super.start();
    }

    public void stop() {
        super.stop();
    }

    @Override
    public void tick() {
        if (isRunning()) {
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
                    Intake.moveIntakeDown();
                    Intake.runIntakeForwards();
                    advanceStep();
                    break;
                case 7:
                    turnDegrees(-90, 1);

            }
        }
    }

    public static double getSecondTurnAngle () {
        return 0;
    }

}

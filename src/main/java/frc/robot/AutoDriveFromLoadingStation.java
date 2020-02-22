package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class AutoDriveFromLoadingStation extends AutoBaseClass {
    public AutoDriveFromLoadingStation() {
		super();

	}

	public void start() {
		super.start();
	}

	public void tick() {
        if (isRunning()) {
            DriveAuto.tick();
            SmartDashboard.putNumber("Auto Step", getCurrentStep());
            switch (getCurrentStep()) {
                case 0:
                    driveInches(12, 0, 1, false, true); 
                    setTimerAndAdvanceStep(2000);
                    break;
                case 1:
                    break;
                case 2:
                    turnDegrees(180, 1);
                    setTimerAndAdvanceStep(3000);
                    break;
                case 3:
                    DistanceSensor.getRange();
                    break;
                case 4:
                    if (DistanceSensor.getRange() >= 12){
                        driveInches(12, 45, 1, false, true);
                    } else if (DistanceSensor.getRange() <= 12){
                        driveInches(120, 0, 1, false, true);
                        setTimerAndAdvanceStep(2000);
                    }
                    break;
                case 5:
                    break;
                case 6:
                    stop();
                    break;
            }
        }
	}
}
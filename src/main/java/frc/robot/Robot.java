/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.math.BigDecimal;
import java.math.RoundingMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends TimedRobot {
  KeyMap gamepad;
  AutoBaseClass mAutoProgram;
  boolean isAutoRunning = false;

  @Override
  public void robotInit() {
    gamepad = new KeyMap();
    ShooterSRX.getInstance();
    ShooterSRX.StopShooter();
    Intake.getInstance();
    RobotGyro.getInstance();
	DriveTrain.getInstance();
	DriveAuto.getInstance();
	ColorSensor.getInstance();

	mAutoProgram = new AutoDoNothing();
	
	
	Calibration.loadSwerveCalibration();

	RobotGyro.reset();

	DriveTrain.allowTurnEncoderReset();
	DriveTrain.resetTurnEncoders(); // sets encoders based on absolute encoder positions

	SmartDashboard.putBoolean("Show Encoders", true);
  }

  @Override
	public void teleopInit() {
		DriveTrain.stopDriveAndTurnMotors();
		DriveTrain.setAllTurnOrientation(0, false); // sets them back to calibrated zero position
	}

  @Override
  public void teleopPeriodic() {

	// if (gamepad.getButtonA(1) && !isAutoRunning) {
	// 	DriveAuto.driveInches(60, 0, 1);
	// 	isAutoRunning = true;
	// } else
	// if (gamepad.getButtonB(1) && !isAutoRunning) {
	// 	DriveAuto.driveInches(-60, 0, 1);
	// 	isAutoRunning = true;
	// } else {
	// 	if (!gamepad.getButtonA(1) && !gamepad.getButtonB(1)) {
	// 		isAutoRunning = false;
	// 	}
	// }

    if (gamepad.getButtonA(1)) {
      System.out.println("START SHOOTING");
      ShooterSRX.StartShooter();
    }
    if (gamepad.getButtonB(1)) {
      ShooterSRX.StopShooter();
	}
	if (gamepad.startIntakeForwards()){
		Intake.runIntakeForwards();
	}
	if (gamepad.startIntakeBackwards()){
		Intake.runIntakeBackwards();
	}
	if (gamepad.turnPositiveDegrees() && DriveAuto.hasArrived()) {
		DriveAuto.turnDegrees(90, 1);
		isAutoRunning = true;
	}
	if (gamepad.turnNegativeDegrees() && DriveAuto.hasArrived()) {
		DriveAuto.turnDegrees(-90, 1);
		isAutoRunning = true;
	}
    // if (gamepad.getButtonDpadDown(1)) {
    //   Intake.moveIntakeDown();
    // }
    // if (gamepad.getButtonDpadUp(1)) {
    //   Intake.moveIntakeUp();
    // }
    // if (gamepad.getButtonBumperLeft(1)) {
    //   Intake.stopIntake();
    // }
	ShooterSRX.tick();
	DriveAuto.tick();
	ColorSensor.tick();

    // --------------------------------------------------
		// RESET - allow manual reset of systems by pressing Start
		// --------------------------------------------------
		if (gamepad.getZeroGyro()) {
			RobotGyro.reset();
			DriveTrain.allowTurnEncoderReset();
			DriveTrain.resetTurnEncoders(); // sets encoders based on absolute encoder positions
			DriveTrain.setAllTurnOrientation(0, false);
		}

		// DRIVE
		if (mAutoProgram.isRunning()) {
			mAutoProgram.tick();
		} else {
			// DRIVER CONTROL MODE
			// Issue the drive command using the parameters from
			// above that have been tweaked as needed
			double driveRotAmount;
			double driveFWDAmount = gamepad.getSwerveYAxis();
			double driveStrafeAmount = -gamepad.getSwerveXAxis();
			boolean normalDrive = !gamepad.getDriveModifier();

			if (Math.abs(driveFWDAmount) <= .2 || !normalDrive) // strafe adjust if not driving forward
				driveStrafeAmount = strafeAdjust(driveStrafeAmount, normalDrive);

			driveRotAmount = rotationalAdjust(gamepad.getSwerveRotAxis());

			driveFWDAmount = forwardAdjust(driveFWDAmount, normalDrive);

			if (Math.abs(driveFWDAmount)>.5) {
				isAutoRunning = false;
			}

			if (!isAutoRunning) {
				if (gamepad.getRobotCentricModifier())
					DriveTrain.humanDrive(driveFWDAmount, driveStrafeAmount, driveRotAmount);
		  		else
					DriveTrain.fieldCentricDrive(driveFWDAmount, driveStrafeAmount, driveRotAmount);
			}
			
		}

		showDashboardInfo();
  }

  @Override
  public void robotPeriodic() {
  }

 
  @Override
  public void autonomousInit() {
	  mAutoProgram = new AutoAlign();
	  mAutoProgram.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
	if (mAutoProgram.isRunning()) {
		mAutoProgram.tick();
	}
   
  }

	public void disabledInit() {
		DriveTrain.allowTurnEncoderReset(); // allows the turn encoders to be
											// reset once during disabled
											// periodic
		DriveTrain.resetDriveEncoders();

		// DriveTrain.disablePID();

		Calibration.initializeSmartDashboard();
	}

	public void disabledPeriodic() {
		DriveTrain.resetTurnEncoders(); // happens only once because a flag
										// prevents multiple calls
		showDashboardInfo();

		if (Calibration.shouldCalibrateSwerve()) {
			double[] pos = DriveTrain.getAllAbsoluteTurnOrientations();
			Calibration.saveSwerveCalibration(pos[0], pos[1], pos[2], pos[3]);
		}

		Calibration.checkIfShouldResetCalibration();
	}
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  private double rotationalAdjust(double rotateAmt) {
		// put some rotational power restrictions in place to make it
		// more controlled movement
		double adjustedAmt = 0;

		if (Math.abs(rotateAmt) < .1) {
			adjustedAmt = 0;
		} else {
			if (Math.abs(rotateAmt) < .6) {
				adjustedAmt = .10 * Math.signum(rotateAmt); // take 10% of the input
			} else {
				if (Math.abs(rotateAmt) < .8) {
					adjustedAmt = .30 * Math.signum(rotateAmt); // take 30% of input
				} else {
					if (Math.abs(rotateAmt) < .95) {
						adjustedAmt = .45 * Math.signum(rotateAmt); // take 45%
					} else {
						adjustedAmt = rotateAmt * .85;    // take 85% 
					}
				}
			}
		}
		return adjustedAmt;
	}

	private double forwardAdjust(double fwd, boolean normalDrive) {
		if (normalDrive) {
			return fwd;
		} else {
			return fwd * .45;
		}
	}

	private double strafeAdjust(double strafeAmt, boolean normalDrive) {
		// put some power restrictions in place to make it
		// more controlled
		return strafeAmt;

		// double adjustedAmt = 0;

		// if (Math.abs(strafeAmt) < .1) {
		// 	adjustedAmt = 0;
		// } else {
		// 	if (normalDrive) { // do normal adjustments
		// 		if (Math.abs(strafeAmt) < .7) {
		// 			adjustedAmt = .3 * strafeAmt; // .2 * Math.signum(strafeAmt);
		// 		} else {
		// 			if (Math.abs(strafeAmt) < .98) {
		// 				adjustedAmt = .50 * strafeAmt; // .4 * Math.signum(strafeAmt);
		// 			} else {
		// 				adjustedAmt = strafeAmt;
		// 			}
		// 		}
		// 	} else { // lift is up, so do more drastic adjustments
		// 		adjustedAmt = strafeAmt * .35;
		// 	}
		// }
		// return adjustedAmt;
  }
  
  private void showDashboardInfo() {
		SmartDashboard.putNumber("Gyro Relative", round2(RobotGyro.getRelativeAngle()));
		SmartDashboard.putNumber("Gyro Raw", round2(RobotGyro.getAngle()));

		if (SmartDashboard.getBoolean("Show Encoders", false)) {
			DriveTrain.showTurnEncodersOnDash();
			DriveTrain.showDriveEncodersOnDash();
		}
  }
  
  private static Double round2(Double val) {
		// added this back in on 1/15/18
		return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
}

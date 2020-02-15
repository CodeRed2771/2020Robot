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
import frc.robot.libs.HID.HID;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends TimedRobot {

	SendableChooser<String> autoChooser;
	SendableChooser<String> positionChooser;
	SendableChooser<String> driveChooser;
	String autoSelected;
	KeyMap gamepad;
	AutoBaseClass mAutoProgram;
	boolean isAutoRunning = false;
	AutoAlign mAutoAlign = new AutoAlign();

	final String threeBasicBalls = "3 Basic Balls";
	final String eightBallRight = "8 Balls Right";
	final String fiveBallsMiddle = "5 Balls Middle";
	final String sixBallsLeft = "6 Balls Left";

	@Override
	public void robotInit() {
		gamepad = new KeyMap();
		Shooter.getInstance();
		Shooter.StopShooter();
		Intake.getInstance();
		RobotGyro.getInstance();
		DistanceSensor.getInstance();

		Calibration.loadSwerveCalibration();
		DriveTrain.getInstance();
		DriveAuto.getInstance();
		// ColorSensorAndClimber.getInstance();
		Vision.getInstance();
	

		setupAutoChoices();
		mAutoProgram = new AutoDoNothing();

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
		SmartDashboard.putNumber("DIST", Vision.getDistanceFromTarget());
		SmartDashboard.updateValues();
		if (gamepad.getButtonA(1) && !isAutoRunning) {
			DriveAuto.driveInches(60, 0, 1);
			isAutoRunning = true;
		} else if (gamepad.getButtonB(1) && !isAutoRunning) {
			DriveAuto.driveInches(-60, 0, 1);
			isAutoRunning = true;
		} else {
			if (!gamepad.getButtonA(1) && !gamepad.getButtonB(1)) {
				isAutoRunning = false;
			}
		}

		if (gamepad.startVision()) {
			mAutoAlign.start();
		}

		if (gamepad.getButtonA(1)) {
			System.out.println("START SHOOTING");
			Shooter.StartShooter();
			Indexer.startIndexer();
		}
		if (gamepad.getButtonB(1)) {
			Shooter.StopShooter();
			Indexer.stopIndexer();
		}
		if (gamepad.startIntakeForwards()) {
			Intake.runIntakeForwards();
		}
		if (gamepad.startIntakeBackwards()) {
			Intake.runIntakeBackwards();
		}
		if (gamepad.stopIntake()) {
			Intake.stopIntake();
		}
		if (gamepad.spinWheel()) {
			// ColorSensorAndClimber.start3To5TimesSpinning();
		}
		if (gamepad.matchColor()) {
			// ColorSensorAndClimber.startMatchColorSpinning();
		}
		Shooter.setAdjustmentFactor(gamepad.getShooterAdjustment());

		Shooter.tick();
		DriveAuto.tick();
		// ColorSensorAndClimber.tick();
		// ColorSensorAndClimber.matchColor();
		mAutoAlign.tick();
		DistanceSensor.tick();
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

			if (Math.abs(driveFWDAmount) > .5) {
				isAutoRunning = false;
			}

			if (!isAutoRunning) {
				if (gamepad.getRobotCentricModifier()) {
					DriveTrain.humanDrive(driveFWDAmount, driveStrafeAmount, driveRotAmount);
				} else {
					DriveTrain.fieldCentricDrive(driveFWDAmount, driveStrafeAmount, driveRotAmount);
				}
			}
		}

		showDashboardInfo();
	}

	@Override
	public void robotPeriodic() {
	}

	@Override
	public void autonomousInit() {

		RobotGyro.reset();

		mAutoProgram.stop();
		// String selectedPos = positionChooser.getSelected();
		// SmartDashboard.putString("Position Chooser Selected", selectedPos);
		// char robotPosition = selectedPos.toCharArray()[0];
		// System.out.println("Robot position: " + robotPosition);

		// autoSelected = (String) autoChooser.getSelected();
		// SmartDashboard.putString("Auto Selected: ", autoSelected);

		mAutoProgram = mAutoAlign;
		mAutoAlign.start();

		// switch (autoSelected) {
		// case threeBasicBalls:
		// 	mAutoProgram = new AutonBasic3BallOffLine();
		// 	mAutoProgram.start(robotPosition);
		// 	break;
		// case eightBallRight:
		// 	mAutoProgram = new AutonAllTheWayRight8Ball();
		// 	mAutoProgram.start(robotPosition);
		// 	break;
		// case fiveBallsMiddle:
		// 	mAutoProgram = new AutonMiddle5Balls();
		// 	mAutoProgram.start(robotPosition);
		// 	break;
		// case sixBallsLeft:
		// 	mAutoProgram = new AutonLeft6Balls();
		// 	mAutoProgram.start(robotPosition);
		// 	break;
		// }


	}

	private void setupAutoChoices() {
		// Position Chooser
		positionChooser = new SendableChooser<String>();
		positionChooser.addOption("Left", "L");
		positionChooser.setDefaultOption("Center", "C");
		positionChooser.addOption("Right", "R");
		SmartDashboard.putData("Position", positionChooser);

		autoChooser = new SendableChooser<String>();
		autoChooser.setDefaultOption(threeBasicBalls, threeBasicBalls);
		autoChooser.addOption(eightBallRight, eightBallRight);
		autoChooser.addOption(fiveBallsMiddle, fiveBallsMiddle);
		autoChooser.addOption(sixBallsLeft, sixBallsLeft);
		SmartDashboard.putData("Auto Chose:", autoChooser);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		if (mAutoProgram.isRunning()) {
			mAutoProgram.tick();
			Shooter.tick();
		}
	}

	public void disabledInit() {
		// allows the turn encoders to be reset once during disabled periodic
		DriveTrain.allowTurnEncoderReset();
		DriveTrain.resetDriveEncoders();
		DriveTrain.resetTurnEncoders();

		Calibration.initializeSmartDashboard();
	}

	public void disabledPeriodic() {
		showDashboardInfo();
		SmartDashboard.putNumber("DIST", Vision.getDistanceFromTarget());

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

		if (Math.abs(rotateAmt) < .2) {
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
						adjustedAmt = rotateAmt * .85; // take 85%
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

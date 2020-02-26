/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.math.BigDecimal;
import java.math.RoundingMode;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
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
	BuiltInAccelerometer accel;
	AutoBaseClass mAutoProgram;

	final String threeBasicBalls = "3 Basic Balls";
	final String eightBallRight = "8 Balls Right";
	final String fiveBallsMiddle = "5 Balls Middle";
	final String sixBallsLeft = "6 Balls Left";

	@Override
	public void robotInit() {
		gamepad = new KeyMap();
		accel = new BuiltInAccelerometer();
		Shooter.getInstance();
		Shooter.StopShooter();
		ShooterPivoter.getInstance();
		Intake.getInstance();
		RobotGyro.getInstance();
		// DistanceSensor.getInstance();
		Calibration.loadSwerveCalibration();
		DriveTrain.getInstance();
		DriveAuto.getInstance();
		Climber.getInstance();
		ColorSensorAndTraverser.getInstance();
		Vision.getInstance();

		setupAutoChoices();
		mAutoProgram = new AutoDoNothing();

		RobotGyro.reset();
		ShooterPivoter.resetPivoter();

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
		// if (gamepad.getButtonA(1) && !isAutoRunning) {
		// DriveAuto.driveInches(60, 0, 1);
		// isAutoRunning = true;
		// } else if (gamepad.getButtonB(1) && !isAutoRunning) {
		// DriveAuto.driveInches(-60, 0, 1);
		// isAutoRunning = true;
		// } else {
		// if (!gamepad.getButtonA(1) && !gamepad.getButtonB(1)) {
		// isAutoRunning = false;
		// }
		// }

		if (gamepad.startVision()) {
			mAutoProgram = new AutoAlign();
			mAutoProgram.start();
		}
		if (gamepad.startIntake()) {
			Intake.runIntakeForwards();
		}
		if (gamepad.stopIntake()) {
			Intake.stopIntake();
		}
		if (gamepad.intakeDownPosition()) {
			Intake.moveIntakeDown();
		}
		if (gamepad.intakeUpPosition()) {
			Intake.moveIntakeUp();
		}
		if (gamepad.spinWheel()) {
			ColorSensorAndTraverser.start3To5TimesSpinning();
		}
		if (gamepad.matchColor()) {
			ColorSensorAndTraverser.startMatchColorSpinning();
		}
		if (gamepad.stopShooter() || gamepad.stopShooting()) {
			Shooter.StopShooter();
		}
		if (gamepad.lowClimberHeight() || gamepad.climberLowPosition()) {
			Climber.setlowClimberPosition();
		}
		if (gamepad.colorWheelClimberHeight()) {
			Climber.setColorWheelClimberPosition();
		}
		if (gamepad.climber() && gamepad.getRobotCentricModifier()) {
			Climber.setHighClimberPosition();
		}
		if (gamepad.closeShooterPosition()) {
			ShooterPivoter.shootClosePosition();
		}
		if (gamepad.midTrenchPosition()) {
			ShooterPivoter.midTrench();
		}
		if (gamepad.backTrenchPosition()) {
			ShooterPivoter.backTrench();
		}
		if (gamepad.startShooter()) {
			Shooter.StartShooter();
		}
		if (gamepad.levelScale()) {
			ColorSensorAndTraverser.runTrue(true);
		}
		if (gamepad.turboTurning()) {
			// ADD TURBO TURN
		}
		if (gamepad.dropBellyPan()) {
			Climber.dropBellyPan(true);
		}
		if (gamepad.pickUpbellyPanContinueClimb()) {
			Climber.pickUpBellyPanAndContinueClimbing(true);
		}
		if (gamepad.oneShotShooter()) {
			Indexer.queuerOneShot();
		}
		if (gamepad.continualShooter()) {
			Indexer.queuerContinualShot();
		}
		if (gamepad.getRobotCentricModifier() && gamepad.oneShotShooter()) {
			Climber.setIdealClimberPositionToDropBellyPan();
		}
		if (Math.abs(gamepad.shooterPivoterAdjuster()) > 0.1) {
			ShooterPivoter.moveToSetPoint(gamepad.shooterPivoterAdjuster()); // THIS FUNCTIONS NEED TO BE IMPROVISED BASED ON WHAT WE ARE GIVEN
		}
		if (Math.abs(gamepad.manualClimberAdjuster()) > 0.1) {
			Climber.moveToSetPoint(gamepad.manualClimberAdjuster()); // THIS FUNCTIONS NEED TO BE IMPROVISED BASED ON WHAT WE ARE GIVEN
		}

		


		
		Shooter.setAdjustmentFactor(gamepad.getShooterAdjustment());

		Shooter.tick();
		ShooterPivoter.tick();
		DriveAuto.tick();
		Climber.tick();
		ColorSensorAndTraverser.tick();
		ColorSensorAndTraverser.matchColor();
		ColorSensorAndTraverser.levelScale();

		// DistanceSensor.tick();
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
		}

		// DRIVER CONTROL MODE
		// Issue the drive command using the parameters from
		// above that have been tweaked as needed
		double driveRotAmount;
		double driveFWDAmount = gamepad.getSwerveYAxis();
		double driveStrafeAmount = -gamepad.getSwerveXAxis();
		
			
		driveRotAmount = rotationalAdjust(gamepad.getSwerveRotAxis());
		driveFWDAmount = forwardAdjust(driveFWDAmount, true);

		SmartDashboard.putNumber("SWERVE ROT AXIS", gamepad.getSwerveRotAxis());
		SmartDashboard.putNumber("ADJUSTED SWERVE ROT AMOUNT", driveRotAmount);

		if (Math.abs(driveFWDAmount) > .5) {
			if (mAutoProgram.isRunning())
				mAutoProgram.stop();
		}

		if (!mAutoProgram.isRunning()) {
			if (gamepad.getRobotCentricModifier()) {
				DriveTrain.humanDrive(driveFWDAmount, driveStrafeAmount, driveRotAmount);
			} else {
				DriveTrain.fieldCentricDrive(driveFWDAmount, driveStrafeAmount, driveRotAmount);
			}
		}

		showDashboardInfo();
	}

	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("Rio X", accel.getX());
		SmartDashboard.putNumber("Rio Y", accel.getY());
		SmartDashboard.putNumber("Rio Z", accel.getZ());

		SmartDashboard.updateValues();
	}

	@Override
	public void autonomousInit() {

		RobotGyro.reset();

		mAutoProgram.stop();
		String selectedPos = positionChooser.getSelected();
		SmartDashboard.putString("Position Chooser Selected", selectedPos);
		char robotPosition = selectedPos.toCharArray()[0];
		System.out.println("Robot position: " + robotPosition);

		autoSelected = (String) autoChooser.getSelected();
		SmartDashboard.putString("Auto Selected: ", autoSelected);

		mAutoProgram = new AutoDoNothing();
		mAutoProgram.start();

		switch (autoSelected) {
		case threeBasicBalls:
			mAutoProgram = new AutonBasic3BallOffLine();
			mAutoProgram.start(robotPosition);
			break;
		case eightBallRight:
			mAutoProgram = new AutonAllTheWayRight8Ball();
			mAutoProgram.start(robotPosition);
			break;
		case fiveBallsMiddle:
			mAutoProgram = new AutonMiddle5Balls();
			mAutoProgram.start(robotPosition);
			break;
		case sixBallsLeft:
			mAutoProgram = new AutonLeft6Balls();
			mAutoProgram.start(robotPosition);
			break;
		}

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
		ShooterPivoter.tick();
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
			if (Math.abs(rotateAmt) < .78) {
				adjustedAmt = .10 * Math.signum(rotateAmt); // take 10% of the input
			} else {
				if (Math.abs(rotateAmt) < .99) {
					adjustedAmt = .25 * Math.signum(rotateAmt); // take 45%
				} else {
					adjustedAmt = rotateAmt * .5; // take 85%
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

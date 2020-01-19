/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ShooterSRX {
    private static ShooterSRX instance;
    // private static TalonSRX shooterMotor = new TalonSRX(Wiring.SHOOTER>MOTOR_ID);
    private static TalonFX shooterMotor = new TalonFX(Wiring.SHOOTER_MOTOR_ID);
    private static boolean isEnabled = false;
    private static double targetSpeed = 0;
   
    public ShooterSRX() {
        shooterMotor.configFactoryDefault(10);
        shooterMotor.setInverted(false);
        /* first choose the sensor */
		shooterMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

		/* set the relevant frame periods to be at least as fast as periodic rate */
		shooterMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
		shooterMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);

		/* set the peak and nominal outputs */
		shooterMotor.configNominalOutputForward(0, 0);
		shooterMotor.configNominalOutputReverse(0, 0);
		shooterMotor.configPeakOutputForward(1, 0);
		shooterMotor.configPeakOutputReverse(-1, 0);
		shooterMotor.setNeutralMode(NeutralMode.Coast);

		shooterMotor.configClosedloopRamp(.25, 0);

		/* set closed loop gains in slot0 - see documentation */
		shooterMotor.selectProfileSlot(0, 0);

		shooterMotor.config_kP(0, Calibration.SHOOTER_P, 0);
		shooterMotor.config_kI(0, Calibration.SHOOTER_I, 0);
		shooterMotor.config_kD(0, Calibration.SHOOTER_D, 0);
		shooterMotor.config_kF(0, Calibration.SHOOTER_F, 0);

		/* zero the sensor */
		shooterMotor.setSelectedSensorPosition(0, 0, 0);

		// Current limit
        // shooterMotor.configContinuousCurrentLimit(25);
        // MAY NEED THAT LATER
        
        SmartDashboard.putNumber("Shoot P", Calibration.SHOOTER_P);
		SmartDashboard.putNumber("Shoot I", Calibration.SHOOTER_I);
		SmartDashboard.putNumber("Shoot D", Calibration.SHOOTER_D);
        SmartDashboard.putNumber("Shoot F", Calibration.SHOOTER_F);
        SmartDashboard.putNumber("Shoot Setpoint", Calibration.SHOOTER_DEFAULT_SPEED );

        // feederMotor.setNeutralMode(NeutralMode.Brake);
    }

    public static ShooterSRX getInstance() {
        if (instance == null)
            instance = new ShooterSRX();
        return instance;
    }

    public static void tick() {

        // if (isEnabled && isAtSpeed()) {
        //     feederMotor.set(ControlMode.PercentOutput,1);
        // } else {
        //     feederMotor.set(ControlMode.PercentOutput,0);
        // }

        if (SmartDashboard.getBoolean("Shooter TUNE", true)) {
			shooterMotor.config_kF(0, SmartDashboard.getNumber("Shoot F", 0), 0);
			shooterMotor.config_kP(0, SmartDashboard.getNumber("Shoot P", 0), 0);
			shooterMotor.config_kI(0, SmartDashboard.getNumber("Shoot I", 0), 0);
            shooterMotor.config_kD(0, SmartDashboard.getNumber("Shoot D", 0), 0);

            if (isEnabled) {
                // shooterMotor.set(ControlMode.Velocity, SmartDashboard.getNumber("Shoot Setpoint", Calibration.SHOOTER_DEFAULT_SPEED));
                shooterMotor.set(ControlMode.PercentOutput,-.6);
            }
        }
        
        SmartDashboard.putNumber("Shooter Enc", shooterMotor.getSelectedSensorVelocity());
        SmartDashboard.putBoolean("Is At Speed", isAtSpeed());
    }

    public static void StartShooter() {
        isEnabled = true;
    }

    public static void StopShooter() {
        isEnabled = false;
        shooterMotor.set(ControlMode.PercentOutput,0);
    }

    public static double getShooterSpeed() {
        return shooterMotor.getSelectedSensorVelocity();
    }

    public static boolean isAtSpeed() {
        return (getShooterSpeed() > 0 && Math.abs(getShooterSpeed() - targetSpeed) < 100);
    }

}


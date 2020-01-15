/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    private static Shooter instance;
    private static CANSparkMax shooterMotor = new CANSparkMax(Wiring.SHOOTER_MOTOR_ID, MotorType.kBrushless);
    // private static TalonSRX feederMotor = new TalonSRX(Wiring.FEEDER_MOTOR_ID);
    private static boolean isEnabled = false;
    private static double targetSpeed = 0;
   
    public Shooter() {

        shooterMotor.getPIDController().setFF(Calibration.SHOOTER_F);
        shooterMotor.getPIDController().setP(Calibration.SHOOTER_P);
        shooterMotor.getPIDController().setI(Calibration.SHOOTER_I);
        shooterMotor.getPIDController().setD(Calibration.SHOOTER_D);
        
        SmartDashboard.putNumber("Shoot P", Calibration.SHOOTER_P);
		SmartDashboard.putNumber("Shoot I", Calibration.SHOOTER_I);
		SmartDashboard.putNumber("Shoot D", Calibration.SHOOTER_D);
        SmartDashboard.putNumber("Shoot F", Calibration.SHOOTER_F);
        SmartDashboard.putNumber("Shoot Setpoint", Calibration.SHOOTER_DEFAULT_SPEED );

        // feederMotor.setNeutralMode(NeutralMode.Brake);
    }

    public static Shooter getInstance() {
        if (instance == null)
            instance = new Shooter();
        return instance;
    }

    public static void tick() {

        // if (isEnabled && isAtSpeed()) {
        //     feederMotor.set(ControlMode.PercentOutput,1);
        // } else {
        //     feederMotor.set(ControlMode.PercentOutput,0);
        // }

        if (SmartDashboard.getBoolean("Shooter TUNE", true)) {
			shooterMotor.getPIDController().setFF(SmartDashboard.getNumber("Shoot F", 0));
			shooterMotor.getPIDController().setP(SmartDashboard.getNumber("Shoot P", 0), 0);
			shooterMotor.getPIDController().setI(SmartDashboard.getNumber("Shoot I", 0), 0);
            shooterMotor.getPIDController().setD(SmartDashboard.getNumber("Shoot D", 0), 0);

            if (isEnabled) {
                shooterMotor.getPIDController().setReference(SmartDashboard.getNumber("Shoot Setpoint", Calibration.SHOOTER_DEFAULT_SPEED), ControlType.kVelocity);
            }
        }
        
        SmartDashboard.putNumber("Shooter Enc", shooterMotor.getEncoder().getVelocity());
        SmartDashboard.putBoolean("Is At Speed", isAtSpeed());
    }

    public static void StartShooter() {
        isEnabled = true;
    }

    public static void StopShooter() {
        isEnabled = false;
        shooterMotor.getPIDController().setReference(0, ControlType.kVelocity);
    }

    public static double getShooterSpeed() {
        return shooterMotor.getEncoder().getVelocity();
    }

    public static boolean isAtSpeed() {
        return (getShooterSpeed() > 0 && Math.abs(getShooterSpeed() - targetSpeed) < 100);
    }

}


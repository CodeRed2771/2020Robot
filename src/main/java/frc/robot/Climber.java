/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber {
	
	private static boolean dropBellyPan = false;
	private static boolean pickUpBellyPan = false;
	private static Climber instance;
	private static CANSparkMax extenderMotor = new CANSparkMax(Wiring.EXTEND_MOTOR_ID, MotorType.kBrushless);
	private static CANSparkMax liftMotor = new CANSparkMax(Wiring.LIFT_MOTOR_ID, MotorType.kBrushless);

	public static final double BASE_EXTENDED_POSITION = 50;  // tune for actual max extension revolutions
	public static final double MAX_EXTENDED_POSITION = 400; // needs adjusting

    public Climber() {
		extenderMotor.restoreFactoryDefaults();
		extenderMotor.setIdleMode(IdleMode.kCoast);
		extenderMotor.getPIDController().setOutputRange(-.3, .3);
        extenderMotor.getPIDController().setP(1);

		liftMotor.restoreFactoryDefaults();
        liftMotor.setIdleMode(IdleMode.kBrake);
        liftMotor.getPIDController().setOutputRange(-1, 1);
        liftMotor.getPIDController().setP(1);
	}

	public static Climber getInstance () {
        if (instance == null) {
            instance = new Climber();
        }
        return instance;
    }

	public static void tick() {

		SmartDashboard.putNumber("Climb Extend Enc", extenderMotor.getEncoder().getPosition());
		SmartDashboard.putNumber("Climb Lift Enc", liftMotor.getEncoder().getPosition());
	
		if (dropBellyPan) {
			// DROPS BELLY PAN
		}

		if (pickUpBellyPan) {
			// LOCKS BELLY PAN IN TO CONTINUE CLIMBING
		}
	}

	// extendHook - use for first main extension, then manual adjustment from there
	public static void extendHook() {
        extenderMotor.getPIDController().setReference(BASE_EXTENDED_POSITION, ControlType.kPosition);
	}
	
	public static void adjustExtendedHook(double direction) {
		double newSetpoint;

        if (direction > 0) {
            newSetpoint = extenderMotor.getEncoder().getPosition() + (15);
            if (newSetpoint >= MAX_EXTENDED_POSITION) {
            newSetpoint = MAX_EXTENDED_POSITION;
            }
        } else {
            newSetpoint = extenderMotor.getEncoder().getPosition() - (15);
            if (newSetpoint < 0) {
            newSetpoint = 0;
            }
        }

        extenderMotor.getPIDController().setReference(newSetpoint, ControlType.kPosition);
	}

	public static void liftRobot(double direction) {
		double newSetpoint;

        if (direction > 0) {
            newSetpoint = liftMotor.getEncoder().getPosition() + (15); 
        } else {
            newSetpoint = liftMotor.getEncoder().getPosition() - (15); 
            if (newSetpoint < 0) {
            newSetpoint = 0;
            }
        }

		liftMotor.getPIDController().setReference(newSetpoint, ControlType.kPosition);
		extenderMotor.set(-.1);
	}

	// public static void dropBellyPan(boolean dropBellyPan) {
	// 	Climber.dropBellyPan = dropBellyPan;
	// }

	// public static void pickUpBellyPanAndContinueClimbing (boolean pickUpBellyPan) {
	// 	Climber.pickUpBellyPan = pickUpBellyPan;
	// 	// NEED A SETPOINT TO PUT THE CLIMBER AT
	// }

	// public static void setlowClimberPosition () {
	// 	// GIVE SETPOINT FOR LOW CLIMBER POSITION
	// }

	// public static void setColorWheelClimberPosition () {
	// 	// GIVE SETPOINT FOR COLOR WHEEL CLIMBER POSITION
	// }

	// public static void setHighClimberPosition () {
	// 	// GIVE SETPOINT FOR HIGH CLIMBER POSITION
	// }

	// public static void setIdealClimberPositionToDropBellyPan () {
	// 	// GIVE SETPOINT FOR REGULAR CLIMB POSITION
	// }

}

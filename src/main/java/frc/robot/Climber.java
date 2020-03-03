/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Climber {
	
	private static boolean dropBellyPan = false;
	private static boolean pickUpBellyPan = false;
	private static Climber instance;
	private static CANSparkMax climberMotor;
	private static CANPIDController climberPID;


    public Climber() {
		// climberMotor = new TalonSRX(Wiring.CLIMBER_MOTOR_ID);
		// climberMotor.configFactoryDefault(10);
		climberMotor = new CANSparkMax(Wiring.CLIMBER_MOTOR_ID, MotorType.kBrushless);
		climberMotor.restoreFactoryDefaults();
	}

	public static Climber getInstance () {
        if (instance == null) {
            instance = new Climber();
        }
        return instance;
    }

	public static void tick() {

		if (dropBellyPan) {
			// DROPS BELLY PAN
		}

		if (pickUpBellyPan) {
			// LOCKS BELLY PAN IN TO CONTINUE CLIMBING
		}

		// NEED SOMETHING FOR THE PID AND SETPOINTS

	}

	public static void dropBellyPan(boolean dropBellyPan) {
		Climber.dropBellyPan = dropBellyPan;
	}

	public static void pickUpBellyPanAndContinueClimbing (boolean pickUpBellyPan) {
		Climber.pickUpBellyPan = pickUpBellyPan;
		// NEED A SETPOINT TO PUT THE CLIMBER AT
	}

	public static void setlowClimberPosition () {
		// GIVE SETPOINT FOR LOW CLIMBER POSITION
	}

	public static void setColorWheelClimberPosition () {
		// GIVE SETPOINT FOR COLOR WHEEL CLIMBER POSITION
	}

	public static void setHighClimberPosition () {
		// GIVE SETPOINT FOR HIGH CLIMBER POSITION
	}

	public static void setIdealClimberPositionToDropBellyPan () {
		// GIVE SETPOINT FOR REGULAR CLIMB POSITION
	}

	// public static void moveToSetPoint (double direction) {
        
    //     double newSetpoint;

	// 	if (direction < 0) {
	// 		newSetpoint = climberMotor.getCPR();
	// 		if (newSetpoint <= 0) {
	// 			newSetpoint = 0;
	// 		}
	// 	} else {
	// 		newSetpoint = climberMotor.getSelectedSensorPosition(0) + 1000;
	// 		if (newSetpoint > 30000) {
	// 			newSetpoint = 30000; // 
	// 		}
	// 	}

	// 	//  SET THE SETPOINT TO THE FUNCTION LEVEL VARIABLE SETPOINT
    // }
}

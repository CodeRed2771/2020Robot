/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Encoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// ALL COMMENTED OUT CODE IS HERE FOR TESTING PURPOSES PLEASE DO NOT DELETE - IS

public class ShooterPivoter {

    private static ShooterPivoter instance;
    private static double encoderPosition;
    private static DutyCycleEncoder throughBore;
    private static boolean isConn;
    private static boolean limitGet;
    private static WPI_TalonSRX pivotMotor;
    private static PIDController positionPID;
    private static double targetShaftPosition = 0;
    private static boolean shooterAtPosition = false;

    private static final double minPivotPosition = .828; // back position
    private static final double maxPivotPosition = .877; // forward position

    public ShooterPivoter () {
        pivotMotor = new WPI_TalonSRX(Wiring.SHOOTER_PIVOT_MOTOR_ID);
        pivotMotor.setInverted(InvertType.InvertMotorOutput);

        // NOTE - none of this current limiting seems to work.
        pivotMotor.configContinuousCurrentLimit(1);
        pivotMotor.configPeakCurrentLimit(1);
        pivotMotor.enableCurrentLimit(true);

        throughBore = new DutyCycleEncoder(Wiring.SHOOTER_PIVOTER_PWM_ID); 
        throughBore.setConnectedFrequencyThreshold(900); 
        positionPID = new PIDController(100,0,0);
        // SmartDashboard.putNumber("SHOOTER SHAFT ADJUSTMENT", 0.5);
    }

    public static ShooterPivoter getInstance () {
        if (instance == null) {
            instance = new ShooterPivoter();
        }
        return instance;
    }

    public static void tick() {

        encoderPosition = getShaftEncoderPosition();
        double calculatedPower = positionPID.calculate(encoderPosition,targetShaftPosition/*getDesiredShaftPosition()*/);

        SmartDashboard.putNumber("ShootPivot pos", encoderPosition);
        SmartDashboard.putNumber("SP Targ",targetShaftPosition);
        SmartDashboard.putNumber("SP Pwr", calculatedPower);
        
        if (Math.abs(calculatedPower) > 1) {
            calculatedPower = 1 * Math.signum(calculatedPower);
        }

        pivotMotor.set(ControlMode.PercentOutput, calculatedPower);
    }

    public static double getShaftEncoderPosition() {
        double encValue ;
        encValue = throughBore.get();
        if (Math.abs(encValue)>=1) {
            encValue = (Math.abs(encValue) * 1000 -  (((int) Math.abs(encValue)) * 1000)) /1000;
        }
        return encValue;
    }

    public static void resetPivoter() {
        targetShaftPosition = maxPivotPosition; // max is forward
    }

    public static void shootClosePosition () {
        setDesiredShootPosition(0); // NEEDS TO BE ADJUSTED
    }

    public static void midTrench () {
        setDesiredShootPosition(.5); // NEEDS TO BE ADJUSTED
    }

    public static void backTrench () {
        setDesiredShootPosition(1); // NEEDS TO BE ADJUSTED
    }

    public static void setDesiredShootPosition (double desiredPosition) {
        targetShaftPosition = minPivotPosition + ((maxPivotPosition - minPivotPosition) * desiredPosition);
    }

    public static boolean shooterAtPosition () {
        return shooterAtPosition;
    }

    public static void moveToSetPoint (double direction) {  // NEED TO BE BETTER ADDS AND SETPOINTS
        
        double newSetpoint;

		if (direction < 0) {
			newSetpoint = throughBore.get() - 0.01;
			if (newSetpoint < minPivotPosition) {
				newSetpoint = minPivotPosition;
			}
		} else {
			newSetpoint = throughBore.get() + 0.01;
			if (newSetpoint > maxPivotPosition) {
				newSetpoint = maxPivotPosition; 
			}
		}

		targetShaftPosition = newSetpoint;
    }

    // public static float getDesiredShaftPosition () {
    //     return (float) SmartDashboard.getNumber("SHOOTER SHAFT ADJUSTMENT", 0.5);
    // }
}
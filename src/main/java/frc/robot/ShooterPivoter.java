/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Encoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

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
    private static TalonSRX pivotMotor;
    private static PIDController positionPID;
    private static double targetShaftPosition = 0;
    private static boolean shooterAtPosition = false;

    public ShooterPivoter () {
        pivotMotor = new TalonSRX(Wiring.SHOOTER_PIVOT_MOTOR_ID);
        throughBore = new DutyCycleEncoder(Wiring.SHOOTER_PIVOTER_PWM_ID); 
        throughBore.setConnectedFrequencyThreshold(900); 
        positionPID = new PIDController(1.5,0,0);
        // SmartDashboard.putNumber("SHOOTER SHAFT ADJUSTMENT", 0.5);
    }

    public static ShooterPivoter getInstance () {
        if (instance == null) {
            instance = new ShooterPivoter();
        }
        return instance;
    }

    public static void tick() {

        encoderPosition = throughBore.get();
        isConn = throughBore.isConnected();
        double calculatedPower = positionPID.calculate(encoderPosition,targetShaftPosition/*getDesiredShaftPosition()*/);

        SmartDashboard.putNumber("ShootPivot pos", encoderPosition);
        SmartDashboard.putNumber("SP Targ",targetShaftPosition);
        SmartDashboard.putNumber("SP Pwr", calculatedPower);
        
        pivotMotor.set(ControlMode.PercentOutput, calculatedPower);
    }

    public static void resetPivoter() {
        targetShaftPosition = Calibration.SHOOTER_PIVOTER_INITIAL;
    }

    public static void shootClosePosition () {
        setDesiredShootPosition(0.3); // NEEDS TO BE ADJUSTED
    }

    public static void midTrench () {
        setDesiredShootPosition(0.4); // NEEDS TO BE ADJUSTED
    }

    public static void backTrench () {
        setDesiredShootPosition(0.5);; // NEEDS TO BE ADJUSTED
    }

    public static void setDesiredShootPosition (double desiredPosition) {
        targetShaftPosition = Calibration.SHOOTER_PIVOTER_INITIAL + desiredPosition;
    }

    public static boolean shooterAtPosition () {
        return shooterAtPosition;
    }

    // public static float getDesiredShaftPosition () {
    //     return (float) SmartDashboard.getNumber("SHOOTER SHAFT ADJUSTMENT", 0.5);
    // }
}
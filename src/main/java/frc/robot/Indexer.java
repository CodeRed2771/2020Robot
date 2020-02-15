/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Indexer {

    private static final TalonSRX queueMotor = new TalonSRX(Wiring.QUEUER_MOTOR_ID);
    // private static final CANSparkMax queueMotor = new CANSparkMax(Wiring.QUEUER_MOTOR_ID);

    public Indexer () {
    }

    public static void startIndexer () {
        queueMotor.set(ControlMode.PercentOutput, 0.7);
    }

    public static void stopIndexer () {
        queueMotor.set(ControlMode.PercentOutput, 0);
    }

    public static void reverseIndexer () {
        queueMotor.set(ControlMode.PercentOutput, -0.7);
    }

}

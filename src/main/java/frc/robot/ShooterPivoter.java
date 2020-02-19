/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterPivoter {

    private static ShooterPivoter instance;

    // private Encoder throughBore;
    private static int ticks;
    private static DutyCycleEncoder throughBore;
    private static double freq;
    private static boolean isConn;
    private static DigitalInput limit;
    private static boolean limitGet;

    public ShooterPivoter () {
        throughBore = new DutyCycleEncoder(); // NEED A CHANNEL.... tHE PERSON ON CHIEF DELPHI HAD 9
        throughBore.setConnectedFrequencyThreshold(); // NEED A FREQUENCY.... The person on chief delphi had 900
        limit = new DigitalInput(); // NEED A CHANNEL.... The person on chief delphi had 0 - Ishan
    }

    public static ShooterPivoter getInstance () {
        if (instance == null) {
            instance = new ShooterPivoter();
        }
        return instance;
    }

    public static void tick() {
        ticks = (int)throughBore.get();
        freq = throughBore.getFrequency();
        isConn = throughBore.isConnected();
        limitGet = limit.get();
        SmartDashboard.putNumber("Encoder value", ticks);
        SmartDashboard.putNumber("Encoder frequency", freq);
        SmartDashboard.putBoolean("Encoder is connected", isConn);
        SmartDashboard.putBoolean("Limit is pressed", limitGet);
    }

    public static void resetPivoter() {
        throughBore.reset();
    }

}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.*;
import com.revrobotics.Rev2mDistanceSensor.Port;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.*;
import com.revrobotics.Rev2mDistanceSensor.Port;
import com.revrobotics.Rev2mDistanceSensor.RangeProfile;

public class DistanceSensor {
    private Rev2mDistanceSensor distOnboard; 
    private Rev2mDistanceSensor distMXP;
    private static DistanceSensor instance;
    private static final String profileDefault = "Default";
    private static final String highSpeed = "High Speed";
    private static final String highAccuracy = "High Accuracy";
    private static final String longRange = "Long Range";
    private static String m_profileSelected;
    private static final SendableChooser<String> m_chooser = new SendableChooser<>();
    private static Rev2mDistanceSensor distSens;

    public DistanceSensor() {
        m_chooser.setDefaultOption("Default", profileDefault);
        m_chooser.addOption("High Speed", highSpeed);
        m_chooser.addOption("High Accuracy", highAccuracy);
        m_chooser.addOption("Long Range", longRange);
        SmartDashboard.putData("Profile", m_chooser);
        distSens = new Rev2mDistanceSensor(Port.kOnboard);
        distSens.setAutomaticMode(true);
    }

    public static DistanceSensor getInstance() {
        if (instance == null)
            instance = new DistanceSensor();
        return instance;
    }

    public static void tick() {
        m_profileSelected = m_chooser.getSelected();
        switch (m_profileSelected) {
        case highSpeed:
            distSens.setRangeProfile(RangeProfile.kHighSpeed);
            break;
        case highAccuracy:
            distSens.setRangeProfile(RangeProfile.kHighAccuracy);
            break;
        case longRange:
            distSens.setRangeProfile(RangeProfile.kLongRange);
            break;
        default:
            distSens.setRangeProfile(RangeProfile.kDefault);
            break;
        }

        boolean isValid = distSens.isRangeValid();
        SmartDashboard.putBoolean("Valid", isValid);
        if(isValid) {
            SmartDashboard.putNumber("Range", distSens.getRange());
            SmartDashboard.putNumber("Timestamp", distSens.getTimestamp());
        }
    }
}

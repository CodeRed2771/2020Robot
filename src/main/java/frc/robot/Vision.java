/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.lang.Math;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {

    private static float CameraHeight = 36;
    private static float TargetHeight = 90;
    private static float CameraAngle = 30;
    private static float LIMELIGHT_X_AXIS_PIXELS = 320;
    private static double LIMELIGHT_X_AXIS_FOV = 59.6;
    private static int LIMELIGHT_X_AXIS_CENTER = 160;
    private static float LIMELIGHT_Y_AXIS_PIXELS = 240;
    private static double LIMELIGHT_Y_AXIS_FOV = 45.7;
    private static int LIMELIGHT_Y_AXIS_CENTER = 120;

    private float perPixelToDegree_xAxis () {
        float degreeToPixelX = 0;
        degreeToPixelX = (float) LIMELIGHT_X_AXIS_FOV / LIMELIGHT_X_AXIS_PIXELS;
        return degreeToPixelX;
    }

    private float perPixelToDegree_yAxis () {
        float degreeToPixelY = 0;
        degreeToPixelY = (float) LIMELIGHT_Y_AXIS_FOV / LIMELIGHT_Y_AXIS_PIXELS;
        return degreeToPixelY;
    }

    private double getCurrentAngleToCenterOfTarget_yAxis () {
        double currentAngleToCenterOfTarget = 0;
        int targetYPixel = 0; // need to write something to obtain the target y pixel from camera (getTargetYPixel)
        int pixelDifference = 0;
        double angleDifference = 0;
        // Subtract your 'target Y pixel' from your 'center y pixel.'
        pixelDifference = targetYPixel - LIMELIGHT_Y_AXIS_CENTER;
        // Multiply your answer from above by the 'perPixelToDegree_yAxis'
        angleDifference = pixelDifference * perPixelToDegree_yAxis();
        // Add that answer to 'CameraAngle.' And set it equal to 'currentAngleToCenterOfTarget.'
        currentAngleToCenterOfTarget = angleDifference + CameraAngle;
        return currentAngleToCenterOfTarget;
    }

    public VisionData getDistanceAndAngleFromTarget (VisionData data) {

        // still need to right what angle to turn to 'x axis degrees'

        double side2;
        double distance;

        side2 = (double) TargetHeight - CameraHeight;
        distance = side2 / Math.tan(getCurrentAngleToCenterOfTarget_yAxis());
        data.setDistanceAwayFromTarget((float)distance);
        return data;
    }
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.lang.Math;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {

    private static float CameraHeight = 36;
    private static float TargetHeight = 90;
    private static float CameraAngle = 30;
    private static float LIMELIGHT_Y_AXIS_PIXELS = 240;

    public VisionData getDistanceAndAngleOffTarget (VisionData data) {

        double degrees;
        double ty0;
        double degreesTargetOffGround;
        double distance;   
        double angleOffTarget;

        ty0 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty0").getDouble(0);
        degrees = ty0 *(LIMELIGHT_Y_AXIS_PIXELS/2);
        degreesTargetOffGround = CameraAngle - degrees;
        distance = (TargetHeight - CameraHeight) / Math.tan(degreesTargetOffGround);
        angleOffTarget = ty0 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);

        System.out.println("distance from target:" + distance);
        System.out.println("angle off center target:" + angleOffTarget);
        
        data.setAngleOffTarget((float)angleOffTarget);
        data.setDistanceAwayFromTarget((float)distance);

        return data;
    }
}

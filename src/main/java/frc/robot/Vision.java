package frc.robot;

import java.lang.Math;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.*;

public class Vision {

    private static double CameraHeight = 9.0625; // NEED TO BE MORE ACCURATE
    private static double TargetHeight = 89; // NEED TO BE MORE ACCURATE
    public static double CameraAngle = 32; // NEED TO BE MORE ACCURATE
    private static double LIMELIGHT_Y_AXIS_FOV = 45.7;
    private static NetworkTable table = null;
    private static double angleOffTarget = 0;
    private static double ty = 0;
    private static double degreesTargetOffGround = 0;
    private static double distance = 0;
    public static Vision instance;
    private static double[] mArray = {1,1,1,1,1,1,1,1  }; // Adjustment factors -- starts at 0 feet


    public static Vision getInstance() {
        if (instance == null)
            instance = new Vision();
        return instance;
    }

    public Vision() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        SmartDashboard.putNumber("Adjust Val:", 1);
    }

    public static double getAngleOffset() {
        return table.getEntry("tx").getDouble(0);
    }

    public static double getDistanceAdjustedAngle() {

        return getAngleOffset()*SmartDashboard.getNumber("Adjust Val:", 1);
                                                                                                                        /*
        double distance = getDistanceFromTarget();
        double upperVal = 0;
        double adjustFactorOne = 1;                                                              Actual Code We Are Using to get the adjustment Factor
        double adjustFactorTwo = 1;                                                              The Code Above is Used For Testing Purposes - IS
        double averageAdjustFactorPerInch = 0;
        double finalAdjustedFactor = 1;

        distance = Math.floor(distance/12);
        upperVal = distance + 1;
        adjustFactorOne = mArray[(int)distance];
        adjustFactorTwo = mArray[(int)upperVal];
        averageAdjustFactorPerInch = (adjustFactorTwo - adjustFactorOne) / 12;
        finalAdjustedFactor = (averageAdjustFactorPerInch * distance) + adjustFactorOne;
        return finalAdjustedFactor * getAngleOffset();
                                                                                                                    */
        // return (getAngleOffset() + (-(getDistanceFromTarget() - 180) / 360));

        // if (getDistanceFromTarget() < 180) { // 15 feet
        // return getAngleOffset() + ((180 - getDistanceFromTarget()) * .01);
        // } else {
        // return getAngleOffset() - ((getDistanceFromTarget() - 180) * .01);
        // }

    }

    public static boolean seesTarget() {
        return table.getEntry("tv").getDouble(0) > 0;
    }

    public static boolean onTarget() {
        return (seesTarget() && (Math.abs(getAngleOffset()) <= 1));
    }

    public static double getDistanceFromTarget() {
        ty = table.getEntry("ty").getDouble(0);
        degreesTargetOffGround = CameraAngle + ty;
        distance = (TargetHeight - CameraHeight) / Math.tan(Math.toRadians(degreesTargetOffGround));
        SmartDashboard.putNumber("Distance:", distance);
        return distance;
    }

    public static void setLED(boolean turnOn) {
        table.getEntry("ledMode").forceSetNumber(turnOn ? 3 : 1); // 3 - on, 1 = off, 2 - blink
    }

    public static void flashLED() {
        table.getEntry("ledMode").forceSetNumber(2);
    }

    public static void setDriverMode() {
        // setLED(false);
        table.getEntry("camMode").forceSetNumber(1);
    }

    public static void setVisionTrackingMode() {
        setLED(true);
        table.getEntry("camMode").forceSetNumber(0);
    }

    public static void setVisionToActiveTrackingMode() {
        table.getEntry("snapshot").forceSetNumber(1);
    }

    public static void stopActiveVisionMode() {
        table.getEntry("snapshot").forceSetNumber(0);
    }

    public static void setTargetForShooting() {
        table.getEntry("pipeline").forceSetNumber(0);
    }

    public static void setTargetForFuelCell() {
        table.getEntry("pipeline").forceSetNumber(1);
    }
}
package frc.robot;

import java.lang.Math;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.*;

public class Vision {

    private static double CameraHeight = 9.0625; 
    private static double TargetHeight = 89; 
    public static double CameraAngle = 32; 
    private static double LIMELIGHT_Y_AXIS_FOV = 45.7;
    private static NetworkTable table = null;
    private static double angleOffTarget = 0;
    private static double ty = 0;
    private static double degreesTargetOffGround = 0;
    private static double distance = 0;
    public static Vision instance; // 0,1,2,3,4,5,6,7, 8,  9,   10, 11, 12, 13,  14,  15,  16,  17, 18, 19, 20,   21,  
    private static double[] mArray = {1,1,1,1,1,1,1,1,1.25,1.25,1.2,1.2,1.2,1.15,1.15,1.15,1.15,1.1,1.1,1.1,1.125,1.125,
        1.12225,1.12225,1.0775,1.0555,1.0555,1.0555,1.0555,1.0555,1.005,1.01555,1.02255}; // Adjustment factors -- starts at 0 feet
//      22,     23,     24,    25,    26,    27,    28,    29,    30,   31,     32,

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
        double adjustFactorOne = 1;                                                    
        double adjustFactorTwo = 1;                                                    
        double averageAdjustFactorPerInch = 0;
        double finalAdjustedFactor = 1;
                                   
        distance = Math.floor(distance/12);                                 // THIS IS THE CODE WE ARE GOING TO USE TO GET
        upperVal = distance + 1;                                            // THE DISTANCE ADJUSTED FACTOR - IS
        adjustFactorOne = mArray[(int)distance];
        adjustFactorTwo = mArray[(int)upperVal];
        if (adjustFactorOne == adjustFactorTwo) {
            finalAdjustedFactor = adjustFactorOne;
        } else {
            averageAdjustFactorPerInch = (adjustFactorTwo - adjustFactorOne) / 12;
            finalAdjustedFactor = (averageAdjustFactorPerInch * distance) + adjustFactorOne;
        }
        return finalAdjustedFactor * getAngleOffset();
                                                                               */                                     
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
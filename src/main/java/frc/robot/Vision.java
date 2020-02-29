package frc.robot;

import java.lang.Math;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.*;

public class Vision {

    private static double CameraHeight = 22.3725; // MAYBE 24ISH NEEDS TO BE REALLY ACCURATE
    private static double TargetHeight = 89.75; 
    private static double CameraAngle = 29.5;
    private static double cameraDistanceFromCenterOfRobot = 4.875; // NEEDS TO BE ADJUSTED TO THE DISTANCE THE CAMERA ACTUALLY IS - IS
    private static double LIMELIGHT_Y_AXIS_FOV = 45.7;
    private static NetworkTable table = null;
    private static double angleOffTarget = 0;
    private static double ty = 0;
    private static double degreesTargetOffGround = 0;
    private static double distance = 0;
    public static Vision instance;              // 0,  1,  2,  3,  4,   5,   6,    7,    8,   9,   10, 11, 12, 13,  14,  15,     16,  17, 18, 19, 20,   21,  
    private static double[] turnAdjustmentArray = {1.5,1.5,1.5,1.5,1.49,1.49,1.355,1.355,1.25,1.25,1.2,1.2,1.2,1.15,1.15,1.1,1.1,1.075,1.1,1.095,1.1195,1.125,
        1.,1.,1.0775,1.0555,1.0555,1.0555,1.0555,1.0555,1.055,1.01555,1.02255,1.02255,1.02255,1.0205,1.02255,1.02255,1.02255,1.02255,1.0225,1.0225,1.0225,1.0225,1.0225,1.0225,1.0225,1.0225,1.0225}; // Adjustment factors -- starts at 0 feet
//      22,     23,     24,    25,    26,    27,    28,    29,    30,   31,     32,     33,     34,     35,     36,     37,     38,     39,     40,   41,    42,    43     44



    private static float[] shooterPivoterArray = {};

    public static Vision getInstance() {
        if (instance == null)
            instance = new Vision();
        return instance;
    }

    public Vision() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        SmartDashboard.putNumber("Adjust Val:", 1);
        SmartDashboard.putNumber("ShooterPivoterAdjust", 0.5);
    }

    public static double getAngleOffset() {
        return table.getEntry("tx").getDouble(0);
    }

    public static double getDistanceAdjustedAngle () {

        // return getAngleOffset()*SmartDashboard.getNumber("Adjust Val:", 1);
                                                                                           
        double distance = getDistanceFromTarget();
        double originalDistance = distance;                                                                                                                        
        double upperVal = 0;
        double adjustFactorOne = 1;                                                    
        double adjustFactorTwo = 1;                                                    
        double averageAdjustFactorPerInch = 0;
        double finalAdjustedFactor = 1;
                                   
        distance = Math.floor(distance/12);                                 // THIS IS THE CODE WE ARE GOING TO USE TO GET
        upperVal = distance + 1;                                            // THE DISTANCE ADJUSTED FACTOR - IS
        adjustFactorOne = turnAdjustmentArray[(int) distance];
        adjustFactorTwo = turnAdjustmentArray[(int) upperVal];
        averageAdjustFactorPerInch = (adjustFactorTwo - adjustFactorOne) / 12;
        finalAdjustedFactor = (averageAdjustFactorPerInch * (originalDistance - (distance * 12))) + adjustFactorOne;
        SmartDashboard.putNumber("FINAL ADJUSTED FACTOR", finalAdjustedFactor);
        return finalAdjustedFactor * getAngleOffset();
    }

    public static double getShooterPivoterDesiredPosition () {
        // return SmartDashboard.getNumber("ShooterPivoterAdjust", 0.5);

        double distance = getAdjustedDistanceFromTarget();
        float originalDistance = (float) distance;                                                                                                                        
        float upperVal = 0;
        float desiredShaftPositionOne = 1;                                                    
        float desiredShaftPositionTwo = 1;                                                    
        float averageDesiredShaftPositionPerInch = 0;
        float finalShaftPosition = 1;

        distance = Math.floor(distance/12);
        upperVal = (float) distance + 1;
        desiredShaftPositionOne = shooterPivoterArray[(int) distance];
        desiredShaftPositionTwo = shooterPivoterArray[(int) upperVal];
        averageDesiredShaftPositionPerInch = (desiredShaftPositionTwo - desiredShaftPositionOne) / 12;
        finalShaftPosition = (averageDesiredShaftPositionPerInch * (originalDistance - ((float) distance * 12)));
        return finalShaftPosition;
    }

    public static boolean seesTarget() {
        return table.getEntry("tv").getDouble(0) > 0; 
    }

    public static boolean onTarget() {
        return (seesTarget() && (Math.abs(getAngleOffset()) <= 1));
    }

    public static double getDistanceFromTarget () {
        ty = table.getEntry("ty").getDouble(0);
        degreesTargetOffGround = CameraAngle + ty;
        distance = (TargetHeight - CameraHeight) / Math.tan(Math.toRadians(degreesTargetOffGround));
        SmartDashboard.putNumber("Distance:", distance);
        return distance;
    }

    public static double getAdjustedDistanceFromTarget () {
        
        double originalDistance = getDistanceFromTarget(); 
        double angleFromTarget = getAngleOffset();

        return originalDistance - (cameraDistanceFromCenterOfRobot - ((Math.acos(Math.toRadians(angleFromTarget))) * cameraDistanceFromCenterOfRobot));
    }

    public static void setLED(boolean turnOn) {
        table.getEntry("ledMode").forceSetNumber(turnOn ? 3 : 1); // 3 - on, 1 = off, 2 - blink
    }

    public static void flashLED() {
        table.getEntry("ledMode").forceSetNumber(2);
    }

    public static void setDriverMode() {
        setLED(false);
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
package frc.robot;

import java.lang.Math;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {

  private static float CameraHeight = 36; // NEED TO BE MORE ACCURATE
  private static float TargetHeight = 92; // NEED TO BE MORE ACCURATE
  public static float CameraAngle = 30;  // NEED TO BE MORE ACCURATE
  private static double LIMELIGHT_Y_AXIS_FOV = 45.7;
  private static NetworkTable table = null;
  private static double angleOffTarget = 0;
  private static double ty = 0;
  private static double degreesTargetOffGround = 0;
  private static double distance = 0;
    
  public static Vision instance;

  public static Vision getInstance(){
    if (instance == null)
        instance = new Vision();
      return instance;
  }
  
  public Vision () {
    table = NetworkTableInstance.getDefault().getTable("limelight");
  }

  public static double getAngleOffset(){
    return table.getEntry("tx").getDouble(0);
  }
  public static boolean seesTarget(){
    return table.getEntry("tv").getDouble(0) >0;
  }
  
  public static boolean onTarget() {
    return (seesTarget() && (Math.abs(getAngleOffset()) <= 1));
  }

  public static double getDistanceFromTarget () {
    ty = table.getEntry("ty").getDouble(0);
    degreesTargetOffGround = CameraAngle + ty;
    distance = (TargetHeight - CameraHeight) / Math.tan(Math.toRadians(degreesTargetOffGround));
    return distance;
  }

  // public VisionData getDistanceAndAngleOffTarget (VisionData data) {

  //   // double degrees;
  //   double ty;
  //   double degreesTargetOffGround;
  //   double distance;
  //   //double angleOffTarget;

    // setVisionTrackingMode();
    // setLED(true);
    
  //   ty = table.getEntry("ty").getDouble(0);
  //   // degrees = ty0 *(LIMELIGHT_Y_AXIS_FOV/2);
  //   degreesTargetOffGround = CameraAngle + ty; // change it to '-' if it doesnt report positive angle above what it currently is
  //   distance = (TargetHeight - CameraHeight) / Math.tan(Math.toRadians(degreesTargetOffGround));
  //   angleOffTarget = table.getEntry("tx").getDouble(0);

  //   System.out.println("distance from target:" + distance);
  //   System.out.println("angle off center target:" + angleOffTarget);
        
  //   data.setAngleOffTarget((float)angleOffTarget);
  //   data.setDistanceAwayFromTarget((float)distance);
    
  //   setLED(false);
  //   stopActiveVisionMode();
  //   setDriverMode();

  //   return data;
  // }

  public void setLED (boolean turnOn) {
		table.getEntry("ledMode").forceSetNumber(turnOn ? 3 : 1); // 3 - on, 1 = off, 2 - blink
  }
    
	public void flashLED (){
		table.getEntry("ledMode").forceSetNumber(2);
  }
    
  public void setDriverMode () {
		setLED(false);
		table.getEntry("camMode").forceSetNumber(1);
	}

	public static void setVisionTrackingMode () {
		table.getEntry("camMode").forceSetNumber(0);
  }

  public void setVisionToActiveTrackingMode () {
    table.getEntry("snapshot").forceSetNumber(1);
  }

  public void stopActiveVisionMode () {
    table.getEntry("snapshot").forceSetNumber(0);
  }
}
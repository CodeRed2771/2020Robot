package frc.robot;

import java.lang.Math;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {

  private static double CameraHeight = 9; // NEED TO BE MORE ACCURATE
  private static double TargetHeight = 92; // NEED TO BE MORE ACCURATE
  public static double CameraAngle = 26.4;  // NEED TO BE MORE ACCURATE
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

  public static double getDistanceAdjustedAngle() {

    return (getAngleOffset()+(-(getDistanceFromTarget()-180)/360));

    // if (getDistanceFromTarget() < 180) {  // 15 feet
    //     return getAngleOffset() + ((180 - getDistanceFromTarget()) * .01);
    // } else {
    //     return getAngleOffset() - ((getDistanceFromTarget() - 180) * .01);
    // }

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

  public static void setLED (boolean turnOn) {
		table.getEntry("ledMode").forceSetNumber(turnOn ? 3 : 1); // 3 - on, 1 = off, 2 - blink
  }
    
	public static void flashLED (){
		table.getEntry("ledMode").forceSetNumber(2);
  }
    
  public static void setDriverMode () {
		setLED(false);
		table.getEntry("camMode").forceSetNumber(1);
	}

	public static void setVisionTrackingMode () {
    setLED(true);
		table.getEntry("camMode").forceSetNumber(0);
  }

  public static void setVisionToActiveTrackingMode () {
    table.getEntry("snapshot").forceSetNumber(1);
  }

  public static void stopActiveVisionMode () {
    table.getEntry("snapshot").forceSetNumber(0);
  }
}
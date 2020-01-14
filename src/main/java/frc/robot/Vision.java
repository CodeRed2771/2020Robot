package frc.robot;

import java.lang.Math;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {

  private static float CameraHeight = 36; // NEED TO BE MORE ACCURATE
  private static float TargetHeight = 92; // NEED TO BE MORE ACCURATE
  private static float CameraAngle = 30;  // NEED TO BE MORE ACCURATE
  private static double LIMELIGHT_Y_AXIS_FOV = 45.7;
  private NetworkTable table = null;

  public Vision () {
    table = NetworkTableInstance.getDefault().getTable("limelight");
  }

  public VisionData getDistanceAndAngleOffTarget (VisionData data) {

    double degrees;
    double ty0;
    double degreesTargetOffGround;
    double distance;
    double angleOffTarget;

    setVisionTrackingMode();
    setLED(true);
    
    ty0 = table.getEntry("ty0").getDouble(0);
    degrees = ty0 *(LIMELIGHT_Y_AXIS_FOV/2);
    degreesTargetOffGround = CameraAngle - degrees;
    distance = (TargetHeight - CameraHeight) / Math.tan(Math.toRadians(degreesTargetOffGround));
    angleOffTarget = table.getEntry("tx").getDouble(0);

    System.out.println("distance from target:" + distance);
    System.out.println("angle off center target:" + angleOffTarget);
        
    data.setAngleOffTarget((float)angleOffTarget);
    data.setDistanceAwayFromTarget((float)distance);
    
    setLED(false);
    stopActiveVisionMode();
    setDriverMode();

    return data;
  }

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

	private void setVisionTrackingMode () {
		table.getEntry("camMode").forceSetNumber(0);
    }

  public void setVisionToActiveTrackingMode () {
    table.getEntry("snapshot").forceSetNumber(1);
  }

  public void stopActiveVisionMode () {
    table.getEntry("snapshot").forceSetNumber(0);
  }

}
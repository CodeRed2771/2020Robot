package frc.robot;

import java.lang.Math;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {

    private static float CameraHeight = 36;
    private static float TargetHeight = 92;
    private static float CameraAngle = 30;
    private static float LIMELIGHT_Y_AXIS_PIXELS = 240;
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

    public void setLED(boolean turnOn) {
		table.getEntry("ledMode").forceSetNumber(turnOn ? 3 : 1); // 3 - on, 1 = off, 2 - blink
    }
    
	public void flashLED(){
		table.getEntry("ledMode").forceSetNumber(2);
    }
    
    public void setDriverMode() {
		setLED(false);
		table.getEntry("camMode").forceSetNumber(1);
	}

	private void setVisionTrackingMode() {
		table.getEntry("camMode").forceSetNumber(0);
    }

}


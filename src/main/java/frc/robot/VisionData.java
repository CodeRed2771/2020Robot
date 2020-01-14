package frc.robot;

public class VisionData {

    private float DistanceAwayFromTarget;
    private float AngleOffTarget;
    
    public VisionData() {
        DistanceAwayFromTarget = 0;
        AngleOffTarget = 0;
    }

    public float getDistanceAwayFromTarget() {
		return DistanceAwayFromTarget;
	}
	
	public void setDistanceAwayFromTarget(float distanceAwayFromTarget) {
		DistanceAwayFromTarget = distanceAwayFromTarget;
	}
	
	public float getAngleOffTarget() {
		return AngleOffTarget;
	}
	
	public void setAngleOffTarget(float angleOffTarget) {
		AngleOffTarget = angleOffTarget;
	}

}

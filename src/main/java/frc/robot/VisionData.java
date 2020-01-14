package frc.robot;

public class VisionData {

    private float DistanceAwayFromTarget;
	private float AngleOffTarget;
	private float HoodAngle;
    
    public VisionData() {
        DistanceAwayFromTarget = 0;
		AngleOffTarget = 0;
		HoodAngle = 60;
    }

    public float getDistanceAwayFromTarget() {
		return DistanceAwayFromTarget;
	}
	
	public void setDistanceAwayFromTarget(float distanceAwayFromTarget) {
		this.DistanceAwayFromTarget = distanceAwayFromTarget;
	}
	
	public float getAngleOffTarget() {
		return AngleOffTarget;
	}
	
	public void setAngleOffTarget(float angleOffTarget) {
		this.AngleOffTarget = angleOffTarget;
	}

	public float getHoodAngle () {
		return HoodAngle;
	}

	public void setHoodAngle (float hoodAngle) {
		this.HoodAngle = hoodAngle;
	}

}
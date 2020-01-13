/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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

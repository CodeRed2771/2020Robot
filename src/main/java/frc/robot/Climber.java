/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Climber {
	
	private static boolean startClimbing = false;
	private static boolean levelScale = false;
	private static boolean dropBellyPan = false;
	private static boolean pickUpBellyPan = false;
	private static boolean lowClimberPosition = true;
	private static boolean colorMatchPosition = false;
	private static boolean climbHighPosition = false;
	private static Climber instance;
	private static double currentLiftSetpoint = 0;


    public Climber() {
	
	}

	public static Climber getInstance () {
        if (instance == null) {
            instance = new Climber();
        }
        return instance;
    }

	public static void tick() {

		if (levelScale) {

		}

		if (dropBellyPan) {

		}

		if (pickUpBellyPan) {

		}

		if (lowClimberPosition) {
			climbHighPosition(false);
			colorWheelPosition(false);
			startClimbing(false);

		}

		if (colorMatchPosition) {
			climbHighPosition(false);
			lowClimberPosition(false);
			startClimbing(false);
		}

		if (climbHighPosition) {
			lowClimberPosition(false);
			colorWheelPosition(false);
			startClimbing(false);

		}

		if (startClimbing) {
			
		}
	}

	public static void setLevelScale (boolean levelScale) {
		Climber.levelScale = levelScale;
	}

	public static void dropBellyPan(boolean dropBellyPan) {
		Climber.dropBellyPan = dropBellyPan;
	}

	public static void pickUpBellyPanAndContinueClimbing (boolean pickUpBellyPan) {
		Climber.pickUpBellyPan = pickUpBellyPan;
	}

	public static void lowClimberPosition (boolean lowClimberPosition) {
		Climber.lowClimberPosition = lowClimberPosition;
	}

	public static void colorWheelPosition (boolean colorWheelPosition) {
		Climber.colorMatchPosition = colorWheelPosition;
	}

	public static void climbHighPosition (boolean climberHighPosition) {
		Climber.climbHighPosition = climberHighPosition;
	}

	public static void startClimbing (boolean startClimbing) {
		Climber.startClimbing = startClimbing;
	}
}

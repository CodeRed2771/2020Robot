package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

public class ColorSensor {

    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch colorMatcher = new ColorMatch();
    private final Color BlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);   // NEED TO BE CALIBRATED
    private final Color GreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);  // NEED TO BE CALIBRATED
    private final Color RedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);    // NEED TO BE CALIBRATED
    private final Color YellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113); // NEED TO BE CALIBRATED

    public ColorSensor () {
        colorMatcher.addColorMatch(BlueTarget);
        colorMatcher.addColorMatch(GreenTarget);
        colorMatcher.addColorMatch(RedTarget);
        colorMatcher.addColorMatch(YellowTarget);
    }

    public void spinWheelThreeToFiveTimes () {

    }

    public void matchColor () {

    }

}

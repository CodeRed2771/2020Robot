package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DriverStation;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

public class ColorSensor {

    private final TalonSRX spinMotor = new TalonSRX(Wiring.COLOR_WHEEL_SPINNER);

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
        // SPINMOTOR NEEDS TO SPIN 131072 TICKS TO SPIN CONTROL PANEL 4 TIMES WITHOUT SLIPPAGE
        int timeColorPassed = 0;
        int trackOfColor = 0;
        Color currentColor = colorSensor.getColor();
        Color colorRobotIsOn;
        spinMotor.set(ControlMode.PercentOutput, -.5);
        switch (timeColorPassed) {
            case 0:
                colorRobotIsOn = colorSensor.getColor();
                if (currentColor == colorRobotIsOn) {
                    trackOfColor++;
                    if (trackOfColor == 7) {
                        spinMotor.set(ControlMode.PercentOutput, 0);
                    }
                    timeColorPassed++;
                }
            break;

        }
    }

    public void matchColor () {
        String gameData;
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        Color detectedColor = colorSensor.getColor();
        if (gameData.length() > 0) {
            switch (gameData.charAt(0)) {
                case 'B':
                    if (detectedColor == RedTarget) {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    } else if (detectedColor == YellowTarget) {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    } else if (detectedColor == GreenTarget) {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    } else {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    }
                    break;
                case 'G':
                    if (detectedColor == RedTarget) {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    } else if (detectedColor == BlueTarget) {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    } else if (detectedColor == YellowTarget) {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    } else {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    }
                    break;
                case 'R':
                    if (detectedColor == BlueTarget) {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    } else if (detectedColor == YellowTarget) {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    } else if (detectedColor == GreenTarget) {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    } else {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    }
                    break;
                case 'Y':
                    if (detectedColor == GreenTarget) {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    } else if (detectedColor == BlueTarget) {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    } else if (detectedColor == RedTarget) {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    } else {
                        // WRITE CODE FOR HOW MUCH TO TURN MOTOR BASED OFF CURRENT POSITION, SUBTRACT OR ADD TICKS.
                    }
                    break;
                default:
                    // DO NOTHING
                    break;
            }
        } else {
        // NOTHING RECEIVED
        }
    }
}

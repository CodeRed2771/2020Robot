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

    private static final TalonSRX spinMotor = new TalonSRX(Wiring.COLOR_WHEEL_SPINNER_AND_TRAVERSER);

    private static final I2C.Port i2cPort = I2C.Port.kOnboard;
    private static final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    private static final ColorMatch colorMatcher = new ColorMatch();
    private static final Color BlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);   // NEED TO BE CALIBRATED
    private static final Color GreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);  // NEED TO BE CALIBRATED
    private static final Color RedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);    // NEED TO BE CALIBRATED
    private static final Color YellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113); // NEED TO BE CALIBRATED
    private boolean isSpinning = false;
    private static ColorSensor instance;
    private static boolean isEnabled = false;
    private static int timesColorPassed = 0;
    private static String colorString;
    private static Color lastColorSeen = BlueTarget;
    private static boolean spinningCompleted = false;
   
    public ColorSensor () {
        colorMatcher.addColorMatch(BlueTarget);
        colorMatcher.addColorMatch(GreenTarget);
        colorMatcher.addColorMatch(RedTarget);
        colorMatcher.addColorMatch(YellowTarget);
    }

    public static ColorSensor getInstance() {
        if (instance == null){
            instance = new ColorSensor();
        }
        return instance;
    }

    public void startSpinning() {
        isSpinning = true;
        timesColorPassed = 0;
        spinMotor.set(ControlMode.PercentOutput, -.5);
        lastColorSeen = colorSensor.getColor();
        spinningCompleted = false;
    }

    public static void tick () {
        // SPINMOTOR NEEDS TO SPIN 131072 TICKS TO SPIN CONTROL PANEL 4 TIMES WITHOUT SLIPPAGE
        Color currentColor = colorSensor.getColor();
        ColorMatchResult match = colorMatcher.matchClosestColor(currentColor);

        if (match.color == RedTarget && lastColorSeen != RedTarget) {
            timesColorPassed++;
            lastColorSeen = match.color;
        } 
        lastColorSeen = match.color;

        if (match.color == BlueTarget) {
            colorString = "Blue";
        } else if (match.color == RedTarget) {
            colorString = "Red";
        } else if (match.color == GreenTarget) {
            colorString = "Green";
        } else if (match.color == YellowTarget) {
            colorString = "Yellow";
        } else {
            colorString = "Unknown";
        }
        
        // SmartDashboard.putNumber("Red", currentColor.red);
        // SmartDashboard.putNumber("Green", currentColor.green);
        // SmartDashboard.putNumber("Blue", currentColor.blue);
        // SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putNumber("colorROT", timesColorPassed);
        SmartDashboard.putString("Detected Color", colorString);


        if (timesColorPassed >= 7) {
            spinMotor.set(ControlMode.PercentOutput, 0);
            currentColor = null;
            spinningCompleted = true;
        }
    }

    public boolean isSpinningCompleted() {
        return spinningCompleted;
    }

    public void stopSpinning () {
        spinMotor.set(ControlMode.PercentOutput, 0);
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
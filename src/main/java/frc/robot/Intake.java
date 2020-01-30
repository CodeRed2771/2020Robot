package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.libs.CurrentBreaker;

public class Intake {

    private static Intake instance;
    private static CANSparkMax intakeMotor = new CANSparkMax(Wiring.INTAKE_MOTOR_ID, MotorType.kBrushless);
    private static DoubleSolenoid pistonArm;
    private static Compressor compressor;
    private static int BallCount = 0;
    public static CurrentBreaker currentBreaker;

    public Intake () {
        // pistonArm1 = new DoubleSolenoid(forwardChannel, reverseChannel);
        // pistonArm2 = new DoubleSolenoid(forwardChannel, reverseChannel);
        // compressor = new Compressor();
    }

    public static Intake getInstance () {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    public static void moveIntakeDown() {
        pistonArm.set(DoubleSolenoid.Value.kForward);
    }

    public static void moveIntakeUp () {
        pistonArm.set(DoubleSolenoid.Value.kReverse);
    }

    public static void runIntakeForwards () {
        intakeMotor.set(-.7);
    }

    public static void stopIntake () {
        intakeMotor.set(0);
    }

    public static void runIntakeBackwards () {
        intakeMotor.set(.7);
    }

    
        // new CurrentBreaker(Wiring.Intake_PDP_PORT, Callibration.INTAKE_MAX_CURRENT, 250);
        // CurrentBreaker = new CurrentBreaker(Wiring.Intake_PDP_PORT, Callibration.INTAKE_MAX_CURRENT);
        // return (currentBreaker.tripped());
        // currentBreaker.reset();
    
        public static boolean intakeStalled() {
            return (currentBreaker.tripped());
        }

    public static void ballCount (){
    }
}
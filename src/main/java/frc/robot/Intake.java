package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {

    private static Intake instance;
    public TalonSRX intakeMotor = new TalonSRX(Wiring.INTAKE_MOTOR_ID);
    DoubleSolenoid pistonArm1;
    DoubleSolenoid pistonArm2;
	Compressor compressor;

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

    public void moveIntakeDown() {
        pistonArm1.set(DoubleSolenoid.Value.kForward);
        pistonArm2.set(DoubleSolenoid.Value.kForward);
    }

    public void moveIntakeUp () {
        pistonArm1.set(DoubleSolenoid.Value.kReverse);
        pistonArm2.set(DoubleSolenoid.Value.kReverse);
    }

    public void runIntakeForwards () {
        intakeMotor.set(ControlMode.PercentOutput,1);
    }

    public void stopIntake () {
        intakeMotor.set(ControlMode.PercentOutput,0);
    }

    public void runIntakeBackwards () {
        intakeMotor.set(ControlMode.PercentOutput,-1);
    }
}

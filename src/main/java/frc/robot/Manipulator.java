// package frc.robot;
// //Hi - what did you want me to look at??
// //Hello we are working on a ball counter and need intake stalled info from last year so we’re isolating it
// import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.FeedbackDevice;
// import com.ctre.phoenix.motorcontrol.NeutralMode;
// import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
// import com.ctre.phoenix.motorcontrol.can.TalonSRX;
 
// import edu.wpi.first.wpilibj.DoubleSolenoid;
// import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.libs.CurrentBreaker;
 
// public class Manipulator {
   
//    private static CurrentBreaker currentBreaker = new CurrentBreaker(Wiring.INTAKE_MOTOR_ID, Calibration.INTAKE_MAX_CURRENT, 250);
 
//     linkageCurrentBreaker = new CurrentBreaker(Wiring.LINKAGE_PDP_PORT, 15, 2000);
 
//     void resetIntakeStallDetector();
 
//    public static void tick() {
//    }
//       // UTILITY METHODS ---------------------------------------------------------
 
//    public static boolean intakeStalled() {
//        return (currentBreaker.tripped());
//    }
 
//    public static void resetIntakeStallDetector() {
//        currentBreaker.reset();
//        ejectEndTime = aDistantFutureTime();
//    }
 
//    private static double aDistantFutureTime() {
//        return System.currentTimeMillis() + 900000; // 15 minutes in the future
//     }

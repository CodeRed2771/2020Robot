package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//The purpose of this class is to turn the robot until we are on target.

public class AutoAlign extends AutoBaseClass{

private double angleOffset = 0;

    public void start(){
        super.start();
        Vision.getInstance();
        Vision.setVisionTrackingMode();
    }
    public void stop(){
        super.stop();
    }

    @Override
    public void tick() {
        if (isRunning()){
        DriveAuto.tick();
        SmartDashboard.putNumber("Auto Step", getCurrentStep());

            switch (getCurrentStep()){
                case 0:
                    angleOffset = Vision.getAngleOffset();
                    if (Vision.seesTarget() == true) {
                        advanceStep();
                    } else if (Vision.seesTarget() == false) {
                        setStep(10);
                    }
                    break;
                
                case 1:
                    DriveAuto.turnDegrees(angleOffset, .3);
                    setTimerAndAdvanceStep(1000);
                    break;

                case 2:
                    if (DriveAuto.turnCompleted()){
                        advanceStep();
                    }
                    break;
                case 3:
                
                    angleOffset = Vision.getAngleOffset();
                    SmartDashboard.putNumber("Angle Offset", angleOffset);
                    SmartDashboard.putBoolean("Sees Target", Vision.seesTarget());
                    stop();
                    if (Vision.seesTarget() && (Math.abs(angleOffset) <= 1)){
                        System.out.println("On Target!");
                        stop();
                    }
                    // else {
                    //     setStep(0);
                    // }
                    break;

                case 10:
                    turnDegrees(60, 1);
                    setTimer(6000);
                    advanceStep();
                    break;
                case 11:
                    if (Vision.seesTarget() == true) {
                        setStep(0);
                    } else if (Vision.seesTarget() == false) {
                        setStep(10);
                    }



            }
        }


    }
   
    
}
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
                    angleOffset = Vision.getDistanceAdjustedAngle();
                    if (Vision.seesTarget() == true) {
                        advanceStep();
                    } else if (Vision.seesTarget() == false) {
                        setStep(10);
                    }
                    break;           
                case 1: //thought: one potential issue that could be happening is if the camera was picking up a target that wasn't actually the target... ~AR 
                    DriveAuto.turnDegrees(angleOffset, .6);
                    setTimerAndAdvanceStep(2000);
                    break;

                case 2:
                    if (DriveAuto.hasArrived()){
                        advanceStep();
                    }
                    break;
                case 3:
                    angleOffset = Vision.getDistanceAdjustedAngle();
                    SmartDashboard.putNumber("Angle Offset", angleOffset);
                    SmartDashboard.putBoolean("Sees Target", Vision.seesTarget());
                    if (Vision.onTarget()){
                        System.out.println("On Target!");
                        stop();
                    }
                    else {
                        setStep(0);
                    }
                    break;
                case 10:
                    turnDegrees(60, 1);
                    if (Vision.seesTarget() == true) {
                        setStep(0);
                    }
                    break;
            }
        }


    }
   
    
}
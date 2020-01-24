package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//THIS WAS WRITTEN BY ANNALISE AKA CODE MENACE
//The purpose of this class is to turn the robot until we are on target.

public class AutoAlign extends AutoBaseClass{

private double angleOffset = 0;

    public void start(){
        super.start();
        Vision.getInstance();
    }
    public void stop(){
        super.stop();
    }

    @Override
    public void tick() {
        if (isRunning()){
            switch (getCurrentStep()){
                case 1:
                    angleOffset = Vision.getAngleOffset();
                    if (Vision.seesTarget())
                        advanceStep();
                    break;
                
                case 2:
                    DriveAuto.turnDegrees(-angleOffset, .3);
                    setTimerAndAdvanceStep(1000);
                    break;

                case 3:
                    if (DriveAuto.turnCompleted()){
                        advanceStep();
                    }
                    break;
                case 4:
                    angleOffset = Vision.getAngleOffset();
                    if (Vision.seesTarget() && (Math.abs(angleOffset) <= 1)){
                        System.out.println("On Target!");
                        stop();
                    }
                    else {
                        setStep(1);
                    }




            }
        }


    }
   
    
}
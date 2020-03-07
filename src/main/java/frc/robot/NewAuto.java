package frc.robot;

import java.util.ArrayList;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.opencv.core.Point;

public class NewAuto extends AutoBaseClass {

    private double angleOffset = 0;

    private static ArrayList<Point> BallLocations = new ArrayList<>();

    public NewAuto () {

    }

    public void start() {
        super.start();
    }

    public void start(boolean autoShoot){
        super.start(autoShoot);
    }

    public void stop() {
        super.stop();
    }

    @Override
    public void tick() {
        if (isRunning()) {
            DriveAuto.tick();
            SmartDashboard.putNumber("Auto Step", getCurrentStep());
            switch (getCurrentStep()) {
            case 0:
                //get ball list
                BallLocations.clear();
               // BallLocations = BallVision.GetBallLocations();
                advanceStep();
                break;
            case 1:
                //calculate strafe distance and direction
                double Distance = BallLocations.get(0).y * Math.sin(Math.toRadians(BallLocations.get(0).x));
                double Degrees = (Distance < 0) ? -90 : 90;
                driveInches(Math.abs(Distance), Degrees, .8);
                //command serve base
                setTimerAndAdvanceStep(4000);
                break;
            case 2:
                if (driveCompleted()){
                    advanceStep();
                }
                break;
            case 3:
                stop();
                break;
            }
        }
    }
}

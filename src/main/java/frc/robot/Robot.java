/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;


public class Robot extends TimedRobot {
  KeyMap gamepad;

  @Override
  public void robotInit() {
    gamepad = new KeyMap();
    ShooterSRX.getInstance();
    ShooterSRX.StopShooter();
    Intake.getInstance();
  }

  @Override
  public void teleopPeriodic() {
    if (gamepad.getButtonA(1)) {
      System.out.println("START SHOOTING");
      ShooterSRX.StartShooter();
    }
    if (gamepad.getButtonB(1)) {
      ShooterSRX.StopShooter();
    }
    // if (gamepad.getButtonDpadDown(1)) {
    //   Intake.moveIntakeDown();
    // }
    // if (gamepad.getButtonDpadUp(1)) {
    //   Intake.moveIntakeUp();
    // }
    // if (gamepad.getButtonTriggerRight(1)) {
    //   Intake.runIntakeForwards();
    // }
    // if (gamepad.getButtonTriggerLeft(1)) {
    //   Intake.runIntakeBackwards();
    // }
    // if (gamepad.getButtonBumperLeft(1)) {
    //   Intake.stopIntake();
    // }
    ShooterSRX.tick();
  }

  @Override
  public void robotPeriodic() {
  }

 
  @Override
  public void autonomousInit() {
   
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
   
  }


  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lifter extends SubsystemBase {
  WPI_TalonSRX m_spin1;
  WPI_TalonSRX m_spin2;

  /** Creates a new Launcher. */
  //We need two new constants for this...
  public Lifter() {
    //Here we declare the two new spin motors
    /* 
    m_spin1 = new WPI_TalonSRX(LauncherConstants.);
    m_spin2 = new WPI_TalonSRX(LauncherConstants.);
*/
    // m_launchWheel.setSmartCurrentLimit(kLauncherCurrentLimit);
    // m_feedWheel.setSmartCurrentLimit(kFeedCurrentLimit);
  }

  public void setSpinMotorSpeed(double speed) {
    m_spin1.set(-speed);
    m_spin2.set(speed);
  }

  // A helper method to stop both wheels. You could skip having a method like this and call the
  // individual accessors with speed = 0 instead
  public void stopSpin() {
    m_spin1.set(0);
    m_spin2.set(0);
  }
}

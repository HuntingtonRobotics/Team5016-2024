// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LauncherConstants;

public class Launcher extends SubsystemBase {
  // CANSparkMax m_launchWheel;
  WPI_TalonSRX m_lefty;
  WPI_TalonSRX m_righty;

  /** Creates a new Launcher. */
  public Launcher() {
    // m_launchWheel = new CANSparkMax(kLauncherID, MotorType.kBrushed);
    m_lefty = new WPI_TalonSRX(LauncherConstants.kLauncherLeftyID);
    m_righty = new WPI_TalonSRX(LauncherConstants.kLauncherRightyID);

    m_lefty.follow(m_righty);

    // m_launchWheel.setSmartCurrentLimit(kLauncherCurrentLimit);
    // m_feedWheel.setSmartCurrentLimit(kFeedCurrentLimit);
  }

  public Command getlaunchCommand(double speed) {
    return this.startEnd(() -> setMotorSpeed(speed), () -> stop());
  }

  public void setMotorSpeed(double speed) {
    m_lefty.set(speed);
    m_righty.set(speed);
  }

  // A helper method to stop both wheels. You could skip having a method like this and call the
  // individual accessors with speed = 0 instead
  public void stop() {
    m_lefty.set(0);
    m_righty.set(0);
  }
}

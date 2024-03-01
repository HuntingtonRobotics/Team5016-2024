// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LauncherConstants;

public class Launcher extends SubsystemBase {
  private CANSparkMax m_left;
  private CANSparkMax m_right;

  /** Creates a new Launcher. */
  public Launcher() {
    m_left = new CANSparkMax(LauncherConstants.kLauncherLeftyID, MotorType.kBrushless);
    m_right = new CANSparkMax(LauncherConstants.kLauncherRightyID, MotorType.kBrushless);

    m_left.follow(m_right); // set speed on right only

    m_left.setSmartCurrentLimit(LauncherConstants.kLauncherCurrentLimit);
    m_right.setSmartCurrentLimit(LauncherConstants.kLauncherCurrentLimit);
  }

  public Command getlaunchCommand(double speed) {
    return this.startEnd(() -> setMotorSpeed(speed), () -> stop());
  }

  public void setMotorSpeed(double speed) {
    m_right.set(speed);
  }

  // A helper method to stop both wheels. You could skip having a method like this and call the
  // individual accessors with speed = 0 instead
  public void stop() {
    m_right.set(0);
  }
}

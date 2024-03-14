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
  private CANSparkMax m_bottom; // SparkMax+motor mounted left side
  private CANSparkMax m_top; // SparkMax+motor mounted right side

  /** Creates a new Launcher. */
  public Launcher() {
    m_bottom = new CANSparkMax(LauncherConstants.kLauncherLeftyID, MotorType.kBrushless);
    m_top = new CANSparkMax(LauncherConstants.kLauncherRightyID, MotorType.kBrushless);
    
    m_top.setInverted(true);

    m_bottom.setSmartCurrentLimit(LauncherConstants.kLauncherCurrentLimit);
    m_top.setSmartCurrentLimit(LauncherConstants.kLauncherCurrentLimit);
  }

  public Command getlaunchCommand(double speed) {
    return this.startEnd(() -> setMotorSpeed(speed), () -> stop());
  }

  public void setMotorSpeed(double speed) {
  m_top.set(-0.8);
  m_bottom.set(-0.3);

  }

  public void launchForAmp() {
    m_top.set(-0.3125);
    m_bottom.set(-0.175);
  }

  public boolean isAtSpeed() {
    return (m_top.get() == -0.3125)
     && (m_bottom.get() == -0.175);
  }

  // A helper method to stop both wheels. You could skip having a method like this and call the
  // individual accessors with speed = 0 instead
  public void stop() {
    m_top.set(0);
    m_bottom.set(0);

  }
}

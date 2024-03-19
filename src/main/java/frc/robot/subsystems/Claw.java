// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClawConstants;

/** Subsystem for the "climber" lifter-in-a-box component */
public class Claw extends SubsystemBase {
  private CANSparkMax m_right;
  private CANSparkMax m_left;

  public Claw() {
     
    m_right = new CANSparkMax(ClawConstants.kRightClaw, MotorType.kBrushless);
    m_left = new CANSparkMax(ClawConstants.kLeftClaw, MotorType.kBrushless);

    m_left.follow(m_right); // set speed on right only

    m_right.setSmartCurrentLimit(ClawConstants.CurrentLimit);
    m_left.setSmartCurrentLimit(ClawConstants.CurrentLimit);
  }
  
  public Command getClawUp() {
    return this.startEnd(
        () -> {
          m_right.set(1.0);
        },
        () -> {
          m_right.set(0);
        });
  }

  public Command getClawDown() {
    return this.startEnd(
        () -> {
          m_right.set(-1.0);
        },
        () -> {
          m_right.set(0);
        });
  }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClawConstants;

/** Subsystem for the lifter-in-a-box component */
public class Claw extends SubsystemBase {
  private CANSparkMax m_spin1;
  private CANSparkMax m_spin2;

  public Claw() {
     
    m_spin1 = new CANSparkMax(ClawConstants.kRightClaw, MotorType.kBrushless);
    m_spin2 = new CANSparkMax(ClawConstants.kLeftClaw, MotorType.kBrushless);

    m_spin2.follow(m_spin1);

    m_spin1.setSmartCurrentLimit(ClawConstants.CurrentLimit);
    m_spin2.setSmartCurrentLimit(ClawConstants.CurrentLimit);
  }
  
  public Command getClawUp() {
    return this.startEnd(
        () -> {
          m_spin1.set(1.0);
          m_spin2.set(-1.0);
        },
        () -> {
          m_spin1.set(0);
          m_spin2.set(0);
        });
  }

  public Command getClawDown() {
    return this.startEnd(
        () -> {
          m_spin1.set(-1.0);
          m_spin2.set(1.0);
        },
        () -> {
          m_spin1.set(0);
          m_spin2.set(0);
        });

      }
}

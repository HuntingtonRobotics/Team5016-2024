// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClawConstants;
import frc.robot.Constants.LifterConstants;

public class Claw extends SubsystemBase {
  WPI_TalonSRX m_spin1;
  WPI_TalonSRX m_spin2;

  /** Creates a new Launcher. */
  //We need two new constants for this...
  public Claw() {
    //Here we declare the two new spin motors
     
    m_spin1 = new WPI_TalonSRX(ClawConstants.kRightClaw);
    m_spin2 = new WPI_TalonSRX(ClawConstants.kLeftClaw);

    m_spin2.follow(m_spin1);

    // m_launchWheel.setSmartCurrentLimit(kLauncherCurrentLimit);
    // m_feedWheel.setSmartCurrentLimit(kFeedCurrentLimit);
    
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

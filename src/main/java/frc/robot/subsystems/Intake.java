// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  public CANSparkMax m_intakeMotor;

  /** Creates a new Launcher. */
  public Intake() {
    m_intakeMotor = new CANSparkMax(IntakeConstants.IntakeMotorID, MotorType.kBrushless);
    m_intakeMotor.setSmartCurrentLimit(IntakeConstants.CurrentLimit);
  }

  /**
   * This method is an example of the 'subsystem factory' style of command creation. A method inside
   * the subsytem is created to return an instance of a command. This works for commands that
   * operate on only that subsystem, a similar approach can be done in RobotContainer for commands
   * that need to span subsystems. The Subsystem class has helper methods, such as the startEnd
   * method used here, to create these commands.
   */
  public Command getIntakeCommand() {
    // The startEnd helper method takes a method to call when the command is initialized and one to
    // call when it ends
    return this.startEnd(
        // When the command is initialized, set the wheels to the intake speed values
        () -> {
          // setFeedWheel(IntakeConstants.IntakeFeederSpeed);
          setIntakeSpeed(IntakeConstants.IntakeSpeed);
        },
        // When the command stops, stop the wheels
        () -> {
          stop();
        });
  }

  public Command reverseIntakeCommand() {
    // The startEnd helper method takes a method to call when the command is initialized and one to
    // call when it ends
    return this.startEnd(
        // When the command is initialized, set the wheels to the intake speed values
        () -> {
          // setFeedWheel(-IntakeConstants.IntakeFeederSpeed);
          setIntakeSpeed(-IntakeConstants.IntakeSpeed);
        },
        // When the command stops, stop the wheels
        () -> {
          stop();
        });
  }

  // Proof of concept
  public Command setIntakeSpeed(double speed) {
    return this.run(() -> setFeedWheel(speed));
  }

  // An accessor method to set the speed (technically the output percentage) of the feed wheel
  public void setFeedWheel(double speed) {
    m_intakeMotor.set(speed);
  }

  // A helper method to stop both wheels. You could skip having a method like this and call the
  // individual accessors with speed = 0 instead
  public void stop() {
    // m_intakeMotor.set(0);
    setFeedWheel(0.0);
  }

  public void resume() {
    m_intakeMotor.set(-0.7);
  }
}

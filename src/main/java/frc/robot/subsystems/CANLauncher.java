// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.LauncherConstants.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANLauncher extends SubsystemBase {
  // CANSparkMax m_launchWheel;
  WPI_TalonSRX m_feedWheel_lower;
  WPI_TalonSRX m_feedWheel_upper;
  /** Creates a new Launcher. */
  public CANLauncher() {
    // m_launchWheel = new CANSparkMax(kLauncherID, MotorType.kBrushed);
    m_feedWheel_lower = new WPI_TalonSRX(kLowerFeederID);
    m_feedWheel_upper = new WPI_TalonSRX(kUpperFeederID);

    m_feedWheel_lower.follow(m_feedWheel_upper);

    // m_launchWheel.setSmartCurrentLimit(kLauncherCurrentLimit);
    // m_feedWheel.setSmartCurrentLimit(kFeedCurrentLimit);
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
          setFeedWheel(kIntakeFeederSpeed);
          //m_feedWheel_lower.set(kIntakeFeederSpeed);
          //  setLaunchWheel(kIntakeLauncherSpeed);
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
          setFeedWheel(-kIntakeFeederSpeed);
          //m_feedWheel_lower.set(kIntakeFeederSpeed);
          //  setLaunchWheel(kIntakeLauncherSpeed);
        },
        // When the command stops, stop the wheels
        () -> {
          stop();
        });
  }

  public Command setIntakeSpeed(double speed) {
    return this.runOnce(
        () -> {
          setFeedWheel(kIntakeFeederSpeed);
        });
  }

  // An accessor method to set the speed (technically the output percentage) of the launch wheel
  // public void setLaunchWheel(double speed) {
  // m_launchWheel.set(speed);
  // }

  // An accessor method to set the speed (technically the output percentage) of the feed wheel
  public void setFeedWheel(double speed) {
    m_feedWheel_lower.set(speed);
    m_feedWheel_upper.set(speed);
  }

  // A helper method to stop both wheels. You could skip having a method like this and call the
  // individual accessors with speed = 0 instead
  public void stop() {
    //  m_launchWheel.set(0);
    m_feedWheel_lower.set(0);
    m_feedWheel_upper.set(0);
  }
}

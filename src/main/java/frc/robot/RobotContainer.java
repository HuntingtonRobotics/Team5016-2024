// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
// import frc.robot.subsystems.PWMDrivetrain;
// import frc.robot.subsystems.PWMLauncher;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.CANLifter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here.
  private final CANDrivetrain m_drivetrain = new CANDrivetrain();
  private final Intake m_intake = new Intake();
  private final Launcher m_launcher = new Launcher();
  private final CANLifter m_lifter = new CANLifter();

  /*The gamepad provided in the KOP shows up like an XBox controller if the mode switch is set to X mode using the
   * switch on the top.*/
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_operatorController =
      new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be accessed via the
   * named factory methods in the Command* classes in edu.wpi.first.wpilibj2.command.button (shown
   * below) or via the Trigger constructor for arbitary conditions
   */
  private void configureBindings() {
    // Set the default command for the drivetrain to drive using the joysticks
    m_drivetrain.setDefaultCommand(
        new RunCommand(
            () ->
                m_drivetrain.arcadeDrive(
                    m_driverController.getRightX(),
                    m_driverController
                        .getLeftY()), // For 2024 we had to swap Y & X to map properly to the XBox
            // controller joysticks
            m_drivetrain));

    /*Create an inline sequence to run when the operator presses and holds the A (green) button. Run the PrepareLaunch
     * command for 1 seconds and then run the LaunchNote command */
    // m_operatorController
    //     .a()
    //     .whileTrue(
    //         new PrepareLaunch(m_launcher)
    //             .withTimeout(LauncherConstants.kLauncherDelay)
    //             .andThen(new LaunchNote(m_launcher))
    //             .handleInterrupt(() -> m_launcher.stop()));

    // Set up a binding to run the intake command while the operator is pressing and holding the
    // left Bumper
    m_driverController.leftBumper().whileTrue(m_intake.getIntakeCommand());

    m_driverController.rightBumper().whileTrue(m_intake.reverseIntakeCommand());

    // Launcher commands
    //  Temporarily map XYAB buttons to different speeds while we figure out the best launch speed
    m_driverController.x().whileTrue(m_launcher.getlaunchCommand(0.2));
    m_driverController.y().whileTrue(m_launcher.getlaunchCommand(0.4));
    m_driverController.b().whileTrue(m_launcher.getlaunchCommand(0.6));
    m_driverController.a().whileTrue(m_launcher.getlaunchCommand(0.8));

    // POC: Use LT to control speed of intake to determine the best constant speed
    //  This is extra and probably not neeeded for competition
    // m_driverController
    //     .leftTrigger(0.25)
    //     .whileTrue(
    //         new RunCommand(() -> m_intake.setFeedWheel(m_driverController.getLeftTriggerAxis())))
    //     .onFalse(new RunCommand(() -> m_intake.setFeedWheel(0)));

    // Lifter controlled with POV control i.e. "hat"
    m_driverController.povUp().whileTrue(m_lifter.getLifterUpCommand());
    m_driverController.povDown().whileTrue(m_lifter.getLifterDownCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_drivetrain);
  }
}

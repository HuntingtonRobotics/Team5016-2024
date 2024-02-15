// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.PrepareLaunch;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.LauncherArm;
import frc.robot.subsystems.Claw;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here.
  private final SwerveDriveContainer swerve = new SwerveDriveContainer();
  private final CANDrivetrain m_drivetrain = new CANDrivetrain();
  private final Intake m_intake = new Intake();
  private final Launcher m_launcher = new Launcher();
  private final LauncherArm m_launcherArm = new LauncherArm();
  private final Claw m_claw = new Claw();
  

  public final DigitalInput limitSwitch = new DigitalInput(0);
  public final Trigger limitSwitchTrigger = new Trigger(limitSwitch::get);

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
    configureDrivetrainBindings();

    // Set up a binding to run the intake command while the operator is pressing and holding the
    // left Bumper
    m_driverController.leftBumper().whileTrue(m_intake.getIntakeCommand());

    m_driverController.rightBumper().whileTrue(m_intake.reverseIntakeCommand());

    // Stop the intake when the limit switch is activated ("false")
    limitSwitchTrigger.toggleOnFalse(Commands.run(m_intake::stop));

    /*Create an inline sequence to run when the operator presses and holds the A (green) button. Run the PrepareLaunch
     * command for 1 seconds and then run the LaunchNote command */
    // m_operatorController
    m_driverController
        .a()
        .whileTrue(
            new PrepareLaunch(m_launcher)
                .withTimeout(LauncherConstants.kLauncherDelay)
                // .andThen(new LaunchNote(m_launcher))
                .andThen(m_intake.getIntakeCommand())
                .handleInterrupt(() -> m_launcher.stop()));

    // Launcher controlled with POV control i.e. "hat"
    m_driverController.povUp().whileTrue(m_launcherArm.getLauncherUpCommand());
    m_driverController.povDown().whileTrue(m_launcherArm.getLauncherDownCommand());


    m_driverController.povRight().whileTrue(m_claw.getClawDown());
    m_driverController.povLeft().whileTrue(m_claw.getClawUp());


  }

  private void configureDrivetrainBindings() {
    
    swerve.drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        swerve.drivetrain.applyRequest(() -> 
          swerve.drive.withVelocityX(-m_driverController.getLeftY() * swerve.MaxSpeed) // Drive forward with negative Y (forward)
                      .withVelocityY(-m_driverController.getLeftX() * swerve.MaxSpeed) // Drive left with negative X (left)
                      .withRotationalRate(-m_driverController.getRightX() * swerve.MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    m_driverController.b().whileTrue(swerve.drivetrain
        .applyRequest(() -> swerve.point.withModuleDirection(new Rotation2d(-m_driverController.getLeftY(), -m_driverController.getLeftX()))));
    m_driverController.x().whileTrue(swerve.drivetrain.applyRequest(() -> swerve.brake));

    // reset the field-centric heading
    m_driverController.start().onTrue(swerve.drivetrain.runOnce(() -> swerve.drivetrain.seedFieldRelative()));

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

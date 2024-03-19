// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AimAndRange;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveStraight;
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
  private final Intake m_intake = new Intake();
  private final Launcher m_launcher = new Launcher();
  private final LauncherArm m_launcherArm = new LauncherArm();
  private final Claw m_claw = new Claw();

  private final SendableChooser<Command> autonomousChooser = new SendableChooser<>();

  public final DigitalInput limitSwitch = new DigitalInput(0);
  public final Trigger limitSwitchTrigger = new Trigger(limitSwitch::get);

  Alliance assignedAlliance;
  SwerveRequest.FieldCentric swerveCmd;
  public DriveStraight m_driveStraightAuto = new DriveStraight(swerve.drivetrain);

  /*The gamepad provided in the KOP shows up like an XBox controller if the mode switch is set to X mode using the
   * switch on the top.*/
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_operatorController =
      new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    assignedAlliance = getAlliance();

    // Configure the trigger bindings
    configureBindings();

    AutonomousArgs autoArgs = BuildAutonomousArgs();
    
    autonomousChooser.setDefaultOption("Drive Only", Autos.driveForward(swerve, autoArgs));
    autonomousChooser.addOption("Launch then Drive", Autos.launchThenDriveForward(swerve, autoArgs, m_intake, m_launcher));
    SmartDashboard.putData(autonomousChooser);

    CameraServer.startAutomaticCapture(); // adds to dashboard

  }

  private AutonomousArgs BuildAutonomousArgs() {
    var args = new AutonomousArgs();

    double autoDriveDuration = SmartDashboard.getNumber("auto-DriveDuration", args.DriveDurationSeconds);

    args.DriveDurationSeconds = autoDriveDuration;
    args.LauncherMotorRampUpDelaySeconds = SmartDashboard.getNumber("auto-LauncherMotorRampUpDelay", args.LauncherMotorRampUpDelaySeconds);
    args.NoteDeliveryDurationSeconds = SmartDashboard.getNumber("auto-NoteDelivery", args.NoteDeliveryDurationSeconds);
    args.SequenceStopDeadlineSeconds = SmartDashboard.getNumber("auto-sequenceStop", args.SequenceStopDeadlineSeconds);
    args.SequenceGlobalTimeoutSeconds = SmartDashboard.getNumber("auto-sequenceGlobalTimeout", args.SequenceGlobalTimeoutSeconds);

    return args;
  }

  private Alliance getAlliance() {
    Optional<Alliance> ally = DriverStation.getAlliance();
    if (ally.isPresent()) {
      return ally.get();
    }

    return Alliance.Blue;
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be accessed via the
   * named factory methods in the Command* classes in edu.wpi.first.wpilibj2.command.button (shown
   * below) or via the Trigger constructor for arbitary conditions
   */
  private void configureBindings() {
    configureDrivetrainBindings();
    configureGameplayBindings();
  }

  private void configureDrivetrainBindings() {
    
    swerve.drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        swerve.drivetrain.applyRequest(() -> 
          swerve.drive.withVelocityY(-m_driverController.getLeftY() * swerve.MaxSpeed) // Drive forward with negative Y (forward)
                      .withVelocityX(m_driverController.getLeftX() * swerve.MaxSpeed) // Drive left with negative X (left)
                      .withRotationalRate(-m_driverController.getRightX() * swerve.MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    m_driverController.b().whileTrue(swerve.drivetrain
        .applyRequest(() -> swerve.point.withModuleDirection(new Rotation2d(-m_driverController.getLeftY(), -m_driverController.getLeftX()))));
    m_driverController.x().whileTrue(swerve.drivetrain.applyRequest(() -> swerve.brake));

    // reset the field-centric heading
    m_driverController.start().onTrue(swerve.drivetrain.runOnce(() -> swerve.drivetrain.seedFieldRelative()));

    // Auto aim & range - press button when getting close to an AprilTag
    m_driverController.y().whileTrue(AimAndRange.getCommand(swerve, assignedAlliance));

  }

  private void configureGameplayBindings() {
    // Set up a binding to run the intake command while the operator is pressing and holding the
    // left Bumper
    m_operatorController.leftBumper().onTrue(m_intake.run(()-> m_intake.setFeedWheel(Constants.IntakeConstants.IntakeFeederSpeed))).onFalse(m_intake.run(()->m_intake.setFeedWheel(0)));

    m_operatorController.rightBumper().onTrue(m_intake.run(()->m_intake.setFeedWheel(-Constants.IntakeConstants.IntakeFeederSpeed))).onFalse(m_intake.run(()->m_intake.setFeedWheel(0)));

    // Stop the intake when the limit switch is activated ("false")
    limitSwitchTrigger.toggleOnFalse(Commands.run(m_intake::stop));

    // LAUNCH - speeds optimized for Speaker
    m_operatorController.a().whileTrue(Commands.startEnd(m_launcher::launchForSpeaker, m_launcher::stop, m_launcher));

    // LAUNCH - speeds optimized for Amp
    m_operatorController.y().whileTrue(Commands.startEnd(m_launcher::launchForAmp, m_launcher::stop, m_launcher));
    //Command smartLaunch = 
    //   Commands.sequence(
    //     Commands.runOnce(m_launcher::launchForAmp, m_launcher),
    //     Commands.waitUntil(() -> m_launcher.isAtSpeed()),
    //     Commands.waitSeconds(2.0),
    //     Commands.runOnce(() -> m_intake.setFeedWheel(Constants.IntakeConstants.IntakeFeederSpeed), m_intake),
    //     Commands.waitUntil(() -> true),
    //     Commands.waitSeconds(1.0),
    //     Commands.runOnce(() -> {m_launcher.stop();m_intake.setFeedWheel(0); })
    //   )
    //   // .withTimeout(4.0)
    //   // .andThen(
    //   //   Commands.runOnce(() -> {m_launcher.stop();m_intake.setFeedWheel(0); })
      
    // );

    // Launcher controlled with POV control i.e. "hat"
    m_operatorController.povUp().whileTrue(m_launcherArm.getLauncherUpCommand());
    m_operatorController.povDown().whileTrue(m_launcherArm.getLauncherDownCommand());
    
    m_operatorController.rightTrigger().whileTrue(m_claw.getClawUp());
    m_operatorController.leftTrigger().whileTrue(m_claw.getClawDown());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //return autonomousChooser.getSelected();
    AutonomousArgs autoArgs = BuildAutonomousArgs();
    // return Autos.driveForward(swerve, autoArgs);
    return Autos.launchThenDriveForward(swerve, autoArgs, m_intake, m_launcher);
  }
}

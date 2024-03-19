// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.AutonomousArgs;
import frc.robot.Constants;
import frc.robot.SwerveDriveContainer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;

public final class Autos {

  /* Original example from template; note the command structure */
  // return new RunCommand(() -> drivetrain.arcadeDrive(-.5, 0))
  // .withTimeout(1)
  // .andThen(new RunCommand(() -> drivetrain.arcadeDrive(0, 0)));

  public static Command driveForward(SwerveDriveContainer swerve, AutonomousArgs args) {
    Command driveSequence = Commands.print("Starting driveForward sequence")
      .andThen(swerve.drivetrain.applyRequest(() -> swerve.drive.withVelocityY(swerve.MaxSpeed*0.25)))
      .andThen(Commands.deadline(
        Commands.waitSeconds(args.SequenceStopDeadlineSeconds)),
        swerve.drivetrain.applyRequest(() -> swerve.brake)
      )
      .andThen(Commands.print("driveForward sequence completed normally"));
      
    return driveSequence.handleInterrupt(() -> write("driveForward sequence interrupted"));
  }

  public static Command launchToSpeaker(Intake intakeSub, Launcher launcherSub, AutonomousArgs args) {
    Command launchSequence = Commands.print("Starting launchToSpeaker sequence")
        .andThen(Commands.runOnce(launcherSub::launchForSpeaker, launcherSub))
        .andThen(Commands.waitSeconds(args.LauncherMotorRampUpDelaySeconds))
        .andThen(Commands.deadline(
            Commands.waitSeconds(args.NoteDeliveryDurationSeconds), // run intake until this expires
            Commands.runOnce(() -> intakeSub.setFeedWheel(Constants.IntakeConstants.IntakeFeederSpeed), intakeSub)))
        .andThen(Commands.deadline(
            Commands.waitSeconds(args.SequenceStopDeadlineSeconds),
            Commands.runOnce(launcherSub::stop, launcherSub),
            Commands.runOnce(intakeSub::stop, intakeSub))
            .andThen(() -> write("launchToSpeaker sequence completed normally"))
            );

    return launchSequence.handleInterrupt(() -> write("launchToSpeaker sequence interrupted"));
  }

  public static Command intakeAbit(Intake intakeSub, AutonomousArgs args) {
    Command intakeSequence = Commands.print("Starting intakeAbit sequence") 
          .andThen(Commands.runOnce(() -> intakeSub.setFeedWheel(Constants.IntakeConstants.IntakeFeederSpeed), intakeSub))
          .andThen(Commands.deadline(
             Commands.waitSeconds(args.SequenceStopDeadlineSeconds),
             Commands.runOnce(intakeSub::stop, intakeSub)))
          .andThen(Commands.print("intakeABit sequence completed normally"));

    return intakeSequence.handleInterrupt(() -> write("intakeSequence interrupted"));
  }
    
  public static Command launchThenDriveForward(SwerveDriveContainer swerve, AutonomousArgs args, Intake intake, Launcher launcher) {
    return launchToSpeaker(intake, launcher, args)
      .andThen(driveForward(swerve, args).withTimeout(2.5));
      //.andThen(intakeAbit(intake, args))
      //.withTimeout(4.0); // No matter what, finish the sequence after X seconds
  }

  private static void write(String x) {
    System.out.println(x);
  }

  public static Command launchToSpeaker_Test() {
    Command launchSequence = Commands.runOnce(() -> write("Started launcher motor"))
        .andThen(Commands.waitSeconds(3.0).andThen(Commands.runOnce(() -> write("Waited 3 seconds"))))
        .andThen(Commands.deadline(
            Commands.waitSeconds(5.0).andThen(Commands.runOnce(() -> write("Waited 5s"))),
            Commands.runOnce(() -> write("Intake started at " + Constants.IntakeConstants.IntakeFeederSpeed))))
        .andThen(Commands.deadline(
            Commands.waitSeconds(1.5),
            Commands.runOnce(() -> write("Launcher stopped")),
            Commands.runOnce(() -> write("Intake stopped")))
            .andThen(() -> write("Sequence completed normally")))
        .handleInterrupt(() -> write("sequence interrupted"));

    return launchSequence.withTimeout(10); // No matter what, finish the sequence after X seconds

  }

}

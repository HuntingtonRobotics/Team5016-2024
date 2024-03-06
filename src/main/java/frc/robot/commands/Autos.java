// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.SwerveDriveContainer;
import frc.robot.Constants.LauncherConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;

public final class Autos {

  /* Original example from template; note the command structure */
    // return new RunCommand(() -> drivetrain.arcadeDrive(-.5, 0))
    //     .withTimeout(1)
    //     .andThen(new RunCommand(() -> drivetrain.arcadeDrive(0, 0)));

  private static SwerveRequest.FieldCentricFacingAngle swerveReq = new SwerveRequest.FieldCentricFacingAngle();

  public static Command driveAndTurn(SwerveDriveContainer swerve) {
    swerveReq
      .withTargetDirection(Rotation2d.fromDegrees(180))
      .withVelocityX(swerve.MaxSpeed)
      .withVelocityY(0);

    return new RunCommand(() -> swerve.drivetrain.applyRequest(() -> swerveReq)
      .withTimeout(3.0)
    );
  }

  public static Command driveTurnShoot(SwerveDriveContainer swerve, Launcher launchSub, Intake intakeSub) {
    swerveReq
      .withTargetDirection(Rotation2d.fromDegrees(180))
      .withVelocityX(swerve.MaxSpeed)
      .withVelocityY(0);
    
    return new RunCommand(() -> swerve.drivetrain.applyRequest(() -> swerveReq)
      .withTimeout(3.0)
      .andThen(
        Commands.run(() -> launchSub.setMotorSpeed(0.8))
          .withTimeout(LauncherConstants.kLauncherDelay)
          .andThen(intakeSub.getIntakeCommand())
          .handleInterrupt(() -> launchSub.stop())
      )
    );
  }
}

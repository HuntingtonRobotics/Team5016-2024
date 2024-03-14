// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
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
  // .withTimeout(1)
  // .andThen(new RunCommand(() -> drivetrain.arcadeDrive(0, 0)));

  public static Command driveForward(SwerveDriveContainer swerve) {
    return swerve.drivetrain.applyRequest(() -> 
        swerve.drive.withVelocityY(swerve.MaxSpeed)
                      //.withVelocityY(-m_driverController.getLeftY() * swerve.MaxSpeed) // Drive left with negative X (left)
                      //.withRotationalRate(-m_driverController.getRightX() * swerve.MaxAngularRate) // Drive counterclockwise with negative X (left))
    )
        .withTimeout(1.0);
  }

  public static Command driveAndTurn(SwerveDriveContainer swerve) {
    var cmd = new SwerveRequest.FieldCentricFacingAngle()
        .withTargetDirection(Rotation2d.fromDegrees(180))
        .withVelocityX(swerve.MaxSpeed);

    return new RunCommand(() -> swerve.drivetrain.applyRequest(() -> cmd)
        .withTimeout(3.0));
  }

  public static Command autoDriveXCommand(SwerveDriveContainer swerve) {
    if (DriverStation.getAlliance().isPresent()) {
      var req = new SwerveRequest.FieldCentric().withVelocityX(
          0.3);

      return new RunCommand(() -> swerve.drivetrain.setControl(req)).withTimeout(3.0);
    } else {
      return null;
    }
  }

  public static Command autoDriveYCommand(SwerveDriveContainer swerve) {
    if (DriverStation.getAlliance().isPresent()) {
      var req = new SwerveRequest.FieldCentric().withVelocityY(
          DriverStation.getAlliance().get() == DriverStation.Alliance.Blue ? 0.2 : -0.2);

      return new RunCommand(() -> swerve.drivetrain.setControl(req)).withTimeout(2.0);
    } else {
      return null;
    }
  }

  public static Command driveTurnShoot(SwerveDriveContainer swerve, Launcher launchSub, Intake intakeSub) {
    var swerveCmd = new SwerveRequest.FieldCentricFacingAngle()
        .withTargetDirection(Rotation2d.fromDegrees(180))
        .withVelocityX(swerve.MaxSpeed);

    return new RunCommand(() -> swerve.drivetrain.applyRequest(() -> swerveCmd)
        .withTimeout(3.0)
        .andThen(
            Commands.run(() -> launchSub.setMotorSpeed(0.8))
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(intakeSub.getIntakeCommand())
                .handleInterrupt(() -> launchSub.stop())));
  }
}

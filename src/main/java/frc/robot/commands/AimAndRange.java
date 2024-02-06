package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.LimelightHelpers;

public class AimAndRange {
  static String LimelightCameraName = "limelight";
  // constants for fine-tuning movement
  static double kpAim = -0.1d;
  static double kpDistance = -0.1d;
  static double min_aim_command = 0.05d;

  public static Command getCommand(CANDrivetrain drivetrain) {
    double tx = LimelightHelpers.getTX(LimelightCameraName);
    double ty = LimelightHelpers.getTY(LimelightCameraName);

    double heading_error = -tx;
    double distance_error = -ty;
    double steering_adjust = 0.0d;

    if (tx > 1.0) {
      steering_adjust = kpAim * heading_error - min_aim_command;
    } else if (tx < -1.0) {
      steering_adjust = kpAim * heading_error + min_aim_command;
    }

    double distance_adjust = kpDistance * distance_error;

    double speed = steering_adjust + distance_adjust;
    double rotation = -1 * (steering_adjust + distance_adjust);

    return new RunCommand(() -> drivetrain.arcadeDrive(speed, rotation), drivetrain);
  }
}

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.SwerveDriveContainer;
import frc.robot.subsystems.LimelightHelpers;

public class AimAndRange {
  static String LimelightCameraName = "limelight";
  static String Limelight_BlueAmp = "AP7_BlueAmp";
  static String Limelight_RedAmp = "AP4_RedAmp";

  // constants for fine-tuning movement
  static double kpAim = -0.1d;
  static double kpDistance = -0.1d;
  static double min_aim_command = 0.05d;

  public static Command getCommand(SwerveDriveContainer swerve, Alliance alliance) {
    String pipeline = "";
    if (alliance == Alliance.Blue)
    {
      pipeline = Limelight_BlueAmp;
    }
    else
    {
      pipeline = Limelight_RedAmp;
    }

    double tx = LimelightHelpers.getTX(pipeline);
    double ty = LimelightHelpers.getTY(pipeline);

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

    // Override movement
    return new RunCommand(() -> swerve.drivetrain.applyRequest(() ->
    swerve.drive.withVelocityX(speed)
                .withVelocityY(speed)
                .withRotationalRate(rotation)), swerve.drivetrain);
  }
}

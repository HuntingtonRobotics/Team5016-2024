package frc.robot;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.TunerConstants;

public class SwerveDriveContainer {
    private double MaxSpeed = 6; // 6 meters per second desired top speed
    private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

    public final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
        .withDeadband(MaxSpeed * 0.12).withRotationalDeadband(MaxAngularRate * 0.12) // Add a 10% deadband
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
    public final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    public final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    public final Telemetry logger = new Telemetry(MaxSpeed);
}

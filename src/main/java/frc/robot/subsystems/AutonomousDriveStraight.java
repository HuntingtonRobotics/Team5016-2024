package frc.robot.subsystems;

import frc.robot.SwerveDriveContainer;

// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.Constants.DrivetrainConstants;

// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
 import frc.robot.subsystems.CommandSwerveDrivetrain;
 import java.util.function.Supplier;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class AutonomousDriveStraight extends Command{
    private double speed;
    //private final SwerveDriveContainer m_driveTrain;

// public AutonomousDriveStraight(SwerveDriveContainer runCodeRun, double speed) {
//         m_driveTrain = runCodeRun;
//         this.speed = speed;

//         var swerveCmd = new SwerveRequest.FieldCentricFacingAngle()
//       .withTargetDirection(Rotation2d.fromDegrees(180))
//       .withVelocityX(speed);
//       m_driveTrain.drivetrain.applyRequest(swerveCmd);
    
//     return new RunCommand(() -> swerve.drivetrain.applyRequest(() -> swerveCmd)
//       .withTimeout(3.0));
    
}



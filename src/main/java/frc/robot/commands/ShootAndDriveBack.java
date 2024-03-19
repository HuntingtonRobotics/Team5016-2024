package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.SwerveDriveContainer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;

public class ShootAndDriveBack extends SequentialCommandGroup{

    // Launcher m_launcher
    // SwerveDriveContainer m_drive;
    // public ShootAndDriveBack(Launcher launcher, Intake intake, SwerveDriveContainer swerve){
    //     addCommands(
    //         new InstantCommand(()-> swerve.drivetrain.getPigeon2().setYaw(-120)),
    //         Commands.runOnce(() -> launcher.setMotorSpeed(0.8)),
    //         new WaitCommand(1.0),
    //         intake.setIntakeSpeed(Constants.IntakeConstants.IntakeFeederSpeed),
    //         new WaitCommand(1.5),
    //         Autos.autoDriveXCommand(swerve),
    //         Autos.autoDriveYCommand(swerve)
    //     );
    // }

}

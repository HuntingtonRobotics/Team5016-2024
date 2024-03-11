// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class DriveStraight extends Command {
  CommandSwerveDrivetrain mSwerve;
  SwerveRequest.FieldCentric mRequest;
  double m_startTime;
  /** Creates a new DriveStriaight. */
  public DriveStraight(CommandSwerveDrivetrain mSwerve) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.mSwerve = mSwerve;
    mRequest = new SwerveRequest.FieldCentric();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_startTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mSwerve.setControl(mRequest.withVelocityX(.2).withRotationalRate(0).withVelocityY(0));
  }
  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mSwerve.setControl(mRequest.withVelocityX(0).withRotationalRate(0).withVelocityY(0));
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp() - m_startTime >= 2;
  }
}

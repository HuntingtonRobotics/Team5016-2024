package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LauncherArmConstants;

public class LauncherArm extends SubsystemBase {

  private CANSparkMax m_left;
  private CANSparkMax m_right;

  public LauncherArm() {

    m_left = new CANSparkMax(LauncherArmConstants.kLeftLifterID, MotorType.kBrushless);
    m_right = new CANSparkMax(LauncherArmConstants.kRightLifterID, MotorType.kBrushless);

    m_left.follow(m_right);

    m_left.setSmartCurrentLimit(LauncherArmConstants.CurrentLimit);
    m_right.setSmartCurrentLimit(LauncherArmConstants.CurrentLimit);
  }

  public Command getLauncherUpCommand() {
    return this.startEnd(
        () -> {
          m_left.set(1.0);
          m_right.set(-1.0);
        },
        () -> {
          m_left.set(0);
          m_right.set(0);
        });
  }

  public Command getLauncherDownCommand() {
    return this.startEnd(
        () -> {
          m_left.set(-1.0);
          m_right.set(1.0);
        },
        () -> {
          m_left.set(0);
          m_right.set(0);
        });
  }
}

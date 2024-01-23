package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LifterConstants;

public class CANLifter extends SubsystemBase {

  WPI_TalonSRX m_lifter_left;
  WPI_TalonSRX m_lifter_right;

  public CANLifter() {

    m_lifter_left = new WPI_TalonSRX(LifterConstants.kLeftLifterID);
    m_lifter_right = new WPI_TalonSRX(LifterConstants.kRightLifterID);
  }

  public Command getLifterUpCommand() {
    return this.startEnd(
        () -> {
          m_lifter_left.set(1.0);
          m_lifter_right.set(0.2);
        },
        () -> {
          m_lifter_left.set(0);
          m_lifter_right.set(0);
        });
  }

  public Command getLifterDownCommand() {
    return this.startEnd(
        () -> {
          m_lifter_left.set(-1.0);
          m_lifter_right.set(-0.2);
        },
        () -> {
          m_lifter_left.set(0);
          m_lifter_right.set(0);
        });
  }
}

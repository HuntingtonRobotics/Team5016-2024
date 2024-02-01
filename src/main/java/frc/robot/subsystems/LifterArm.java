package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LifterConstants;

public class LifterArm extends SubsystemBase {

  WPI_TalonSRX m_lifter_left;
  WPI_TalonSRX m_lifter_right;

  public LifterArm() {

    m_lifter_left = new WPI_TalonSRX(LifterConstants.kLeftLifterID);
    m_lifter_right = new WPI_TalonSRX(LifterConstants.kRightLifterID);

    m_lifter_left.follow(m_lifter_right);
    // m_feedWheel_upper.follow(m_feedWheel_lower);
  }

  // private final DigitalInput limit = new DigitalInput(0);

  // public Command stopLifter() {
  //   return this.startEnd(
  //       () -> {
  //         if (limit.get() == false) {
  //           System.out.println("this is working");
  //           try {
  //             Thread.sleep(50);
  //           } catch (InterruptedException e) {
  //             // TODO Auto-generated catch block
  //             // m_lifter_left.set(0);
  //             // m_lifter_right.set(0);
  //           }
  //         }
  //       },
  //       () -> {
  //         m_lifter_left.set(1.0);
  //         m_lifter_right.set(-1.0);
  //       });
  // }

  public Command getLifterUpCommand() {
    return this.startEnd(
        () -> {
          m_lifter_left.set(1.0);
          m_lifter_right.set(-1.0);
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
          m_lifter_right.set(1.0);
        },
        () -> {
          m_lifter_left.set(0);
          m_lifter_right.set(0);
        });
  }
}

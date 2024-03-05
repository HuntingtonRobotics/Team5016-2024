package frc.robot.subsystems;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


/* This code is to test PID-drive movement of the Launcher Arm */
public class PIDArm extends SubsystemBase  {
    
    private CANSparkMax m_rightLead;
    private CANSparkMax m_leftFollow;
    
    //new stuff
    private SparkPIDController m_rightPIDController;
    private SparkPIDController m_leftPIDController;
    private RelativeEncoder m_rightEncoder;
    private RelativeEncoder m_leftEncoder;



    public PIDArm() {
      
        m_rightLead = new CANSparkMax(30, MotorType.kBrushless);
        m_leftFollow = new CANSparkMax(31, MotorType.kBrushless);

//test stuff

// set the PID coefficients for the right arm motor
m_rightPIDController = m_rightLead.getPIDController();
m_rightPIDController.setP(0.01);
m_rightPIDController.setI(0);
m_rightPIDController.setD(0);

// set the PID coefficients for the left arm motor
m_leftPIDController = m_leftFollow.getPIDController();
m_leftPIDController.setP(0.01);
m_leftPIDController.setI(0);
m_leftPIDController.setD(0);

// set the encoders for the right and left arm motors
m_rightEncoder = m_rightLead.getEncoder();
m_leftEncoder = m_leftFollow.getEncoder();

// reset the encoder positions
m_rightEncoder.setPosition(0);
m_leftEncoder.setPosition(0);


      }
    

  public Command getLauncherUpCommand() {
    return this.startEnd(
        () -> {
          m_leftFollow.set(.9);
          m_rightLead.set(-.9);
        },
        () -> {
          m_leftFollow.set(0);
          m_rightLead.set(0);
        });
  }

  public Command getLauncherDownCommand() {
    return this.startEnd(
        () -> {
          m_leftFollow.set(-.9);
          m_rightLead.set(.9);
        },
        () -> {
          m_leftFollow.set(0);
          m_rightLead.set(0);
        });
  }





  public void setAngle(double angle) {
    double rightSpeed = (angle - m_rightEncoder.getPosition()) / 50.0;
    double leftSpeed = (angle - m_leftEncoder.getPosition()) / 50.0;

    m_rightPIDController.setReference(angle, CANSparkMax.ControlType.kPosition);
    m_leftPIDController.setReference(angle, CANSparkMax.ControlType.kPosition);

    m_rightLead.set(rightSpeed);
    m_leftFollow.set(leftSpeed);

    SmartDashboard.putNumber("Right encoder position", m_rightEncoder.getPosition());
    SmartDashboard.putNumber("Left encoder position", m_leftEncoder.getPosition());
}











    
}

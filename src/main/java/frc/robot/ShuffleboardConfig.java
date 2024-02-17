package frc.robot;

import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class ShuffleboardConfig {
    public void Setup(UsbCamera primaryCamera) {

        Shuffleboard.getTab("DriverDashboard")
                    .add(primaryCamera);
    }
}

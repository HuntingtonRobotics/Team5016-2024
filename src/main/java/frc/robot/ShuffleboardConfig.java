package frc.robot;

import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.Constants.LauncherConstants;

public class ShuffleboardConfig {

    private GenericEntry LauncherDelay;
    public double getLauncherDelay() {
        return LauncherDelay.getDouble(LauncherConstants.kLauncherDelay);
    }

    public void Setup(UsbCamera primaryCamera) {

        var tab = Shuffleboard.getTab("DriverDashboard");

        tab.add(primaryCamera);
        LauncherDelay = tab.add("Launcher Delay", 1.0)
           .getEntry();

        
    }
}

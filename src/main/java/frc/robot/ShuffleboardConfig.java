package frc.robot;

import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.Constants.LauncherConstants;

public class ShuffleboardConfig {

    // private GenericEntry LauncherDelay;
    // public double getLauncherDelay() {
    //     if  (LauncherDelay.exists()) {
    //         return LauncherDelay.getDouble(LauncherConstants.kLauncherDelay);
    //     }
    //     else
    //     {
    //         return 1.0;
    //     }
    // }

    public void Setup(UsbCamera primaryCamera) {

        var tab = Shuffleboard.getTab("Teleoperated");

        tab.add(primaryCamera);
        // LauncherDelay = tab.add("Launcher Delay", 1.0)
        //    .getEntry();

        
    }
}

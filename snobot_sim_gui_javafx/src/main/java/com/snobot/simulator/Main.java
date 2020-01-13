package com.snobot.simulator;

import com.sun.javafx.application.LauncherImpl;

public final class Main
{
    private Main()
    {

    }

    public static void main(String[] aArgs)
    {
        DefaultDataAccessorFactory.initalize();

        // JavaFX 11+ uses GTK3 by default, and has problems on some display
        // servers
        // This flag forces JavaFX to use GTK2
        // System.setProperty("jdk.gtk.version", "2");
        LauncherImpl.launchApplication(SimulatorApplication.class, SimulatorPreloader.class, aArgs);
    }
}

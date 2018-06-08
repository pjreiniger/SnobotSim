package com.snobot.simulator.jni;

import com.snobot.simulator.JniLibraryResourceLoader;

import edu.wpi.first.wpiutil.RuntimeDetector;

public abstract class BaseSimulatorJni // NOPMD
{
    static
    {
        initilaize();
    }

    public static void initilaize()
    {
        if (!RuntimeDetector.isWindows())
        {
            JniLibraryResourceLoader.loadLibrary("uv");
        }
        JniLibraryResourceLoader.loadLibrary("wpiutil");
        JniLibraryResourceLoader.loadLibrary("wpiHal");
        JniLibraryResourceLoader.loadLibrary("halsim_adx_gyro_accelerometer");
        JniLibraryResourceLoader.loadLibrary("navx_simulator");
        JniLibraryResourceLoader.loadLibrary("snobotSimCpp");
        JniLibraryResourceLoader.loadLibrary("snobotSimJni");
        SnobotSimulatorJni.initializeSimulator();
    }
}

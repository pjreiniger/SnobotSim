package com.snobot.simulator.jni;

import com.snobot.simulator.JniLibraryResourceLoader;

public abstract class BaseSimulatorJni // NOPMD
{
    static
    {
        initilaize();
    }

    public static void initilaize()
    {
        JniLibraryResourceLoader.loadLibrary("wpiutil");
        JniLibraryResourceLoader.loadLibrary("wpiHal");
        JniLibraryResourceLoader.loadLibrary("halsim_adx_gyro_accelerometer");
        JniLibraryResourceLoader.loadLibrary("navx_simulator");
        JniLibraryResourceLoader.loadLibrary("snobotSimCpp");
        JniLibraryResourceLoader.loadLibrary("snobotSimJni");
        SnobotSimulatorJni.initializeSimulator();
    }
}

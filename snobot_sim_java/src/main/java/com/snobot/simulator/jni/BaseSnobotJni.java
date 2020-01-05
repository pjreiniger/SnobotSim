package com.snobot.simulator.jni;

import com.snobot.simulator.JniLibraryResourceLoader;

import edu.wpi.first.hal.HAL;

public class BaseSnobotJni
{
    static
    {
        JniLibraryResourceLoader.loadLibrary("wpiutil");
        JniLibraryResourceLoader.loadLibrary("wpiHal");
        JniLibraryResourceLoader.loadLibrary("navx_simulator_jni");
        JniLibraryResourceLoader.loadLibrary("CTRE_PhoenixCCI");
        JniLibraryResourceLoader.loadLibrary("SparkMaxDriver");

        HAL.initialize(500, 0);
    }
}

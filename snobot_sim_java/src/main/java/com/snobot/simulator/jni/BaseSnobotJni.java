package com.snobot.simulator.jni;

import com.snobot.simulator.JniLibraryResourceLoader;

import edu.wpi.first.wpilibj.hal.HAL;

public class BaseSnobotJni
{
    static
    {
        JniLibraryResourceLoader.loadLibrary("wpiutil");
        JniLibraryResourceLoader.loadLibrary("wpiHal");
        JniLibraryResourceLoader.loadLibrary("halsim_adx_gyro_accelerometer");
        JniLibraryResourceLoader.loadLibrary("navx_simulator_base");
        JniLibraryResourceLoader.loadLibrary("navx_simulator");
        JniLibraryResourceLoader.loadLibrary("adx_family");
        JniLibraryResourceLoader.loadLibrary("CTRE_PhoenixCCI");

        HAL.initialize(500, 0);
    }
}

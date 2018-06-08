package com.snobot.simulator.jni;

import com.snobot.simulator.JniLibraryResourceLoader;

import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpiutil.RuntimeDetector;

public class BaseSnobotJni
{
    static
    {
        if (!RuntimeDetector.isWindows())
        {
            JniLibraryResourceLoader.loadLibrary("uv");
        }

        JniLibraryResourceLoader.loadLibrary("wpiutil");
        JniLibraryResourceLoader.loadLibrary("wpiHal");
        JniLibraryResourceLoader.loadLibrary("halsim_adx_gyro_accelerometer");
        // JniLibraryResourceLoader.loadLibrary("navx_simulator");
        // JniLibraryResourceLoader.loadLibrary("navx_simulator_jni");
        JniLibraryResourceLoader.loadLibrary("adx_family_jni");
        JniLibraryResourceLoader.loadLibrary("CTRE_PhoenixCCI");

        HAL.initialize(500, 0);
        RegisterCallbacksJni.reset();
    }
}

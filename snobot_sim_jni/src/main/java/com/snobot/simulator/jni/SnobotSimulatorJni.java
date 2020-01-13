
package com.snobot.simulator.jni;

public final class SnobotSimulatorJni extends BaseSimulatorJni
{
    private SnobotSimulatorJni()
    {

    }

    public static native void initializeSimulator();

    public static native void reset();

    public static native void shutdown();

    public static native String getVersion();

    public static native void initializeLogging(int aLogLevel);

    public static native boolean loadConfigFile(String aConfigFile);

    public static native boolean saveConfigFile(String aConfigFile);
}

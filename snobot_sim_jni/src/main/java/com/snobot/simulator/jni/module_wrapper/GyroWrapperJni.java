
package com.snobot.simulator.jni.module_wrapper;

import com.snobot.simulator.jni.BaseSimulatorJni;

public final class GyroWrapperJni extends BaseSimulatorJni
{
    private GyroWrapperJni()
    {

    }

    public static native boolean isInitialized(int aPort);

    public static native void setName(int aPort, String aName);

    public static native String getName(int aPort);

    public static native boolean getWantsHidden(int aPort);

    public static native boolean createSimulator(int aPort, String aType);

    public static native void removeSimluator(int aPort);

    public static native double getAngle(int aPort);

    public static native void setAngle(int aPort, double aAngle);

    public static native void reset(int aPort);

    public static native int[] getPortList();
}

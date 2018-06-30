
package com.snobot.simulator.jni.module_wrapper;

import com.snobot.simulator.jni.BaseSimulatorJni;

public final class DigitalSourceWrapperJni extends BaseSimulatorJni
{
    private DigitalSourceWrapperJni()
    {

    }

    public static native boolean isInitialized(int aPort);

    public static native void setName(int aPort, String aName);

    public static native String getName(int aPort);

    public static native boolean getWantsHidden(int aPort);

    public static native boolean createSimulator(int aPort, String aType);

    public static native void removeSimluator(int aPort);

    public static native boolean getState(int aPort);

    public static native void setState(int aPort, boolean aValue);

    public static native int[] getPortList();
}

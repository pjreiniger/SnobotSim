
package com.snobot.simulator.jni.module_wrapper;

import com.snobot.simulator.jni.BaseSimulatorJni;

public final class SolenoidWrapperJni extends BaseSimulatorJni
{
    private SolenoidWrapperJni()
    {

    }

    public static native void setName(int aPort, String aName);

    public static native String getName(int aPort);

    public static native boolean getWantsHidden(int aPort);

    public static native boolean get(int aPort);

    public static native int[] getPortList();
}

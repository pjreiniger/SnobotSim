
package com.snobot.simulator.jni.module_wrapper;

import com.snobot.simulator.jni.BaseSimulatorJni;

public final class AccelerometerWrapperJni extends BaseSimulatorJni
{
    private AccelerometerWrapperJni()
    {

    }

    public static native void setName(int aPort, String aName);

    public static native String getName(int aPort);

    public static native boolean getWantsHidden(int aPort);

    public static native double getAcceleration(int aPort);

    public static native void setAcceleration(int aPort, double aAcceleration);

    public static native int[] getPortList();

}

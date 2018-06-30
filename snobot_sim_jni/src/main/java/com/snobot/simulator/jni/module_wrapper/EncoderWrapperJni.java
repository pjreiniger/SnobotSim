
package com.snobot.simulator.jni.module_wrapper;

import com.snobot.simulator.jni.BaseSimulatorJni;

public final class EncoderWrapperJni extends BaseSimulatorJni
{
    private EncoderWrapperJni()
    {

    }

    public static native int getHandle(int aPortA, int aPortB);

    public static native void setName(int aPort, String aName);

    public static native String getName(int aPort);

    public static native boolean getWantsHidden(int aPort);

    public static native boolean connectSpeedController(int aEncoderHandle, int aSpeedControllerHandle);

    public static native boolean isHookedUp(int aPort);

    public static native int getHookedUpId(int aPort);

    public static native void setDistancePerTick(int aPort, double aDistancePerTick);

    public static native double getDistancePerTick(int aPort);

    public static native double getRaw(int aPort);

    public static native double getDistance(int aPort);

    public static native int[] getPortList();
}

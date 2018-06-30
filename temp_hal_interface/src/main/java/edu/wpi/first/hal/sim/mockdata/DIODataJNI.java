package edu.wpi.first.hal.sim.mockdata;

import edu.wpi.first.wpilibj.sim.NotifyCallback;

public class DIODataJNI
{
    public static native void resetData(int aPort);

    public static native void registerInitializedCallback(int aPort, NotifyCallback aCallback, boolean aInitialNotify);

    public static native boolean getValue(int aPort);

    public static native void setValue(int aPort, boolean aValue);

}

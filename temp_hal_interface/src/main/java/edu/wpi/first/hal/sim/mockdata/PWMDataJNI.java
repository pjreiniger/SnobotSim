package edu.wpi.first.hal.sim.mockdata;

import edu.wpi.first.wpilibj.sim.NotifyCallback;

public class PWMDataJNI
{

    public static native void resetData(int aPort);

    public static native void registerInitializedCallback(int aPort, NotifyCallback aCallback, boolean aInitialNotify);

    public static native void registerSpeedCallback(int aPort, NotifyCallback aCallback, boolean aInitialNotify);

}

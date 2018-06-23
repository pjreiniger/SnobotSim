package edu.wpi.first.hal.sim.mockdata;

import edu.wpi.first.wpilibj.sim.NotifyCallback;

public class RelayDataJNI
{

    public static native void resetData(int aPort);

    public static native void registerInitializedForwardCallback(int aPort, NotifyCallback aCallback, boolean aInitialNotify);

    public static native boolean getForward(int m_index);

    public static native void setForward(int m_index, boolean forward);

    public static native boolean getReverse(int m_index);

    public static native void setReverse(int m_index, boolean reverse);

}

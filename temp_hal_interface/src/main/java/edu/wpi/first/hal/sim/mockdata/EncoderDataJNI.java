package edu.wpi.first.hal.sim.mockdata;

import edu.wpi.first.wpilibj.sim.NotifyCallback;

public class EncoderDataJNI
{

    public static native void resetData(int aPort);

    public static native void registerInitializedCallback(int aPort, NotifyCallback aCallback, boolean aInitialNotify);

    public static native void registerResetCallback(int aPort, NotifyCallback aCallback, boolean aInitialNotify);

    public static native int getCount(int m_index);

    public static native void setCount(int m_index, int count);

    public static native double getPeriod(int m_index);

    public static native void setPeriod(int m_index, double period);

    public static native void setDistancePerPulse(int m_index, double distancePerPulse);

    public static native double getDistancePerPulse(int m_index);

    public static native boolean getReset(int index);

    public static native void setReset(int index, boolean reset);

}

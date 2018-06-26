package edu.wpi.first.hal.sim.mockdata;

import edu.wpi.first.wpilibj.sim.BufferCallback;
import edu.wpi.first.wpilibj.sim.ConstBufferCallback;
import edu.wpi.first.wpilibj.sim.NotifyCallback;

public class SPIDataJNI
{
    public static native void resetData(int aPort);

    public static native void registerInitializedCallback(int aPort, NotifyCallback aCallback, boolean aInitialNotify);

    public static native void registerReadCallback(int aPort, BufferCallback aCallback);

    public static native void registerWriteCallback(int aPort, ConstBufferCallback aCallback);
}

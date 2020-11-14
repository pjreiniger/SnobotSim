package com.snobot.simulator.jni.standard_components;

import edu.wpi.first.hal.simulation.NotifyCallback;

public abstract class PortBasedNotifyCallback implements NotifyCallback
{
    protected final int mPort;

    public PortBasedNotifyCallback(int aIndex)
    {
        mPort = aIndex;
    }
}

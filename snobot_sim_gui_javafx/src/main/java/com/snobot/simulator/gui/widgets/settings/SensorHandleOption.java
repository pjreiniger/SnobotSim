package com.snobot.simulator.gui.widgets.settings;

public class SensorHandleOption
{
    public int mHandle;
    public String mName;

    public SensorHandleOption(int aHandle, String aName)
    {
        mHandle = aHandle;
        mName = aName;
    }

    @Override
    public String toString()
    {
        return mName + "(" + mHandle + ")";
    }
}

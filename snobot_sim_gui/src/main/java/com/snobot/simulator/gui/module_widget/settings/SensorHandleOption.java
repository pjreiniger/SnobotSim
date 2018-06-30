package com.snobot.simulator.gui.module_widget.settings;

public class SensorHandleOption
{
    int mHandle;
    String mName;

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

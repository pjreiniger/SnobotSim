package com.snobot.simulator.module_wrapper.wpi;

import com.snobot.simulator.module_wrapper.ASensorWrapper;
import com.snobot.simulator.module_wrapper.interfaces.ISolenoidWrapper;

public class WpiSolenoidWrapper extends ASensorWrapper implements ISolenoidWrapper
{
    private boolean mState;

    public WpiSolenoidWrapper(int aIndex)
    {
        super("Solenoid " + aIndex);
        mState = false;
    }

    @Override
    public boolean get()
    {
        return mState;
    }

    @Override
    public void set(boolean aState)
    {
        mState = aState;
    }
}

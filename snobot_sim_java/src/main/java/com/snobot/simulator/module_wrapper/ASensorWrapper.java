package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.module_wrapper.interfaces.ISensorWrapper;

public class ASensorWrapper implements ISensorWrapper
{
    protected String mName;
    protected boolean mIsInitialized;
    protected boolean mWantsHidden;

    public ASensorWrapper(String aName)
    {
        mName = aName;
    }

    @Override
    public boolean isInitialized()
    {
        return mIsInitialized;
    }

    @Override
    public void setInitialized(boolean aInitialized)
    {
        mIsInitialized = aInitialized;
    }

    @Override
    public String getName()
    {
        return mName;
    }

    @Override
    public void setName(String aName)
    {
        mName = aName;
    }

    @Override
    public boolean getWantsHidden()
    {
        return mWantsHidden;
    }

    @Override
    public void setWantsHidden(boolean aVisible)
    {
        mWantsHidden = aVisible;
    }

    @Override
    public void close() throws Exception
    {
        // Nothing to do
    }

    @Override
    public String getType()
    {
        return getClass().getName();
    }
}

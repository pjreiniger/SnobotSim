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

    /* (non-Javadoc)
     * @see com.snobot.simulator.module_wrapper.ISensorWrapper#getName()
     */
    @Override
    public String getName()
    {
        return mName;
    }

    /* (non-Javadoc)
     * @see com.snobot.simulator.module_wrapper.ISensorWrapper#setName(java.lang.String)
     */
    @Override
    public void setName(String aName)
    {
        mName = aName;
    }

    /* (non-Javadoc)
     * @see com.snobot.simulator.module_wrapper.ISensorWrapper#getWantsHidden()
     */
    @Override
    public boolean getWantsHidden()
    {
        return mWantsHidden;
    }

    /* (non-Javadoc)
     * @see com.snobot.simulator.module_wrapper.ISensorWrapper#setWantsHidden(boolean)
     */
    @Override
    public void setWantsHidden(boolean aVisible)
    {
        mWantsHidden = aVisible;
    }

    @Override
    public void close()
    {
        // Nothing to do
    }
}

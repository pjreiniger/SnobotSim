package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.RelayWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IRelayWrapper;

public class RelayWrapper implements IRelayWrapper
{
    private final int mHandle;

    public RelayWrapper(int aPort, String aType)
    {
        mHandle = aPort;
        RelayWrapperJni.createSimulator(aPort, aType);
    }

    public RelayWrapper(int aHandle)
    {
        mHandle = aHandle;
    }

    @Override
    public boolean isInitialized()
    {
        return RelayWrapperJni.isInitialized(mHandle);
    }

    @Override
    public void setInitialized(boolean aInitialized)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close()
    {
        RelayWrapperJni.removeSimluator(mHandle);
    }

    @Override
    public void setName(String aName)
    {
        RelayWrapperJni.setName(mHandle, aName);
    }

    @Override
    public String getName()
    {
        return RelayWrapperJni.getName(mHandle);
    }

    @Override
    public boolean getWantsHidden()
    {
        return RelayWrapperJni.getWantsHidden(mHandle);
    }

    @Override
    public boolean getRelayForwards()
    {
        return RelayWrapperJni.getFowardValue(mHandle);
    }

    @Override
    public void setRelayReverse(boolean aReverse)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRelayForwards(boolean aForwards)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getRelayReverse()
    {
        return RelayWrapperJni.getReverseValue(mHandle);
    }

    @Override
    public void setWantsHidden(boolean aVisible)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getType()
    {
        return null;
    }

}

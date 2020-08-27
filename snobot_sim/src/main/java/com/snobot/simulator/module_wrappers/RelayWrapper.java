package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.RelayWrapperJni;

public class RelayWrapper
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

    public boolean isInitialized()
    {
        return RelayWrapperJni.isInitialized(mHandle);
    }

    public void removeSimulator()
    {
        RelayWrapperJni.removeSimluator(mHandle);
    }

    public void setName(String aName)
    {
        RelayWrapperJni.setName(mHandle, aName);
    }

    public String getName()
    {
        return RelayWrapperJni.getName(mHandle);
    }

    public boolean getWantsHidden()
    {
        return RelayWrapperJni.getWantsHidden(mHandle);
    }

    public boolean getFowardValue()
    {
        return RelayWrapperJni.getFowardValue(mHandle);
    }

    public boolean getReverseValue()
    {
        return RelayWrapperJni.getReverseValue(mHandle);
    }

    public void removeSimluator()
    {
        RelayWrapperJni.removeSimluator(mHandle);
    }
}

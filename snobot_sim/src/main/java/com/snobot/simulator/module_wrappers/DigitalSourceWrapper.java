package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.DigitalSourceWrapperJni;

public class DigitalSourceWrapper
{
    private final int mHandle;

    public DigitalSourceWrapper(int aPort, String aType)
    {
        mHandle = aPort;
        DigitalSourceWrapperJni.createSimulator(aPort, aType);
    }

    public DigitalSourceWrapper(int aPort)
    {
        mHandle = aPort;
    }

    public boolean isInitialized()
    {
        return DigitalSourceWrapperJni.isInitialized(mHandle);
    }

    public void removeSimulator()
    {
        DigitalSourceWrapperJni.removeSimluator(mHandle);
    }

    public void setName(String aName)
    {
        DigitalSourceWrapperJni.setName(mHandle, aName);
    }

    public String getName()
    {
        return DigitalSourceWrapperJni.getName(mHandle);
    }

    public boolean getWantsHidden()
    {
        return DigitalSourceWrapperJni.getWantsHidden(mHandle);
    }

    public boolean getState()
    {
        return DigitalSourceWrapperJni.getState(mHandle);
    }

    public void setState(boolean aValue)
    {
        DigitalSourceWrapperJni.setState(mHandle, aValue);
    }

    public void removeSimluator(int aPort)
    {
        DigitalSourceWrapperJni.removeSimluator(aPort);
    }
}

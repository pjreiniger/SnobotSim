package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.SolenoidWrapperJni;

public class SolenoidWrapper
{
    private final int mHandle;

    public SolenoidWrapper(int aPort, String aType)
    {
        mHandle = aPort;
        SolenoidWrapperJni.createSimulator(aPort, aType);
    }

    public SolenoidWrapper(int aPort)
    {
        mHandle = aPort;
    }

    public boolean isInitialized()
    {
        return SolenoidWrapperJni.isInitialized(mHandle);
    }

    public void removeSimulator()
    {
        SolenoidWrapperJni.removeSimluator(mHandle);
    }

    public void setName(String aName)
    {
        SolenoidWrapperJni.setName(mHandle, aName);
    }

    public String getName()
    {
        return SolenoidWrapperJni.getName(mHandle);
    }

    public boolean getWantsHidden()
    {
        return SolenoidWrapperJni.getWantsHidden(mHandle);
    }

    public boolean get()
    {
        return SolenoidWrapperJni.get(mHandle);
    }

    public void removeSimluator(int aPort)
    {
        SolenoidWrapperJni.removeSimluator(aPort);
    }
}

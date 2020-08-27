package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.AnalogInWrapperJni;

public class AnalogInWrapper
{
    private final int mHandle;

    public AnalogInWrapper(int aPort, String aType)
    {
        mHandle = aPort;
        AnalogInWrapperJni.createSimulator(aPort, aType);
    }

    public AnalogInWrapper(int aHandle)
    {
        mHandle = aHandle;
    }

    public boolean isInitialized()
    {
        return AnalogInWrapperJni.isInitialized(mHandle);
    }

    public void removeSimulator()
    {
        AnalogInWrapperJni.removeSimluator(mHandle);
    }

    public void setName(String aName)
    {
        AnalogInWrapperJni.setName(mHandle, aName);
    }

    public String getName()
    {
        return AnalogInWrapperJni.getName(mHandle);
    }

    public boolean getWantsHidden()
    {
        return AnalogInWrapperJni.getWantsHidden(mHandle);
    }

    public double getVoltage()
    {
        return AnalogInWrapperJni.getVoltage(mHandle);
    }

    public void removeSimluator(int aPort)
    {
        AnalogInWrapperJni.removeSimluator(aPort);
    }
}

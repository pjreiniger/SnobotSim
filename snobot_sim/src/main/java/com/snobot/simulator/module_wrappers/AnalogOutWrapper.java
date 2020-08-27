package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.AnalogInWrapperJni;
import com.snobot.simulator.jni.module_wrapper.AnalogOutWrapperJni;

public class AnalogOutWrapper
{
    private final int mHandle;

    public AnalogOutWrapper(int aPort, String aType)
    {
        mHandle = aPort;
        AnalogOutWrapperJni.createSimulator(aPort, aType);
    }

    public AnalogOutWrapper(int aHandle)
    {
        mHandle = aHandle;
    }

    public boolean isInitialized()
    {
        return AnalogOutWrapperJni.isInitialized(mHandle);
    }

    public void removeSimulator()
    {
        AnalogOutWrapperJni.removeSimluator(mHandle);
    }

    public void setName(String aName)
    {
        AnalogOutWrapperJni.setName(mHandle, aName);
    }

    public String getName()
    {
        return AnalogOutWrapperJni.getName(mHandle);
    }

    public boolean getWantsHidden()
    {
        return AnalogOutWrapperJni.getWantsHidden(mHandle);
    }

    public double getVoltage()
    {
        return AnalogOutWrapperJni.getVoltage(mHandle);
    }

    public void removeSimluator()
    {
        AnalogInWrapperJni.removeSimluator(mHandle);
    }
}

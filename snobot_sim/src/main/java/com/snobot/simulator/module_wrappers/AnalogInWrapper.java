package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.AnalogInWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogInWrapper;

public class AnalogInWrapper implements IAnalogInWrapper
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

    @Override
    public void setInitialized(boolean aInitialized)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isInitialized()
    {
        return AnalogInWrapperJni.isInitialized(mHandle);
    }

    @Override
    public void close()
    {
        AnalogInWrapperJni.removeSimluator(mHandle);
    }

    @Override
    public void setName(String aName)
    {
        AnalogInWrapperJni.setName(mHandle, aName);
    }

    @Override
    public String getName()
    {
        return AnalogInWrapperJni.getName(mHandle);
    }

    @Override
    public boolean getWantsHidden()
    {
        return AnalogInWrapperJni.getWantsHidden(mHandle);
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

    @Override
    public void setVoltage(double aVoltage)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getVoltage()
    {
        return AnalogInWrapperJni.getVoltage(mHandle);
    }
}

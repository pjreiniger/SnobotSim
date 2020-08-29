package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.AnalogInWrapperJni;
import com.snobot.simulator.jni.module_wrapper.AnalogOutWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogOutWrapper;

public class AnalogOutWrapper implements IAnalogOutWrapper
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

    @Override
    public boolean isInitialized()
    {
        return AnalogOutWrapperJni.isInitialized(mHandle);
    }

    @Override
    public void setInitialized(boolean aInitialized)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setName(String aName)
    {
        AnalogOutWrapperJni.setName(mHandle, aName);
    }

    @Override
    public String getName()
    {
        return AnalogOutWrapperJni.getName(mHandle);
    }

    @Override
    public boolean getWantsHidden()
    {
        return AnalogOutWrapperJni.getWantsHidden(mHandle);
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
        return AnalogOutWrapperJni.getVoltage(mHandle);
    }

    @Override
    public void close()
    {
        AnalogInWrapperJni.removeSimluator(mHandle);
    }
}

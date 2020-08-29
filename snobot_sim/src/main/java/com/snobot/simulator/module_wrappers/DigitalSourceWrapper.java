package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.DigitalSourceWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IDigitalIoWrapper;

public class DigitalSourceWrapper implements IDigitalIoWrapper
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

    @Override
    public boolean isInitialized()
    {
        return DigitalSourceWrapperJni.isInitialized(mHandle);
    }

    @Override
    public void setInitialized(boolean aInitialized)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close()
    {
        DigitalSourceWrapperJni.removeSimluator(mHandle);
    }

    @Override
    public void setName(String aName)
    {
        DigitalSourceWrapperJni.setName(mHandle, aName);
    }

    @Override
    public String getName()
    {
        return DigitalSourceWrapperJni.getName(mHandle);
    }

    @Override
    public boolean getWantsHidden()
    {
        return DigitalSourceWrapperJni.getWantsHidden(mHandle);
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
    public boolean get()
    {
        return DigitalSourceWrapperJni.getState(mHandle);
    }

    @Override
    public void set(boolean aValue)
    {
        DigitalSourceWrapperJni.setState(mHandle, aValue);
    }
}

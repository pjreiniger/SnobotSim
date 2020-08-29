package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.SolenoidWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.ISolenoidWrapper;

public class SolenoidWrapper implements ISolenoidWrapper
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

    @Override
    public boolean isInitialized()
    {
        return SolenoidWrapperJni.isInitialized(mHandle);
    }

    @Override
    public void setInitialized(boolean aInitialized)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close()
    {
        SolenoidWrapperJni.removeSimluator(mHandle);
    }

    @Override
    public void setName(String aName)
    {
        SolenoidWrapperJni.setName(mHandle, aName);
    }

    @Override
    public String getName()
    {
        return SolenoidWrapperJni.getName(mHandle);
    }

    @Override
    public boolean getWantsHidden()
    {
        return SolenoidWrapperJni.getWantsHidden(mHandle);
    }

    @Override
    public void set(boolean aState)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean get()
    {
        return SolenoidWrapperJni.get(mHandle);
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

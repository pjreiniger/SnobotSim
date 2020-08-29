package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.GyroWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;

public class GyroWrapper implements IGyroWrapper
{
    private final int mHandle;

    public GyroWrapper(int aPort, String aType)
    {
        mHandle = aPort;
        GyroWrapperJni.createSimulator(aPort, aType);
    }

    public GyroWrapper(int aHandle)
    {
        mHandle = aHandle;
    }

    @Override
    public boolean isInitialized()
    {
        return GyroWrapperJni.isInitialized(mHandle);
    }

    @Override
    public void setInitialized(boolean aInitialized)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close()
    {
        GyroWrapperJni.removeSimluator(mHandle);
    }

    @Override
    public void setName(String aName)
    {
        GyroWrapperJni.setName(mHandle, aName);
    }

    @Override
    public String getName()
    {
        return GyroWrapperJni.getName(mHandle);
    }

    @Override
    public boolean getWantsHidden()
    {
        return GyroWrapperJni.getWantsHidden(mHandle);
    }

    @Override
    public double getAngle()
    {
        return GyroWrapperJni.getAngle(mHandle);
    }

    @Override
    public void setAngle(double aAngle)
    {
        GyroWrapperJni.setAngle(mHandle, aAngle);
    }

    public void reset()
    {
        GyroWrapperJni.reset(mHandle);
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

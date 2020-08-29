package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.AccelerometerWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IAccelerometerWrapper;

public class AccelerometerWrapper implements IAccelerometerWrapper
{
    private final int mHandle;

    public AccelerometerWrapper(int aPort, String aType)
    {
        mHandle = aPort;
        AccelerometerWrapperJni.createSimulator(aPort, aType);
    }

    public AccelerometerWrapper(int aHandle)
    {
        mHandle = aHandle;
    }

    @Override
    public boolean isInitialized()
    {
        return AccelerometerWrapperJni.isInitialized(mHandle);
    }

    @Override
    public void setInitialized(boolean aInitialized)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close()
    {
        AccelerometerWrapperJni.removeSimluator(mHandle);
    }

    @Override
    public void setName(String aName)
    {
        AccelerometerWrapperJni.setName(mHandle, aName);
    }

    @Override
    public String getName()
    {
        return AccelerometerWrapperJni.getName(mHandle);
    }

    @Override
    public boolean getWantsHidden()
    {
        return AccelerometerWrapperJni.getWantsHidden(mHandle);
    }

    @Override
    public void setWantsHidden(boolean aVisible)
    {

    }

    @Override
    public String getType()
    {
        return null;
    }

    @Override
    public double getAcceleration()
    {
        return AccelerometerWrapperJni.getAcceleration(mHandle);
    }

    @Override
    public void setAcceleration(double aAcceleration)
    {
        AccelerometerWrapperJni.setAcceleration(mHandle, aAcceleration);
    }
}

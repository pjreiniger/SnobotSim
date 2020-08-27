package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.AccelerometerWrapperJni;

public class AccelerometerWrapper
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

    public boolean isInitialized()
    {
        return AccelerometerWrapperJni.isInitialized(mHandle);
    }


    public void removeSimulator()
    {
        AccelerometerWrapperJni.removeSimluator(mHandle);
    }

    public void setName(String aName)
    {
        AccelerometerWrapperJni.setName(mHandle, aName);
    }

    public String getName()
    {
        return AccelerometerWrapperJni.getName(mHandle);
    }

    public boolean getWantsHidden()
    {
        return AccelerometerWrapperJni.getWantsHidden(mHandle);
    }

    public double getAcceleration()
    {
        return AccelerometerWrapperJni.getAcceleration(mHandle);
    }

    public void setAcceleration(double aAcceleration)
    {
        AccelerometerWrapperJni.setAcceleration(mHandle, aAcceleration);
    }
}

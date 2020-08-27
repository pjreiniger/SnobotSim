package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.GyroWrapperJni;

public class GyroWrapper
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

    public boolean isInitialized()
    {
        return GyroWrapperJni.isInitialized(mHandle);
    }

    public void removeSimulator()
    {
        GyroWrapperJni.removeSimluator(mHandle);
    }

    public void setName(String aName)
    {
        GyroWrapperJni.setName(mHandle, aName);
    }

    public String getName()
    {
        return GyroWrapperJni.getName(mHandle);
    }

    public boolean getWantsHidden()
    {
        return GyroWrapperJni.getWantsHidden(mHandle);
    }

    public double getAngle()
    {
        return GyroWrapperJni.getAngle(mHandle);
    }

    public void setAngle(double aAngle)
    {
        GyroWrapperJni.setAngle(mHandle, aAngle);
    }

    public void reset()
    {
        GyroWrapperJni.reset(mHandle);
    }

    public void removeSimluator(int aPort)
    {
        GyroWrapperJni.removeSimluator(aPort);
    }
}

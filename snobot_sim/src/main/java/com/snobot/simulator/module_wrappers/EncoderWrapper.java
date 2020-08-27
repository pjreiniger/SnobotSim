package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.EncoderWrapperJni;

public class EncoderWrapper
{
    private final int mHandle;

    public EncoderWrapper(int aPort, String aType)
    {
        mHandle = aPort;
        EncoderWrapperJni.createSimulator(aPort, aType);
    }

    public EncoderWrapper(int aPort)
    {
        mHandle = aPort;
    }

    public boolean isInitialized()
    {
        return EncoderWrapperJni.isInitialized(mHandle);
    }

    public void removeSimulator()
    {
        EncoderWrapperJni.removeSimluator(mHandle);
    }

    public void setName(String aName)
    {
        EncoderWrapperJni.setName(mHandle, aName);
    }

    public String getName()
    {
        return EncoderWrapperJni.getName(mHandle);
    }

    public boolean getWantsHidden()
    {
        return EncoderWrapperJni.getWantsHidden(mHandle);
    }

    public boolean connectSpeedController(int aSpeedControllerHandle)
    {
        return EncoderWrapperJni.connectSpeedController(mHandle, aSpeedControllerHandle);
    }

    public boolean isHookedUp()
    {
        return EncoderWrapperJni.isHookedUp(mHandle);
    }

    public int getHookedUpId()
    {
        return EncoderWrapperJni.getHookedUpId(mHandle);
    }

    public double getDistance()
    {
        return EncoderWrapperJni.getDistance(mHandle);
    }

    public void removeSimluator(int aPort)
    {
        EncoderWrapperJni.removeSimluator(aPort);
    }
}

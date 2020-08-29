package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.EncoderWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IEncoderWrapper;

public class EncoderWrapper implements IEncoderWrapper
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

    @Override
    public boolean isInitialized()
    {
        return EncoderWrapperJni.isInitialized(mHandle);
    }

    @Override
    public void setInitialized(boolean aInitialized)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close()
    {
        EncoderWrapperJni.removeSimluator(mHandle);
    }

    @Override
    public void setName(String aName)
    {
        EncoderWrapperJni.setName(mHandle, aName);
    }

    @Override
    public String getName()
    {
        return EncoderWrapperJni.getName(mHandle);
    }

    @Override
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
    public void reset()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPosition(double aPosition)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setVelocity(double aVelocity)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getPosition()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getVelocity()
    {
        throw new UnsupportedOperationException();
    }
}

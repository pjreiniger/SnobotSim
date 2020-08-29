package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.module_wrapper.EncoderWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IEncoderWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;

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

    @Override
    public boolean connectSpeedController(int aSpeedControllerHandle)
    {
        return EncoderWrapperJni.connectSpeedController(mHandle, aSpeedControllerHandle);
    }

    @Override
    public boolean connectSpeedController(IPwmWrapper aSpeedController)
    {
        return connectSpeedController(aSpeedController.getHandle());
    }

    @Override
    public boolean isHookedUp()
    {
        return EncoderWrapperJni.isHookedUp(mHandle);
    }

    @Override
    public int getHookedUpId()
    {
        return EncoderWrapperJni.getHookedUpId(mHandle);
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
        return EncoderWrapperJni.getDistance(mHandle);
    }

    @Override
    public double getVelocity()
    {
        throw new UnsupportedOperationException();
    }
}

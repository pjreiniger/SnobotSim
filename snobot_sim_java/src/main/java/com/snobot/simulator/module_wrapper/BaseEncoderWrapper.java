package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.module_wrapper.interfaces.IEncoderWrapper;

public class BaseEncoderWrapper extends ASensorWrapper implements IEncoderWrapper
{
    protected double mPosition;
    protected double mVelocity;

    public BaseEncoderWrapper(String aName)
    {
        super(aName);
    }

    @Override
    public void setPosition(double aPosition)
    {
        mPosition = aPosition;
    }

    @Override
    public void setVelocity(double aVelocity)
    {
        mVelocity = aVelocity;
    }

    @Override
    public double getPosition()
    {
        return mPosition;
    }

    @Override
    public double getVelocity()
    {
        return mVelocity;
    }

    @Override
    public void reset()
    {
        // TODO Auto-generated method stub

    }

}

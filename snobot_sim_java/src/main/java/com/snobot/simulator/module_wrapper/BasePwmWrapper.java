package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IMotorSimulator;
import com.snobot.simulator.module_wrapper.interfaces.IMotorFeedbackSensor;

public abstract class BasePwmWrapper extends ASensorWrapper implements IPwmWrapper
{
    protected IMotorSimulator mMotorSimulator;
    protected IMotorFeedbackSensor mFeedbackSensor;

    protected final int mHandle;

    public BasePwmWrapper(int aHandle, String aName)
    {
        super(aName);

        mHandle = aHandle;

        mMotorSimulator = new IMotorSimulator.NullMotorSimulator();
        mFeedbackSensor = new IMotorFeedbackSensor.NullFeedbackSensor();
    }

    @Override
    public void setMotorSimulator(IMotorSimulator aSimulator)
    {
        mMotorSimulator = aSimulator;
    }

    @Override
    public IMotorSimulator getMotorSimulator()
    {
        return mMotorSimulator;
    }

    @Override
    public double getVoltagePercentage()
    {
        return mMotorSimulator.getVoltagePercentage();
    }

    @Override
    public void setVoltagePercentage(double aSpeed)
    {
        mMotorSimulator.setVoltagePercentage(aSpeed);
    }

    @Override
    public double getPosition()
    {
        return mMotorSimulator.getPosition();
    }

    @Override
    public double getVelocity()
    {
        return mMotorSimulator.getVelocity();
    }

    @Override
    public double getCurrent()
    {
        return mMotorSimulator.getCurrent();
    }

    @Override
    public double getAcceleration()
    {
        return mMotorSimulator.getAcceleration();
    }

    @Override
    public void reset()
    {
        mMotorSimulator.reset();
    }

    @Override
    public void reset(double aPosition, double aVelocity, double aCurrent)
    {
        mMotorSimulator.reset(aPosition, aVelocity, aCurrent);
    }

    @Override
    public void setFeedbackSensor(IMotorFeedbackSensor aFeedbackSensor)
    {
        mFeedbackSensor = aFeedbackSensor;
    }

    @Override
    public IMotorFeedbackSensor getFeedbackSensor()
    {
        return mFeedbackSensor;
    }

    @Override
    public void update(double aWaitTime)
    {
        mMotorSimulator.update(aWaitTime);
        mFeedbackSensor.setPosition(mMotorSimulator.getPosition());
        mFeedbackSensor.setVelocity(mMotorSimulator.getVelocity());
    }

    @Override
    public int getHandle()
    {
        return mHandle;
    }

}

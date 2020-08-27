package com.snobot.simulator.motor_sim;

import com.snobot.simulator.module_wrapper.interfaces.IMotorSimulator;

public class SimpleMotorSimulator implements IMotorSimulator
{
    private final SimpleMotorSimulationConfig mConfig;

    private double mVoltagePercent;
    private double mVelocity;
    private double mPosition;

    public SimpleMotorSimulator(SimpleMotorSimulationConfig aConfig)
    {
        mConfig = aConfig;
    }

    @Override
    public void setVoltagePercentage(double aSpeed)
    {
        mVoltagePercent = aSpeed;
    }

    @Override
    public double getVoltagePercentage()
    {
        return mVoltagePercent;
    }

    @Override
    public double getAcceleration()
    {
        return 0;
    }

    @Override
    public double getVelocity()
    {
        return mVelocity;
    }

    @Override
    public double getPosition()
    {
        return mPosition;
    }

    @Override
    public double getCurrent()
    {
        return 0;
    }

    @Override
    public void reset()
    {
        reset(0, 0, 0);
    }

    @Override
    public void reset(double aPosition, double aVelocity, double aCurrent)
    {
        mPosition = aPosition;
        mVelocity = aVelocity;
    }

    @Override
    public void update(double aUpdateTime)
    {
        mVelocity = mConfig.mMaxSpeed * mVoltagePercent;
        mPosition += mVelocity * aUpdateTime;
    }

    public SimpleMotorSimulationConfig getConfig()
    {
        return mConfig;
    }
}

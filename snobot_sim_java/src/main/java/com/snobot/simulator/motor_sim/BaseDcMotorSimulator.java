package com.snobot.simulator.motor_sim;

public abstract class BaseDcMotorSimulator implements IMotorSimulator
{
    protected final DcMotorModel mMotorModel;
    protected final double mConversionFactor;
    protected double mVoltagePercentage;

    public BaseDcMotorSimulator(DcMotorModel aMotorModel)
    {
        this(aMotorModel, 1);
    }

    public BaseDcMotorSimulator(DcMotorModel aMotorModel, double aConversionFactor)
    {
        mMotorModel = aMotorModel;
        mConversionFactor = aConversionFactor;
    }

    @Override
    public void setVoltagePercentage(double aSpeed)
    {
        mVoltagePercentage = aSpeed;
    }

    @Override
    public double getVoltagePercentage()
    {
        return mVoltagePercentage;
    }

    @Override
    public double getVelocity()
    {
        return mMotorModel.getVelocity() * mConversionFactor;
    }

    @Override
    public double getPosition()
    {
        return mMotorModel.getPosition() * mConversionFactor;
    }

    @Override
    public double getAcceleration()
    {
        return mMotorModel.getAcceleration() * mConversionFactor;
    }

    @Override
    public void reset()
    {
        reset(0, 0, 0);
    }

    @Override
    public void reset(double aPosition, double aVelocity, double aCurrent)
    {
        mMotorModel.reset(aPosition, aVelocity, aCurrent);
    }

    @Override
    public double getCurrent()
    {
        return mMotorModel.getCurrent();
    }

    public DcMotorModelConfig getMotorConfig()
    {
        return mMotorModel.getMotorConfig();
    }

}

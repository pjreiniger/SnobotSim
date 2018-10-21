package com.snobot.simulator.motor_sim;

public class GravityLoadDcMotorSim extends BaseDcMotorSimulator
{
    protected static final double sGRAVITY = 9.8;

    protected final GravityLoadMotorSimulationConfig mConfig;

    public GravityLoadDcMotorSim(DcMotorModel aModel, GravityLoadMotorSimulationConfig aConfig)
    {
        super(aModel);

        mConfig = aConfig;
    }


    @Override
    public void update(double aCycleTime)
    {
        double extraAcceleration = -sGRAVITY;

        mMotorModel.step(mVoltagePercentage * 12, mConfig.getLoad(), extraAcceleration, aCycleTime);
    }

    public GravityLoadMotorSimulationConfig getConfig()
    {
        return mConfig;
    }
}

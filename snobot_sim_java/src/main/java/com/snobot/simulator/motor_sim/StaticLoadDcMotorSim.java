package com.snobot.simulator.motor_sim;

public class StaticLoadDcMotorSim extends BaseDcMotorSimulator
{
    protected final StaticLoadMotorSimulationConfig mConfig;

    public StaticLoadDcMotorSim(DcMotorModel aModel, StaticLoadMotorSimulationConfig aConfig)
    {
        super(aModel, aConfig.mConversionFactor);

        mConfig = aConfig;
    }

    @Override
    public void update(double aCycleTime)
    {
        mMotorModel.step(mVoltagePercentage * 12, mConfig.mLoad, 0, aCycleTime);
    }

    public StaticLoadMotorSimulationConfig getConfig()
    {
        return mConfig;
    }

}

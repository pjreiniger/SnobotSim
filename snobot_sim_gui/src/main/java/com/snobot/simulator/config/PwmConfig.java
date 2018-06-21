package com.snobot.simulator.config;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.IMotorSimulatorConfig;

public class PwmConfig extends BasicModuleConfig
{
    private IMotorSimulatorConfig mMotorSimConfig;
    private DcMotorModelConfig.FactoryParams mMotorModelConfig;

    public PwmConfig()
    {
        this(-1, "Unset", null, null, null);
    }

    public PwmConfig(int aHandle, String aName, String aType, IMotorSimulatorConfig aMotorSimConfig,
            DcMotorModelConfig.FactoryParams aMotorModelConfig)
    {
        super(aHandle, aName, aType);

        mMotorSimConfig = aMotorSimConfig;
        mMotorModelConfig = aMotorModelConfig;
    }

    public IMotorSimulatorConfig getmMotorSimConfig()
    {
        return mMotorSimConfig;
    }

    public void setmMotorSimConfig(IMotorSimulatorConfig aMotorSimConfig)
    {
        this.mMotorSimConfig = aMotorSimConfig;
    }

    public DcMotorModelConfig.FactoryParams getmMotorModelConfig()
    {
        return mMotorModelConfig;
    }

    public void setmMotorModelConfig(DcMotorModelConfig.FactoryParams aMotorModelConfig)
    {
        this.mMotorModelConfig = aMotorModelConfig;
    }

}

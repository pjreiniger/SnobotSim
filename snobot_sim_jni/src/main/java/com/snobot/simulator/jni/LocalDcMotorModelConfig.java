package com.snobot.simulator.jni;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.DcMotorModelConfig.FactoryParams;
import com.snobot.simulator.motor_sim.DcMotorModelConfig.MotorParams;

public class LocalDcMotorModelConfig
{
    protected final DcMotorModelConfig mConfig;

    public LocalDcMotorModelConfig(
            String aMotorType,
            int aNumMotors,
            double aGearReduction,
            double aGearboxEfficiency,

            double aNominalVoltage,
            double aFreeSpeedRpm,
            double aFreeCurrent,
            double aStallTorque,
            double aStallCurrent,
            double aMotorInertia,
            boolean aInverted,
            boolean aHasBrake,

            double aKt, double aKv, double aResistance)
    {
        DcMotorModelConfig.FactoryParams factoryParams = new FactoryParams(aMotorType, aNumMotors, aGearReduction, aGearboxEfficiency, aInverted,
                aHasBrake);
        DcMotorModelConfig.MotorParams motorParams = new MotorParams(aNominalVoltage, aFreeSpeedRpm, aFreeCurrent, aStallTorque, aStallCurrent,
                aMotorInertia, aKt, aKv, aResistance);

        mConfig = new DcMotorModelConfig(factoryParams, motorParams);
    }

    public LocalDcMotorModelConfig(DcMotorModelConfig aMotorConfig)
    {
        mConfig = aMotorConfig;
    }

    public DcMotorModelConfig getConfig()
    {
        return mConfig;
    }

    @Override
    public String toString()
    {
        return "LocalDcMotorModelConfig [mConfig=" + mConfig + "]";
    }

}

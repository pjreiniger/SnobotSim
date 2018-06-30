package com.snobot.simulator.motor_sim;

public class StaticLoadMotorSimulationConfig implements IMotorSimulatorConfig
{
    public final double mLoad;
    public final double mConversionFactor;

    @SuppressWarnings("unused")
    private StaticLoadMotorSimulationConfig()
    {
        this(0, 0);
    }

    public StaticLoadMotorSimulationConfig(double aLoad)
    {
        this(aLoad, 1);
    }

    public StaticLoadMotorSimulationConfig(double aLoad, double aConversionFactor)
    {
        this.mLoad = aLoad;
        this.mConversionFactor = aConversionFactor;
    }
}

package com.snobot.simulator.motor_sim;

public class GravityLoadMotorSimulationConfig implements IMotorSimulatorConfig
{
    private double mLoad;

    @SuppressWarnings("unused")
    private GravityLoadMotorSimulationConfig()
    {
        this(0);
    }

    public GravityLoadMotorSimulationConfig(double aLoad)
    {
        mLoad = aLoad;
    }

    public double getLoad()
    {
        return mLoad;
    }

}

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

    public double getmLoad()
    {
        return mLoad;
    }

    public void setmLoad(double mLoad)
    {
        this.mLoad = mLoad;
    }

}

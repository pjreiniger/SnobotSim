package com.snobot.simulator.simulator_components;

import com.snobot.simulator.module_wrapper.ASensorWrapper;

public class CompressorWrapper extends ASensorWrapper
{
    private static final double MAX_PRESSURE = 120;

    protected double mAirPressure;
    protected double mChargeRate; // psi charge per control loop

    public CompressorWrapper()
    {
        super("Compressor");
        mAirPressure = 120;
        mChargeRate = .25;
    }

    public void setChargeRate(double aChargeRate)
    {
        mChargeRate = aChargeRate;
    }

    public void solenoidFired(double aPressure)
    {
        mAirPressure -= aPressure;
        if (mAirPressure < 0)
        {
            mAirPressure = 0;
        }
    }

    public void update()
    {
        mAirPressure += mChargeRate;
        if (mAirPressure > MAX_PRESSURE)
        {
            mAirPressure = MAX_PRESSURE;
        }
    }

    public double getAirPressure()
    {
        return mAirPressure;
    }
}

package com.snobot.simulator.simulator_components.gyro;

import com.snobot.simulator.module_wrapper.ASensorWrapper;

import edu.wpi.first.hal.sim.mockdata.AnalogGyroDataJNI;

public class AnalogGyroWrapper extends ASensorWrapper implements IGyroWrapper
{
    protected final int mPort;
    protected double mAngle;

    public AnalogGyroWrapper(int aPort, String aName)
    {
        super(aName);
        mPort = aPort;
    }

    public void setAngle(double aAngle)
    {
        boolean isUpdate = mAngle != aAngle;
        mAngle = aAngle;
        if (isUpdate)
        {
            AnalogGyroDataJNI.setAngle(mPort, aAngle);
        }
    }

    public double getAngle()
    {
        return mAngle;
    }
}

package com.snobot.simulator.module_wrapper.wpi;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.module_wrapper.ASensorWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;

import edu.wpi.first.hal.sim.AnalogGyroSim;
import edu.wpi.first.hal.sim.NotifyCallback;
import edu.wpi.first.hal.sim.SimValue;

public class WpiAnalogGyroWrapper extends ASensorWrapper implements IGyroWrapper, NotifyCallback
{
    protected final AnalogGyroSim mWpiSimulator;

    public WpiAnalogGyroWrapper(int aPort, String aName)
    {
        super(aName);
        mWpiSimulator = new AnalogGyroSim(aPort);
    }

    @Override
    public void callback(String aCallbackType, SimValue aHalValue)
    {
        LogManager.getLogger(WpiAnalogGyroWrapper.class).log(Level.WARN, "Callback " + aCallbackType + " not supported");
    }

    @Override
    public void setAngle(double aAngle)
    {
        mWpiSimulator.setAngle(aAngle);
    }

    @Override
    public double getAngle()
    {
        return mWpiSimulator.getAngle();
    }
}

package com.snobot.simulator.module_wrapper.wpi;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.module_wrapper.ASensorWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogOutWrapper;

import edu.wpi.first.hal.sim.AnalogOutSim;
import edu.wpi.first.hal.sim.NotifyCallback;
import edu.wpi.first.hal.sim.SimValue;

public class WpiAnalogOutWrapper extends ASensorWrapper implements IAnalogOutWrapper, NotifyCallback
{
    private final AnalogOutSim mWpiSimulator;

    public WpiAnalogOutWrapper(int aPort)
    {
        super("Analog Out " + aPort);
        mWpiSimulator = new AnalogOutSim(aPort);

        // mWpiSimulator.registerVoltageCallback(this, false);
    }

    @Override
    public void callback(String aCallbackType, SimValue aHalValue)
    {
        LogManager.getLogger(WpiAnalogOutWrapper.class).log(Level.WARN, "Callback " + aCallbackType + " not supported");
    }

    @Override
    public void setVoltage(double aVoltage)
    {
        mWpiSimulator.setVoltage(aVoltage);
    }

    @Override
    public double getVoltage()
    {
        return mWpiSimulator.getVoltage();
    }

}

package com.snobot.simulator.module_wrapper.wpi;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.module_wrapper.ASensorWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IRelayWrapper;

import edu.wpi.first.hal.sim.NotifyCallback;
import edu.wpi.first.hal.sim.RelaySim;
import edu.wpi.first.hal.sim.SimValue;

public class WpiRelayWrapper extends ASensorWrapper implements IRelayWrapper, NotifyCallback
{
    private final RelaySim mWpiSimulator;

    public WpiRelayWrapper(int aPort)
    {
        super("Relay " + aPort);

        mWpiSimulator = new RelaySim(aPort);
    }

    @Override
    public void callback(String aCallbackType, SimValue aHalValue)
    {
        LogManager.getLogger(WpiAnalogGyroWrapper.class).log(Level.WARN, "Callback " + aCallbackType + " not supported");
    }

    @Override
    public void setRelayForwards(boolean aForwards)
    {
        mWpiSimulator.setForward(aForwards);
    }

    @Override
    public void setRelayReverse(boolean aReverse)
    {
        mWpiSimulator.setReverse(aReverse);
    }

    @Override
    public boolean getRelayForwards()
    {
        return mWpiSimulator.getForward();
    }

    @Override
    public boolean getRelayReverse()
    {
        return mWpiSimulator.getReverse();
    }

}

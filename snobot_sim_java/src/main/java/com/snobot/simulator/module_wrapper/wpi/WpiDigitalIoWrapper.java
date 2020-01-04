package com.snobot.simulator.module_wrapper.wpi;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.module_wrapper.ASensorWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IDigitalIoWrapper;

import edu.wpi.first.hal.HALValue;
import edu.wpi.first.hal.sim.DIOSim;
import edu.wpi.first.hal.sim.NotifyCallback;

public class WpiDigitalIoWrapper extends ASensorWrapper implements IDigitalIoWrapper, NotifyCallback
{
    private final DIOSim mWpiSimulator;

    public WpiDigitalIoWrapper(int aIndex)
    {
        super("Digital IO " + aIndex);

        mWpiSimulator = new DIOSim(aIndex);

        // mWpiSimulator.registerValueCallback(this, false);
        // mWpiSimulator.registerPulseLengthCallback(this, false);
        // mWpiSimulator.registerIsInputCallback(this, false);
        // mWpiSimulator.registerFilterIndexCallback(this, false);
    }

    @Override
    public void callback(String aCallbackType, HALValue aHalValue)
    {
        LogManager.getLogger(WpiDigitalIoWrapper.class).log(Level.WARN, "Callback " + aCallbackType + " not supported");
    }

    @Override
    public boolean get()
    {
        return mWpiSimulator.getValue();
    }

    @Override
    public void set(boolean aState)
    {
        mWpiSimulator.setValue(aState);
    }
}

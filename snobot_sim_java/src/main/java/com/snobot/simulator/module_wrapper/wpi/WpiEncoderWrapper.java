package com.snobot.simulator.module_wrapper.wpi;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.module_wrapper.BaseEncoderWrapper;

import edu.wpi.first.hal.HALValue;
import edu.wpi.first.hal.sim.EncoderSim;
import edu.wpi.first.hal.sim.NotifyCallback;

public class WpiEncoderWrapper extends BaseEncoderWrapper implements NotifyCallback
{
    private final EncoderSim mWpiSimulator;
    private double mDistancePerPulse;

    public WpiEncoderWrapper(int aPort)
    {
        super("Encoder " + aPort);

        mDistancePerPulse = 1;
        mWpiSimulator = new EncoderSim(aPort);

        // mWpiSimulator.registerCountCallback(this, false);
        // mWpiSimulator.registerPeriodCallback(this, false);
        // mWpiSimulator.registerResetCallback(this, false);
        // mWpiSimulator.registerMaxPeriodCallback(this, false);
        // mWpiSimulator.registerDirectionCallback(this, false);
        // mWpiSimulator.registerReverseDirectionCallback(this, false);
        // mWpiSimulator.registerSamplesToAverageCallback(this, false);
    }

    @Override
    public void callback(String aCallbackType, HALValue aHalValue)
    {
        LogManager.getLogger(WpiEncoderWrapper.class).log(Level.WARN, "Callback " + aCallbackType + " not supported");
    }

    @Override
    public void setPosition(double aPosition)
    {
        super.setPosition(aPosition);
        mWpiSimulator.setCount((int) (aPosition / mDistancePerPulse));
    }

    @Override
    public void setVelocity(double aVelocity)
    {
        super.setVelocity(aVelocity);
        mWpiSimulator.setPeriod(1.0 / aVelocity);
    }

    @Override
    public void reset()
    {
        super.reset();
        mWpiSimulator.setReset(true);
        mWpiSimulator.setReset(false);
    }

    public void setDistancePerTick(double aDistancePerPulse)
    {
        mDistancePerPulse = aDistancePerPulse;
    }
}

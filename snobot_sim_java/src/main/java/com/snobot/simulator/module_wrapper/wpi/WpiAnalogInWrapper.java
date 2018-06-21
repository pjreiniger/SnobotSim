package com.snobot.simulator.module_wrapper.wpi;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.module_wrapper.ASensorWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogInWrapper;

import edu.wpi.first.wpilibj.sim.AnalogInSim;
import edu.wpi.first.wpilibj.sim.NotifyCallback;
import edu.wpi.first.wpilibj.sim.SimValue;

public class WpiAnalogInWrapper extends ASensorWrapper implements IAnalogInWrapper, NotifyCallback
{
    private final AnalogInSim mWpiSimulator;

    public WpiAnalogInWrapper(int aPort)
    {
        super("Analog In " + aPort);
        mWpiSimulator = new AnalogInSim(aPort);

        // mWpiSimulator.registerAverageBitsCallback(this, false);
        // mWpiSimulator.registerOversampleBitsCallback(this, false);
        // mWpiSimulator.registerVoltageCallback(this, false);
        // mWpiSimulator.registerAccumulatorInitializedCallback(this, false);
        // mWpiSimulator.registerAccumulatorValueCallback(this, false);
        // mWpiSimulator.registerAccumulatorCountCallback(this, false);
        // mWpiSimulator.registerAccumulatorCenterCallback(this, false);
        // mWpiSimulator.registerAccumulatorDeadbandCallback(this, false);
    }

    @Override
    public void callback(String aCallbackType, SimValue aHalValue)
    {
        LogManager.getLogger(WpiAnalogInWrapper.class).log(Level.WARN, "Callback " + aCallbackType + " not supported");
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

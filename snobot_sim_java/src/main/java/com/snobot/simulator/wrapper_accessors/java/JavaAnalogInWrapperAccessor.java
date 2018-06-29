package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultAnalogInWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogInWrapper;
import com.snobot.simulator.wrapper_accessors.AnalogSourceWrapperAccessor;

public class JavaAnalogInWrapperAccessor extends BaseWrapperAccessor<IAnalogInWrapper> implements AnalogSourceWrapperAccessor
{

    private final DefaultAnalogInWrapperFactory mFactory;

    public JavaAnalogInWrapperAccessor()
    {
        mFactory = new DefaultAnalogInWrapperFactory();
    }

    @Override
    public boolean isInitialized(int aPort)
    {
        return getValue(aPort).isInitialized();
    }

    @Override
    public void setInitialized(int aPort, boolean aInitialized)
    {
        getValue(aPort).setInitialized(aInitialized);
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return mFactory.create(aPort, aType);
    }

    @Override
    public void removeSimluator(int aPort)
    {
        try
        {
            getValue(aPort).close();
        }
        catch (Exception ex)
        {
            LogManager.getLogger().log(Level.WARN, "Could not close simulator", ex);
        }
        SensorActuatorRegistry.get().getAnalogIn().remove(aPort);
    }

    @Override
    protected Map<Integer, IAnalogInWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getAnalogIn();
    }

    @Override
    public double getVoltage(int aPort)
    {
        return getValue(aPort).getVoltage();
    }

    @Override
    public void setVoltage(int aPort, double aVoltage)
    {
        getValue(aPort).setVoltage(aVoltage);
    }
}

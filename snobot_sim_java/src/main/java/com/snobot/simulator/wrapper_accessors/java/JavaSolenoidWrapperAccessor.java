package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultSolenoidWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.ISolenoidWrapper;
import com.snobot.simulator.wrapper_accessors.SolenoidWrapperAccessor;

public class JavaSolenoidWrapperAccessor extends BaseWrapperAccessor<ISolenoidWrapper> implements SolenoidWrapperAccessor
{
    private final DefaultSolenoidWrapperFactory mFactory;

    public JavaSolenoidWrapperAccessor()
    {
        mFactory = new DefaultSolenoidWrapperFactory();
    }

    @Override
    public boolean isInitialized(int aPort)
    {
        return getValue(aPort).isInitialized();
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return mFactory.create(aPort, aType);
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    @Override
    public void removeSimulator(int aPort)
    {
        try
        {
            getValue(aPort).close();
        }
        catch (Exception ex)
        {
            LogManager.getLogger().log(Level.WARN, "Could not close simulator", ex);
        }
        SensorActuatorRegistry.get().getSolenoids().remove(aPort);
    }

    @Override
    protected Map<Integer, ISolenoidWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getSolenoids();
    }

    @Override
    public boolean get(int aPort)
    {
        return getValue(aPort).get();
    }
}

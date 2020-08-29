package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import com.snobot.simulator.wrapper_accessors.AnalogOutputWrapperAccessor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultAnalogOutWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogOutWrapper;

public class JavaAnalogOutWrapperAccessor extends BaseWrapperAccessor<IAnalogOutWrapper> implements AnalogOutputWrapperAccessor
{
    private final DefaultAnalogOutWrapperFactory mFactory;

    public JavaAnalogOutWrapperAccessor()
    {
        mFactory = new DefaultAnalogOutWrapperFactory();
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
            getWrapper(aPort).close();
        }
        catch (Exception ex)
        {
            LogManager.getLogger().log(Level.WARN, "Could not close simulator", ex);
        }
        SensorActuatorRegistry.get().getAnalogOut().remove(aPort);
    }

    @Override
    protected Map<Integer, IAnalogOutWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getAnalogOut();
    }
}

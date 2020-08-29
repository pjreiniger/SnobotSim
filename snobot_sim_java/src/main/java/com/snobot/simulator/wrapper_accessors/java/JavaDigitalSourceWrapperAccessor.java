package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultDigitalIoWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IDigitalIoWrapper;
import com.snobot.simulator.wrapper_accessors.DigitalSourceWrapperAccessor;

public class JavaDigitalSourceWrapperAccessor extends BaseWrapperAccessor<IDigitalIoWrapper> implements DigitalSourceWrapperAccessor
{
    private final DefaultDigitalIoWrapperFactory mFactory;

    public JavaDigitalSourceWrapperAccessor()
    {
        mFactory = new DefaultDigitalIoWrapperFactory();
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
        SensorActuatorRegistry.get().getDigitalSources().remove(aPort);
    }

    @Override
    protected Map<Integer, IDigitalIoWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getDigitalSources();
    }
}

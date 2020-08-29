package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultGyroWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import com.snobot.simulator.wrapper_accessors.GyroWrapperAccessor;

public class JavaGyroWrapperAccessor extends BaseWrapperAccessor<IGyroWrapper> implements GyroWrapperAccessor
{
    private final DefaultGyroWrapperFactory mFactory;

    public JavaGyroWrapperAccessor()
    {
        mFactory = new DefaultGyroWrapperFactory();
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
        SensorActuatorRegistry.get().getGyros().remove(aPort);
    }

    @Override
    protected Map<Integer, IGyroWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getGyros();
    }
}

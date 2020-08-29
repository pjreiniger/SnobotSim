package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultRelayWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IRelayWrapper;
import com.snobot.simulator.wrapper_accessors.RelayWrapperAccessor;

public class JavaRelayWrapperAccessor extends BaseWrapperAccessor<IRelayWrapper> implements RelayWrapperAccessor
{
    private final DefaultRelayWrapperFactory mFactory;

    public JavaRelayWrapperAccessor()
    {
        mFactory = new DefaultRelayWrapperFactory();
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
        SensorActuatorRegistry.get().getRelays().remove(aPort);
    }

    @Override
    protected Map<Integer, IRelayWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getRelays();
    }
}

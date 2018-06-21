package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

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
    public boolean createSimulator(int aPort, String aType, boolean aIsStartup)
    {
        return mFactory.create(aPort, aType, aIsStartup);
    }

    @Override
    protected Map<Integer, IRelayWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getRelays();
    }

    @Override
    public boolean getFowardValue(int aPort)
    {
        return getValue(aPort).getRelayForwards();
    }

    @Override
    public boolean getReverseValue(int aPort)
    {
        return getValue(aPort).getRelayReverse();
    }
}

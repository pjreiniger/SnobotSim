package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultRelayWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IRelayWrapper;
import com.snobot.simulator.wrapper_accessors.RelayWrapperAccessor;

public class JavaRelayWrapperAccessor extends BaseWrapperAccessor<IRelayWrapper> implements RelayWrapperAccessor
{
    public JavaRelayWrapperAccessor()
    {
        super(new DefaultRelayWrapperFactory());
    }

    @Override
    public Map<Integer, IRelayWrapper> getWrappers()
    {
        return SensorActuatorRegistry.get().getRelays();
    }
}

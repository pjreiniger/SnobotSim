package com.snobot.simulator.wrapper_accessors.java;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultAddressableWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IAddressableLedWrapper;
import com.snobot.simulator.wrapper_accessors.AddressableLedWrapperAccessor;

import java.util.Map;

public class JavaAddressableWrapperAccessor extends BaseWrapperAccessor<IAddressableLedWrapper> implements AddressableLedWrapperAccessor
{
    public JavaAddressableWrapperAccessor()
    {
        super(new DefaultAddressableWrapperFactory());
    }

    @Override
    public Map<Integer, IAddressableLedWrapper> getWrappers()
    {
        return SensorActuatorRegistry.get().getLeds();
    }
}

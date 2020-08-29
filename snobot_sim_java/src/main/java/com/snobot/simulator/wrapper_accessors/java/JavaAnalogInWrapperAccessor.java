package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import com.snobot.simulator.wrapper_accessors.AnalogInWrapperAccessor;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultAnalogInWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogInWrapper;

public class JavaAnalogInWrapperAccessor extends BaseWrapperAccessor<IAnalogInWrapper> implements AnalogInWrapperAccessor
{
    public JavaAnalogInWrapperAccessor()
    {
        super(new DefaultAnalogInWrapperFactory());
    }

    @Override
    protected Map<Integer, IAnalogInWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getAnalogIn();
    }
}

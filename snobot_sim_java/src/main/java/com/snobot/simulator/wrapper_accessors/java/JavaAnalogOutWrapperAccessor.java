package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import com.snobot.simulator.wrapper_accessors.AnalogOutputWrapperAccessor;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultAnalogOutWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogOutWrapper;

public class JavaAnalogOutWrapperAccessor extends BaseWrapperAccessor<IAnalogOutWrapper> implements AnalogOutputWrapperAccessor
{
    public JavaAnalogOutWrapperAccessor()
    {
        super(new DefaultAnalogOutWrapperFactory());
    }

    @Override
    public Map<Integer, IAnalogOutWrapper> getWrappers()
    {
        return SensorActuatorRegistry.get().getAnalogOut();
    }
}

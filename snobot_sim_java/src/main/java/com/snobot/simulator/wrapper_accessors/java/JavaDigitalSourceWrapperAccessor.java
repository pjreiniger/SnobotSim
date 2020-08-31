package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultDigitalIoWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IDigitalIoWrapper;
import com.snobot.simulator.wrapper_accessors.DigitalSourceWrapperAccessor;

public class JavaDigitalSourceWrapperAccessor extends BaseWrapperAccessor<IDigitalIoWrapper> implements DigitalSourceWrapperAccessor
{
    public JavaDigitalSourceWrapperAccessor()
    {
        super(new DefaultDigitalIoWrapperFactory());
    }

    @Override
    public Map<Integer, IDigitalIoWrapper> getWrappers()
    {
        return SensorActuatorRegistry.get().getDigitalSources();
    }
}

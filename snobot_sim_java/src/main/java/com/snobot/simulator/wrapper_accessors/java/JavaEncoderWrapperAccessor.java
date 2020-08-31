package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultEncoderWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IEncoderWrapper;
import com.snobot.simulator.wrapper_accessors.EncoderWrapperAccessor;

public class JavaEncoderWrapperAccessor extends BaseWrapperAccessor<IEncoderWrapper> implements EncoderWrapperAccessor
{
    public JavaEncoderWrapperAccessor()
    {
        super(new DefaultEncoderWrapperFactory());
    }

    @Override
    public Map<Integer, IEncoderWrapper> getWrappers()
    {
        return SensorActuatorRegistry.get().getEncoders();
    }
}

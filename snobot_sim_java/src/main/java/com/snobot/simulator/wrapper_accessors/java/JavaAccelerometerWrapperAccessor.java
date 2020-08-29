package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.interfaces.IAccelerometerWrapper;
import com.snobot.simulator.wrapper_accessors.AccelerometerWrapperAccessor;

public class JavaAccelerometerWrapperAccessor extends BaseWrapperAccessor<IAccelerometerWrapper> implements AccelerometerWrapperAccessor
{
    public JavaAccelerometerWrapperAccessor()
    {
        super(null);
    }

    @Override
    protected Map<Integer, IAccelerometerWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getAccelerometers();
    }
}

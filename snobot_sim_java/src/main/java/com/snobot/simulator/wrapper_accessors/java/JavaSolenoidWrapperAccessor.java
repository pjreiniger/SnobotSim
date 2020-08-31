package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultSolenoidWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.ISolenoidWrapper;
import com.snobot.simulator.wrapper_accessors.SolenoidWrapperAccessor;

public class JavaSolenoidWrapperAccessor extends BaseWrapperAccessor<ISolenoidWrapper> implements SolenoidWrapperAccessor
{
    public JavaSolenoidWrapperAccessor()
    {
        super(new DefaultSolenoidWrapperFactory());
    }

    @Override
    public Map<Integer, ISolenoidWrapper> getWrappers()
    {
        return SensorActuatorRegistry.get().getSolenoids();
    }
}

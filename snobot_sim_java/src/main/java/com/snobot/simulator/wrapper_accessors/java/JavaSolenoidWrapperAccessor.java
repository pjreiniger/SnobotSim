package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultSolenoidWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.ISolenoidWrapper;
import com.snobot.simulator.wrapper_accessors.SolenoidWrapperAccessor;

public class JavaSolenoidWrapperAccessor extends BaseWrapperAccessor<ISolenoidWrapper> implements SolenoidWrapperAccessor
{
    private final DefaultSolenoidWrapperFactory mFactory;

    public JavaSolenoidWrapperAccessor()
    {
        mFactory = new DefaultSolenoidWrapperFactory();
    }

    @Override
    public boolean isInitialized(int aPort)
    {
        return getValue(aPort).isInitialized();
    }

    @Override
    public void setInitialized(int aPort, boolean aInitialized)
    {
        getValue(aPort).setInitialized(aInitialized);
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return mFactory.create(aPort, aType);
    }

    @Override
    protected Map<Integer, ISolenoidWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getSolenoids();
    }

    @Override
    public boolean get(int aPort)
    {
        return getValue(aPort).get();
    }
}

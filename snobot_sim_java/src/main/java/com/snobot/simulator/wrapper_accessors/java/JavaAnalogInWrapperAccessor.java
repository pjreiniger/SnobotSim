package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultAnalogInWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogInWrapper;
import com.snobot.simulator.wrapper_accessors.AnalogSourceWrapperAccessor;

public class JavaAnalogInWrapperAccessor extends BaseWrapperAccessor<IAnalogInWrapper> implements AnalogSourceWrapperAccessor
{

    private final DefaultAnalogInWrapperFactory mFactory;

    public JavaAnalogInWrapperAccessor()
    {
        mFactory = new DefaultAnalogInWrapperFactory();
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
    protected Map<Integer, IAnalogInWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getAnalogIn();
    }

    @Override
    public double getVoltage(int aPort)
    {
        return getValue(aPort).getVoltage();
    }
}

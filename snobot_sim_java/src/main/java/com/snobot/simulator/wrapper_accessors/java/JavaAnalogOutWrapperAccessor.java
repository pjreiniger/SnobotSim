package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultAnalogOutWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogOutWrapper;
import com.snobot.simulator.wrapper_accessors.AnalogSourceWrapperAccessor;

public class JavaAnalogOutWrapperAccessor extends BaseWrapperAccessor<IAnalogOutWrapper> implements AnalogSourceWrapperAccessor
{
    private final DefaultAnalogOutWrapperFactory mFactory;

    public JavaAnalogOutWrapperAccessor()
    {
        mFactory = new DefaultAnalogOutWrapperFactory();
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
    protected Map<Integer, IAnalogOutWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getAnalogOut();
    }

    @Override
    public double getVoltage(int aPort)
    {
        return getValue(aPort).getVoltage();
    }
}

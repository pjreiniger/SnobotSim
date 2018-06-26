package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultDigitalIoWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IDigitalIoWrapper;
import com.snobot.simulator.wrapper_accessors.DigitalSourceWrapperAccessor;

public class JavaDigitalSourceWrapperAccessor extends BaseWrapperAccessor<IDigitalIoWrapper> implements DigitalSourceWrapperAccessor
{
    private final DefaultDigitalIoWrapperFactory mFactory;

    public JavaDigitalSourceWrapperAccessor()
    {
        mFactory = new DefaultDigitalIoWrapperFactory();
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
    protected Map<Integer, IDigitalIoWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getDigitalSources();
    }

    @Override
    public boolean getState(int aPort)
    {
        return getValue(aPort).get();
    }

    @Override
    public void setState(int aPort, boolean aValue)
    {
        getValue(aPort).set(aValue);
    }
}

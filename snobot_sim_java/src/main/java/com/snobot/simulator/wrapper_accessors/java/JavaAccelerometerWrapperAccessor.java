package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.interfaces.IAccelerometerWrapper;
import com.snobot.simulator.wrapper_accessors.AccelerometerWrapperAccessor;

public class JavaAccelerometerWrapperAccessor extends BaseWrapperAccessor<IAccelerometerWrapper> implements AccelerometerWrapperAccessor
{
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
    public void register(int aPort, String aName)
    {
        // Nothing to do
    }

    @Override
    public double getAcceleration(int aPort)
    {
        return getValue(aPort).getAcceleration();
    }

    @Override
    public void setAcceleration(int aPort, double aAcceleration)
    {
        getValue(aPort).setAcceleration(aAcceleration);
    }

    @Override
    protected Map<Integer, IAccelerometerWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getAccelerometers();
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return false;
    }

}

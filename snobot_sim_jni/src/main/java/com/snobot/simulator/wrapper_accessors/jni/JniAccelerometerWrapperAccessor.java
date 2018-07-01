
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.AccelerometerWrapperJni;
import com.snobot.simulator.wrapper_accessors.AccelerometerWrapperAccessor;

public class JniAccelerometerWrapperAccessor implements AccelerometerWrapperAccessor
{
    @Override
    public boolean isInitialized(int aPort)
    {
        return AccelerometerWrapperJni.isInitialized(aPort);
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return AccelerometerWrapperJni.createSimulator(aPort, aType);
    }

    @Override
    public void removeSimulator(int aPort)
    {
        AccelerometerWrapperJni.removeSimluator(aPort);
    }

    @Override
    public void setName(int aPort, String aName)
    {
        AccelerometerWrapperJni.setName(aPort, aName);
    }

    @Override
    public String getName(int aPort)
    {
        return AccelerometerWrapperJni.getName(aPort);
    }

    @Override
    public boolean getWantsHidden(int aPort)
    {
        return AccelerometerWrapperJni.getWantsHidden(aPort);
    }

    @Override
    public double getAcceleration(int aPort)
    {
        return AccelerometerWrapperJni.getAcceleration(aPort);
    }

    @Override
    public void setAcceleration(int aPort, double aAcceleration)
    {
        AccelerometerWrapperJni.setAcceleration(aPort, aAcceleration);
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(AccelerometerWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }

    @Override
    public String getType(int aPort)
    {
        return null;
    }
}

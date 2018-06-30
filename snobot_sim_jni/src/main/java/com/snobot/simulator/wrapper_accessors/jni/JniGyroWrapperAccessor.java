
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.GyroWrapperJni;
import com.snobot.simulator.wrapper_accessors.GyroWrapperAccessor;

public class JniGyroWrapperAccessor implements GyroWrapperAccessor
{
    @Override
    public boolean isInitialized(int aPort)
    {
        return true;
    }

    @Override
    public void setInitialized(int aPort, boolean aInitialized)
    {
        // Nothing to do
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return false;
    }

    @Override
    public void removeSimluator(int aPort)
    {
        // Nothing to do
    }

    @Override
    public void setName(int aPort, String aName)
    {
        GyroWrapperJni.setName(aPort, aName);
    }

    @Override
    public String getName(int aPort)
    {
        return GyroWrapperJni.getName(aPort);
    }

    @Override
    public boolean getWantsHidden(int aPort)
    {
        return GyroWrapperJni.getWantsHidden(aPort);
    }

    @Override
    public double getAngle(int aPort)
    {
        return GyroWrapperJni.getAngle(aPort);
    }

    @Override
    public void setAngle(int aPort, double aAngle)
    {
        GyroWrapperJni.setAngle(aPort, aAngle);
    }

    @Override
    public void reset(int aPort)
    {
        GyroWrapperJni.reset(aPort);
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(GyroWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }

    @Override
    public String getType(int aPort)
    {
        return null;
    }
}

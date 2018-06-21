
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.AnalogSourceWrapperJni;
import com.snobot.simulator.wrapper_accessors.AnalogSourceWrapperAccessor;

public class JniAnalogSourceWrapperAccessor implements AnalogSourceWrapperAccessor
{
    @Override
    public boolean createSimulator(int aPort, String aType, boolean aIsStartup)
    {
        return false;
    }

    @Override
    public void setName(int aPort, String aName)
    {
        AnalogSourceWrapperJni.setName(aPort, aName);
    }

    @Override
    public String getName(int aPort)
    {
        return AnalogSourceWrapperJni.getName(aPort);
    }

    @Override
    public boolean getWantsHidden(int aPort)
    {
        return AnalogSourceWrapperJni.getWantsHidden(aPort);
    }

    @Override
    public double getVoltage(int aPort)
    {
        return AnalogSourceWrapperJni.getVoltage(aPort);
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(AnalogSourceWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }

    @Override
    public String getType(int aPort)
    {
        return null;
    }
}

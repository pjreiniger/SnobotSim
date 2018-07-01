
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.AnalogInWrapperJni;
import com.snobot.simulator.wrapper_accessors.AnalogSourceWrapperAccessor;

public class JniAnalogInWrapperAccessor implements AnalogSourceWrapperAccessor
{
    @Override
    public boolean isInitialized(int aPort)
    {
        return AnalogInWrapperJni.isInitialized(aPort);
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return AnalogInWrapperJni.createSimulator(aPort, aType);
    }

    @Override
    public void removeSimulator(int aPort)
    {
        AnalogInWrapperJni.removeSimluator(aPort);
    }

    @Override
    public void setName(int aPort, String aName)
    {
        AnalogInWrapperJni.setName(aPort, aName);
    }

    @Override
    public String getName(int aPort)
    {
        return AnalogInWrapperJni.getName(aPort);
    }

    @Override
    public boolean getWantsHidden(int aPort)
    {
        return AnalogInWrapperJni.getWantsHidden(aPort);
    }

    @Override
    public double getVoltage(int aPort)
    {
        return AnalogInWrapperJni.getVoltage(aPort);
    }

    @Override
    public void setVoltage(int aPort, double aVoltage)
    {
        // nothing to do
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(AnalogInWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }

    @Override
    public String getType(int aPort)
    {
        return null;
    }
}

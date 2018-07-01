
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.AnalogOutWrapperJni;
import com.snobot.simulator.wrapper_accessors.AnalogSourceWrapperAccessor;

public class JniAnalogOutWrapperAccessor implements AnalogSourceWrapperAccessor
{
    @Override
    public boolean isInitialized(int aPort)
    {
        return AnalogOutWrapperJni.isInitialized(aPort);
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return AnalogOutWrapperJni.createSimulator(aPort, aType);
    }

    @Override
    public void removeSimulator(int aPort)
    {
        AnalogOutWrapperJni.removeSimluator(aPort);
    }

    @Override
    public void setName(int aPort, String aName)
    {
        AnalogOutWrapperJni.setName(aPort, aName);
    }

    @Override
    public String getName(int aPort)
    {
        return AnalogOutWrapperJni.getName(aPort);
    }

    @Override
    public boolean getWantsHidden(int aPort)
    {
        return AnalogOutWrapperJni.getWantsHidden(aPort);
    }

    @Override
    public double getVoltage(int aPort)
    {
        return AnalogOutWrapperJni.getVoltage(aPort);
    }

    @Override
    public void setVoltage(int aPort, double aVoltage)
    {
        // nothing to do
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(AnalogOutWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }

    @Override
    public String getType(int aPort)
    {
        return null;
    }
}

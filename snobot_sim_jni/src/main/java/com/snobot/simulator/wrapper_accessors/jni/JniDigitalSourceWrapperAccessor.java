
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.DigitalSourceWrapperJni;
import com.snobot.simulator.wrapper_accessors.DigitalSourceWrapperAccessor;

public class JniDigitalSourceWrapperAccessor implements DigitalSourceWrapperAccessor
{
    @Override
    public boolean createSimulator(int aPort, String aType, boolean aIsStartup)
    {
        return false;
    }

    @Override
    public void setName(int aPort, String aName)
    {
        DigitalSourceWrapperJni.setName(aPort, aName);
    }

    @Override
    public String getName(int aPort)
    {
        return DigitalSourceWrapperJni.getName(aPort);
    }

    @Override
    public boolean getWantsHidden(int aPort)
    {
        return DigitalSourceWrapperJni.getWantsHidden(aPort);
    }

    @Override
    public boolean getState(int aPort)
    {
        return DigitalSourceWrapperJni.getState(aPort);
    }

    @Override
    public void setState(int aPort, boolean aValue)
    {
        DigitalSourceWrapperJni.setState(aPort, aValue);
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(DigitalSourceWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }

    @Override
    public String getType(int aPort)
    {
        return null;
    }
}

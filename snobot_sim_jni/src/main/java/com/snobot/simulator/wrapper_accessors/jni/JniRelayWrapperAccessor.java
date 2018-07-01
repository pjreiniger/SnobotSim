
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.RelayWrapperJni;
import com.snobot.simulator.wrapper_accessors.RelayWrapperAccessor;

public class JniRelayWrapperAccessor implements RelayWrapperAccessor
{
    @Override
    public boolean isInitialized(int aPort)
    {
        return RelayWrapperJni.isInitialized(aPort);
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return RelayWrapperJni.createSimulator(aPort, aType);
    }

    @Override
    public void removeSimulator(int aPort)
    {
        RelayWrapperJni.removeSimluator(aPort);
    }

    @Override
    public void setName(int aPort, String aName)
    {
        RelayWrapperJni.setName(aPort, aName);
    }

    @Override
    public String getName(int aPort)
    {
        return RelayWrapperJni.getName(aPort);
    }

    @Override
    public boolean getWantsHidden(int aPort)
    {
        return RelayWrapperJni.getWantsHidden(aPort);
    }

    @Override
    public boolean getFowardValue(int aPort)
    {
        return RelayWrapperJni.getFowardValue(aPort);
    }

    @Override
    public boolean getReverseValue(int aPort)
    {
        return RelayWrapperJni.getReverseValue(aPort);
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(RelayWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }

    @Override
    public String getType(int aPort)
    {
        return null;
    }
}

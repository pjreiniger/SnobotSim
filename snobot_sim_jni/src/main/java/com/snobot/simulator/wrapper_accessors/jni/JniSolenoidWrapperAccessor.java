
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.SolenoidWrapperJni;
import com.snobot.simulator.wrapper_accessors.SolenoidWrapperAccessor;

public class JniSolenoidWrapperAccessor implements SolenoidWrapperAccessor
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
    public void setName(int aPort, String aName)
    {
        SolenoidWrapperJni.setName(aPort, aName);
    }

    @Override
    public String getName(int aPort)
    {
        return SolenoidWrapperJni.getName(aPort);
    }

    @Override
    public boolean getWantsHidden(int aPort)
    {
        return SolenoidWrapperJni.getWantsHidden(aPort);
    }

    @Override
    public boolean get(int aPort)
    {
        return SolenoidWrapperJni.get(aPort);
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(SolenoidWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }

    @Override
    public String getType(int aPort)
    {
        return null;
    }
}

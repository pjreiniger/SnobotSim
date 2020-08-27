
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.AnalogInWrapperJni;
import com.snobot.simulator.module_wrappers.AnalogInWrapper;
import com.snobot.simulator.wrapper_accessors.AnalogInWrapperAccessor;

public class JniAnalogInWrapperAccessor extends BaseWrapperAccessor<AnalogInWrapper> implements AnalogInWrapperAccessor
{
    @Override
    public boolean isInitialized(int aPort)
    {
        return getWrapper(aPort).isInitialized();
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return AnalogInWrapperJni.createSimulator(aPort, aType);
    }

    @Override
    public void removeSimulator(int aPort)
    {
        getWrapper(aPort).removeSimluator(aPort);
    }

    @Override
    public void setName(int aPort, String aName)
    {
        getWrapper(aPort).setName(aName);
    }

    @Override
    public String getName(int aPort)
    {
        return getWrapper(aPort).getName();
    }

    @Override
    public boolean getWantsHidden(int aPort)
    {
        return getWrapper(aPort).getWantsHidden();
    }

    @Override
    public double getVoltage(int aPort)
    {
        return getWrapper(aPort).getVoltage();
    }

    @Override
    public void setVoltage(int aPort, double aVoltage)
    {
        // nothing to do
    }

    @Override
    protected AnalogInWrapper createWrapperForExistingType(int aHandle)
    {
        return new AnalogInWrapper(aHandle);
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

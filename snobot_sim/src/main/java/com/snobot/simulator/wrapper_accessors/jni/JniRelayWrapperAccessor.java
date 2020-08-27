
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.RelayWrapperJni;
import com.snobot.simulator.module_wrappers.RelayWrapper;
import com.snobot.simulator.wrapper_accessors.RelayWrapperAccessor;

public class JniRelayWrapperAccessor extends BaseWrapperAccessor<RelayWrapper> implements RelayWrapperAccessor
{
    @Override
    public boolean isInitialized(int aPort)
    {
        return getWrapper(aPort).isInitialized();
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return RelayWrapperJni.createSimulator(aPort, aType);
    }

    @Override
    public void removeSimulator(int aPort)
    {
        getWrapper(aPort).removeSimluator();
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
    public boolean getFowardValue(int aPort)
    {
        return getWrapper(aPort).getFowardValue();
    }

    @Override
    public boolean getReverseValue(int aPort)
    {
        return getWrapper(aPort).getReverseValue();
    }

    @Override
    protected RelayWrapper createWrapperForExistingType(int aHandle)
    {
        return new RelayWrapper(aHandle);
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


package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.RelayWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IRelayWrapper;
import com.snobot.simulator.module_wrappers.RelayWrapper;
import com.snobot.simulator.wrapper_accessors.RelayWrapperAccessor;

public class JniRelayWrapperAccessor extends BaseWrapperAccessor<RelayWrapper> implements RelayWrapperAccessor
{
    @Override
    public IRelayWrapper createSimulator(int aPort, String aType)
    {
        RelayWrapper wrapper = new RelayWrapper(aPort, aType);
        register(aPort, wrapper);
        return wrapper;
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
}

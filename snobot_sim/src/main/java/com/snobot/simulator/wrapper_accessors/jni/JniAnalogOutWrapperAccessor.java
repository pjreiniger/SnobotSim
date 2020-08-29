
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.AnalogOutWrapperJni;
import com.snobot.simulator.module_wrappers.AnalogOutWrapper;
import com.snobot.simulator.wrapper_accessors.AnalogOutputWrapperAccessor;

public class JniAnalogOutWrapperAccessor extends BaseWrapperAccessor<AnalogOutWrapper> implements AnalogOutputWrapperAccessor
{
    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return AnalogOutWrapperJni.createSimulator(aPort, aType);
    }

    @Override
    protected AnalogOutWrapper createWrapperForExistingType(int aHandle)
    {
        return new AnalogOutWrapper(aHandle);
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(AnalogOutWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }
}

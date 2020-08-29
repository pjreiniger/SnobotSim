
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.DigitalSourceWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IDigitalIoWrapper;
import com.snobot.simulator.module_wrappers.DigitalSourceWrapper;
import com.snobot.simulator.wrapper_accessors.DigitalSourceWrapperAccessor;

public class JniDigitalSourceWrapperAccessor extends BaseWrapperAccessor<DigitalSourceWrapper> implements DigitalSourceWrapperAccessor
{
    @Override
    public IDigitalIoWrapper createSimulator(int aPort, String aType)
    {
        DigitalSourceWrapper wrapper = new DigitalSourceWrapper(aPort, aType);
        register(aPort, wrapper);
        return wrapper;
    }

    @Override
    protected DigitalSourceWrapper createWrapperForExistingType(int aHandle)
    {
        return new DigitalSourceWrapper(aHandle);
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(DigitalSourceWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }
}

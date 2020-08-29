
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.GyroWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import com.snobot.simulator.module_wrappers.GyroWrapper;
import com.snobot.simulator.wrapper_accessors.GyroWrapperAccessor;

public class JniGyroWrapperAccessor extends BaseWrapperAccessor<GyroWrapper> implements GyroWrapperAccessor
{
    @Override
    public IGyroWrapper createSimulator(int aPort, String aType)
    {
        GyroWrapper wrapper = new GyroWrapper(aPort, aType);
        register(aPort, wrapper);
        return wrapper;
    }

    @Override
    protected GyroWrapper createWrapperForExistingType(int aHandle)
    {
        return new GyroWrapper(aHandle);
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(GyroWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }
}


package com.snobot.simulator.wrapper_accessors.jni;

import com.snobot.simulator.jni.module_wrapper.AccelerometerWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IAccelerometerWrapper;
import com.snobot.simulator.module_wrappers.AccelerometerWrapper;
import com.snobot.simulator.wrapper_accessors.AccelerometerWrapperAccessor;

public class JniAccelerometerWrapperAccessor extends BaseWrapperAccessor<IAccelerometerWrapper> implements AccelerometerWrapperAccessor
{
    @Override
    public IAccelerometerWrapper createSimulator(int aPort, String aType)
    {
        AccelerometerWrapper wrapper = new AccelerometerWrapper(aPort, aType);
        register(aPort, wrapper);
        return wrapper;
    }

    @Override
    protected AccelerometerWrapper createWrapperForExistingType(int aHandle)
    {
        return new AccelerometerWrapper(aHandle);
    }

    @Override
    public int[] getPortList()
    {
        return AccelerometerWrapperJni.getPortList();
    }
}

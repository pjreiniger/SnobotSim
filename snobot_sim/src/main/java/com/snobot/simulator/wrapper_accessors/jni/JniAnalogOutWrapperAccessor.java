
package com.snobot.simulator.wrapper_accessors.jni;

import com.snobot.simulator.jni.module_wrapper.AnalogOutWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogOutWrapper;
import com.snobot.simulator.module_wrappers.AnalogOutWrapper;
import com.snobot.simulator.wrapper_accessors.AnalogOutputWrapperAccessor;

public class JniAnalogOutWrapperAccessor extends BaseWrapperAccessor<IAnalogOutWrapper> implements AnalogOutputWrapperAccessor
{
    @Override
    public IAnalogOutWrapper createSimulator(int aPort, String aType)
    {
        AnalogOutWrapper wrapper = new AnalogOutWrapper(aPort, aType);
        register(aPort, wrapper);
        return wrapper;
    }

    @Override
    protected AnalogOutWrapper createWrapperForExistingType(int aHandle)
    {
        return new AnalogOutWrapper(aHandle);
    }

    @Override
    public int[] getPortList()
    {
        return AnalogOutWrapperJni.getPortList();
    }
}


package com.snobot.simulator.wrapper_accessors.jni;

import com.snobot.simulator.jni.module_wrapper.AnalogInWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogInWrapper;
import com.snobot.simulator.module_wrappers.AnalogInWrapper;
import com.snobot.simulator.wrapper_accessors.AnalogInWrapperAccessor;

public class JniAnalogInWrapperAccessor extends BaseWrapperAccessor<IAnalogInWrapper> implements AnalogInWrapperAccessor
{
    @Override
    public IAnalogInWrapper createSimulator(int aPort, String aType)
    {
        AnalogInWrapper wrapper = new AnalogInWrapper(aPort, aType);
        register(aPort, wrapper);
        return wrapper;
    }

    @Override
    protected AnalogInWrapper createWrapperForExistingType(int aHandle)
    {
        return new AnalogInWrapper(aHandle);
    }

    @Override
    public int[] getPortList()
    {
        return AnalogInWrapperJni.getPortList();
    }
}

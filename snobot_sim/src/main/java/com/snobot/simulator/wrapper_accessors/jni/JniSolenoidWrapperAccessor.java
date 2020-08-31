
package com.snobot.simulator.wrapper_accessors.jni;

import com.snobot.simulator.jni.module_wrapper.SolenoidWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.ISolenoidWrapper;
import com.snobot.simulator.module_wrappers.SolenoidWrapper;
import com.snobot.simulator.wrapper_accessors.SolenoidWrapperAccessor;

public class JniSolenoidWrapperAccessor extends BaseWrapperAccessor<ISolenoidWrapper> implements SolenoidWrapperAccessor
{
    @Override
    public ISolenoidWrapper createSimulator(int aPort, String aType)
    {
        SolenoidWrapper wrapper = new SolenoidWrapper(aPort, aType);
        register(aPort, wrapper);
        return wrapper;
    }

    @Override
    protected SolenoidWrapper createWrapperForExistingType(int aHandle)
    {
        return new SolenoidWrapper(aHandle);
    }

    @Override
    public int[] getPortList()
    {
        return SolenoidWrapperJni.getPortList();
    }
}

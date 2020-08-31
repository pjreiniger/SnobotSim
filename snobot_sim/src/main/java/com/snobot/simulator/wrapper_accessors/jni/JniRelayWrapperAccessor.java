
package com.snobot.simulator.wrapper_accessors.jni;

import com.snobot.simulator.jni.module_wrapper.RelayWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IRelayWrapper;
import com.snobot.simulator.module_wrappers.RelayWrapper;
import com.snobot.simulator.wrapper_accessors.RelayWrapperAccessor;

public class JniRelayWrapperAccessor extends BaseWrapperAccessor<IRelayWrapper> implements RelayWrapperAccessor
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
    public int[] getPortList()
    {
        return RelayWrapperJni.getPortList();
    }
}

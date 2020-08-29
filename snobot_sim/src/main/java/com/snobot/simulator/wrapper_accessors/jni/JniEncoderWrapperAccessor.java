
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.EncoderWrapperJni;
import com.snobot.simulator.module_wrappers.EncoderWrapper;
import com.snobot.simulator.wrapper_accessors.EncoderWrapperAccessor;

public class JniEncoderWrapperAccessor extends BaseWrapperAccessor<EncoderWrapper> implements EncoderWrapperAccessor
{
    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return EncoderWrapperJni.createSimulator(aPort, aType);
    }

    @Override
    public boolean connectSpeedController(int aEncoderHandle, int aSpeedControllerHandle)
    {
        EncoderWrapper wrapper = getWrapper(aEncoderHandle);
        if (wrapper == null)
        {
            return false;
        }
        return wrapper.connectSpeedController(aSpeedControllerHandle);
    }

    @Override
    public boolean isHookedUp(int aPort)
    {
        return getWrapper(aPort).isHookedUp();
    }

    @Override
    public int getHookedUpId(int aPort)
    {
        return getWrapper(aPort).getHookedUpId();
    }

    @Override
    public double getDistance(int aPort)
    {
        return getWrapper(aPort).getDistance();
    }

    @Override
    protected EncoderWrapper createWrapperForExistingType(int aHandle)
    {
        return new EncoderWrapper(aHandle);
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(EncoderWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }
}

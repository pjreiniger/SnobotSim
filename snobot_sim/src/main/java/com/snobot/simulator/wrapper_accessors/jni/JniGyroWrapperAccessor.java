
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.GyroWrapperJni;
import com.snobot.simulator.module_wrappers.GyroWrapper;
import com.snobot.simulator.wrapper_accessors.GyroWrapperAccessor;

public class JniGyroWrapperAccessor extends BaseWrapperAccessor<GyroWrapper> implements GyroWrapperAccessor
{
    @Override
    public boolean isInitialized(int aPort)
    {
        return getWrapper(aPort).isInitialized();
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return GyroWrapperJni.createSimulator(aPort, aType);
    }

    @Override
    public void removeSimulator(int aPort)
    {
        getWrapper(aPort).removeSimluator(aPort);
    }

    @Override
    public void setName(int aPort, String aName)
    {
        getWrapper(aPort).setName(aName);
    }

    @Override
    public String getName(int aPort)
    {
        return getWrapper(aPort).getName();
    }

    @Override
    public boolean getWantsHidden(int aPort)
    {
        return getWrapper(aPort).getWantsHidden();
    }

    @Override
    public double getAngle(int aPort)
    {
        return getWrapper(aPort).getAngle();
    }

    @Override
    public void setAngle(int aPort, double aAngle)
    {
        getWrapper(aPort).setAngle(aAngle);
    }

    @Override
    public void reset(int aPort)
    {
        getWrapper(aPort).reset();
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

    @Override
    public String getType(int aPort)
    {
        return null;
    }
}

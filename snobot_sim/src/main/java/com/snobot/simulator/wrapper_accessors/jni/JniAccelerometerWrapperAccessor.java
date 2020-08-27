
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.AccelerometerWrapperJni;
import com.snobot.simulator.module_wrappers.AccelerometerWrapper;
import com.snobot.simulator.wrapper_accessors.AccelerometerWrapperAccessor;

public class JniAccelerometerWrapperAccessor extends BaseWrapperAccessor<AccelerometerWrapper> implements AccelerometerWrapperAccessor
{
    @Override
    public boolean isInitialized(int aPort)
    {
        return getWrapper(aPort).isInitialized();
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        AccelerometerWrapper wrapper = new AccelerometerWrapper(aPort, aType);
        return register(aPort, wrapper);
    }

    @Override
    public void removeSimulator(int aPort)
    {
        getWrapper(aPort).removeSimulator();
        removeWrapper(aPort);
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
    public double getAcceleration(int aPort)
    {
        return getWrapper(aPort).getAcceleration();
    }

    @Override
    public void setAcceleration(int aPort, double aAcceleration)
    {
        getWrapper(aPort).setAcceleration(aAcceleration);
    }

    @Override
    protected AccelerometerWrapper createWrapperForExistingType(int aHandle)
    {
        return new AccelerometerWrapper(aHandle);
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(AccelerometerWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }

    @Override
    public String getType(int aPort)
    {
        return null;
    }
}

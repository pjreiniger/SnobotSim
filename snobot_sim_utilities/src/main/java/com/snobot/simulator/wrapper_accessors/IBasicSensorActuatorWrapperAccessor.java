package com.snobot.simulator.wrapper_accessors;

import com.snobot.simulator.module_wrapper.interfaces.ISensorWrapper;

import java.util.List;

public interface IBasicSensorActuatorWrapperAccessor<WrapperType extends ISensorWrapper>
{
    WrapperType getWrapper(int aHandle);

    List<Integer> getPortList();

    boolean createSimulator(int aPort, String aType);

    void removeSimulator(int aPort);
}

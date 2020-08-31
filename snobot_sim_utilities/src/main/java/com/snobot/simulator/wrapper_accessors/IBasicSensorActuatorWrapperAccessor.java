package com.snobot.simulator.wrapper_accessors;

import com.snobot.simulator.module_wrapper.interfaces.ISensorWrapper;

import java.util.Map;

public interface IBasicSensorActuatorWrapperAccessor<WrapperType extends ISensorWrapper>
{
    WrapperType getWrapper(int aHandle);

    Map<Integer, WrapperType> getWrappers();

    WrapperType createSimulator(int aPort, String aType);

    void removeSimulator(int aPort);
}

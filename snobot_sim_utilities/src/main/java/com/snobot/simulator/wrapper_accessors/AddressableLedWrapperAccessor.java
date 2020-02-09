package com.snobot.simulator.wrapper_accessors;

public interface AddressableLedWrapperAccessor extends IBasicSensorActuatorWrapperAccessor
{
    byte[] getData(int aPort);
}

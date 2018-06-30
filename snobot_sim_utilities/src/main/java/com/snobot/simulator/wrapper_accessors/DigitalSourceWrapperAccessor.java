
package com.snobot.simulator.wrapper_accessors;

public interface DigitalSourceWrapperAccessor extends IBasicSensorActuatorWrapperAccessor
{
    public boolean getState(int aPort);

    public void setState(int aPort, boolean aValue);
}


package com.snobot.simulator.wrapper_accessors;

public interface SolenoidWrapperAccessor extends IBasicSensorActuatorWrapperAccessor
{
    public boolean get(int aPort);
}

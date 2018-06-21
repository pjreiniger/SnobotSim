
package com.snobot.simulator.wrapper_accessors;

public interface SolenoidWrapperAccessor extends IBasicSensorActuatorWrapperAccessor
{
    public boolean createSimulator(int aPort, String aType, boolean aIsStartup);

    public boolean get(int aPort);
}

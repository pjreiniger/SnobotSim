
package com.snobot.simulator.wrapper_accessors;

public interface DigitalSourceWrapperAccessor extends IBasicSensorActuatorWrapperAccessor
{
    public boolean createSimulator(int aPort, String aType, boolean aIsStartup);

    public boolean getState(int aPort);

    public void setState(int aPort, boolean aValue);
}

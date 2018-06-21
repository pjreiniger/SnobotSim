
package com.snobot.simulator.wrapper_accessors;

public interface RelayWrapperAccessor extends IBasicSensorActuatorWrapperAccessor
{
    public boolean createSimulator(int aPort, String aType, boolean aIsStartup);

    public boolean getFowardValue(int aPort);

    public boolean getReverseValue(int aPort);
}

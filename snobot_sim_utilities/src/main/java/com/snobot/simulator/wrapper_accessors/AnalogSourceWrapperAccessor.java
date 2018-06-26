
package com.snobot.simulator.wrapper_accessors;

public interface AnalogSourceWrapperAccessor extends IBasicSensorActuatorWrapperAccessor
{
    public boolean createSimulator(int aPort, String aType);

    public double getVoltage(int aPort);
}

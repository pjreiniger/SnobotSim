
package com.snobot.simulator.wrapper_accessors;

public interface AnalogOutputWrapperAccessor extends IBasicSensorActuatorWrapperAccessor
{
    public double getVoltage(int aPort);

    public void setVoltage(int aPort, double aVoltage);
}

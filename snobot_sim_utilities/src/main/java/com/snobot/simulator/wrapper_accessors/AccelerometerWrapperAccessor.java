
package com.snobot.simulator.wrapper_accessors;

public interface AccelerometerWrapperAccessor extends IBasicSensorActuatorWrapperAccessor
{
    public double getAcceleration(int aPort);

    public void setAcceleration(int aPort, double aAcceleration);

}

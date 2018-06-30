
package com.snobot.simulator.wrapper_accessors;

public interface GyroWrapperAccessor extends IBasicSensorActuatorWrapperAccessor
{
    public double getAngle(int aPort);

    public void setAngle(int aPort, double aAngle);

    public void reset(int aPort);
}

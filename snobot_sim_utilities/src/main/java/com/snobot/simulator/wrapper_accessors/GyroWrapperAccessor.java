
package com.snobot.simulator.wrapper_accessors;

public interface GyroWrapperAccessor extends IBasicSensorActuatorWrapperAccessor
{
    public boolean createSimulator(int aPort, String aType, boolean aIsStartup);

    public void register(int aPort, String aName);

    public double getAngle(int aPort);

    public void setAngle(int aPort, double aAngle);

    public void reset(int aPort);
}

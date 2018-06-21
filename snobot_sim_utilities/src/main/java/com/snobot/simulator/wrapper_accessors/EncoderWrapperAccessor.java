
package com.snobot.simulator.wrapper_accessors;

public interface EncoderWrapperAccessor extends IBasicSensorActuatorWrapperAccessor
{
    public boolean createSimulator(int aPort, String aType, boolean aIsStartup);

    public boolean connectSpeedController(int aEncoderHandle, int aSpeedControllerHandle);

    public boolean isHookedUp(int aPort);

    public int getHookedUpId(int aPort);

    public double getDistance(int aPort);
}

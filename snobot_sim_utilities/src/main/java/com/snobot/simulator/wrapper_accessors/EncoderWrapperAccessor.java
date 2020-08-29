
package com.snobot.simulator.wrapper_accessors;

import com.snobot.simulator.module_wrapper.interfaces.IEncoderWrapper;

public interface EncoderWrapperAccessor extends IBasicSensorActuatorWrapperAccessor<IEncoderWrapper>
{
    public boolean connectSpeedController(int aEncoderHandle, int aSpeedControllerHandle);

    public boolean isHookedUp(int aPort);

    public int getHookedUpId(int aPort);

    public double getDistance(int aPort);
}

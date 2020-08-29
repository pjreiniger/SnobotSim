package com.snobot.simulator.module_wrapper.interfaces;

public interface IEncoderWrapper extends ISensorWrapper, IMotorFeedbackSensor
{

    void reset();

    boolean connectSpeedController(int aSpeedControllerHandle);

    boolean connectSpeedController(IPwmWrapper aSpeedController);

    boolean isHookedUp();

    int getHookedUpId();

}

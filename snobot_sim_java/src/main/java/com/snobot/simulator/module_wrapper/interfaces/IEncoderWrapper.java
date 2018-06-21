package com.snobot.simulator.module_wrapper.interfaces;

import com.snobot.simulator.simulator_components.IMotorFeedbackSensor;

public interface IEncoderWrapper extends ISensorWrapper, IMotorFeedbackSensor
{

    void reset();

}

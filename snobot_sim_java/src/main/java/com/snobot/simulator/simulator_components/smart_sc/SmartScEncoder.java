package com.snobot.simulator.simulator_components.smart_sc;

import com.snobot.simulator.module_wrapper.BaseEncoderWrapper;

public class SmartScEncoder extends BaseEncoderWrapper
{

    public SmartScEncoder(int aPort)
    {
        super("CAN Encoder (" + (aPort - BaseCanSmartSpeedController.sCAN_SC_OFFSET) + ")");
    }
}

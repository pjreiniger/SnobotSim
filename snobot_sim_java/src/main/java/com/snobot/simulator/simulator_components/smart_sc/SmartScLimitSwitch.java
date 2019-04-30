package com.snobot.simulator.simulator_components.smart_sc;

import com.snobot.simulator.module_wrapper.ASensorWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IDigitalIoWrapper;

public class SmartScLimitSwitch extends ASensorWrapper implements IDigitalIoWrapper
{
    private boolean mState;

    public SmartScLimitSwitch(int aPort)
    {
        super("CAN Limit Switch (" + (aPort - BaseCanSmartSpeedController.sCAN_SC_OFFSET) + ")");
        setWantsHidden(true);
    }

    @Override
    public boolean get()
    {
        return mState;
    }

    @Override
    public void set(boolean aState)
    {
        mState = aState;
    }

}

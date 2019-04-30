package com.snobot.simulator.simulator_components.smart_sc;

import com.snobot.simulator.module_wrapper.ASensorWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogInWrapper;

public class SmartScAnalogIn extends ASensorWrapper implements IAnalogInWrapper
{
    private double mVoltage;

    public SmartScAnalogIn(int aPort)
    {
        super("CAN Analog (" + (aPort - BaseCanSmartSpeedController.sCAN_SC_OFFSET) + ")");
    }

    @Override
    public void setVoltage(double aVoltage)
    {
        mVoltage = aVoltage;
    }

    @Override
    public double getVoltage()
    {
        return mVoltage;
    }

}

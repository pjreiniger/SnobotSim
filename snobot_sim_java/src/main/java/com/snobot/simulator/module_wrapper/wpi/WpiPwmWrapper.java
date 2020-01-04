package com.snobot.simulator.module_wrapper.wpi;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.module_wrapper.BasePwmWrapper;

import edu.wpi.first.hal.HALValue;
import edu.wpi.first.hal.sim.NotifyCallback;

public class WpiPwmWrapper extends BasePwmWrapper implements NotifyCallback
{

    public WpiPwmWrapper(int aPort)
    {
        super(aPort, "Speed Controller " + aPort);
    }

    @Override
    public void callback(String aCallbackType, HALValue aHalValue)
    {
        LogManager.getLogger(WpiAnalogGyroWrapper.class).log(Level.WARN, "Callback " + aCallbackType + " not supported");
    }

}

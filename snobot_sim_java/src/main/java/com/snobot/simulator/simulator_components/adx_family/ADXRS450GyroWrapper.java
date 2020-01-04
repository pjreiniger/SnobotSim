package com.snobot.simulator.simulator_components.adx_family;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.BaseGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.ISpiWrapper;


public class ADXRS450GyroWrapper extends BaseGyroWrapper implements ISpiWrapper
{

    public ADXRS450GyroWrapper(int aPort)
    {
        super("ADXRS450 Gyro", new LazySimDoubleWrapper("", "")::get, new LazySimDoubleWrapper("", "")::set);

        SensorActuatorRegistry.get().register((IGyroWrapper) this, 100 + aPort);
    }
}

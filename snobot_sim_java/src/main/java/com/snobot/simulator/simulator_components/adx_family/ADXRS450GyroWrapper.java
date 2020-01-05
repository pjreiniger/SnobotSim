package com.snobot.simulator.simulator_components.adx_family;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.BaseGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.ISpiWrapper;
import com.snobot.simulator.simulator_components.LazySimDoubleWrapper;


public class ADXRS450GyroWrapper extends BaseGyroWrapper implements ISpiWrapper
{

    public ADXRS450GyroWrapper(String aDeviceName, int aPort)
    {
        super("ADXRS450 Gyro", new LazySimDoubleWrapper(aDeviceName, "Angle")::get, new LazySimDoubleWrapper(aDeviceName, "Angle")::set);

        SensorActuatorRegistry.get().register((IGyroWrapper) this, 100 + aPort);
    }
}

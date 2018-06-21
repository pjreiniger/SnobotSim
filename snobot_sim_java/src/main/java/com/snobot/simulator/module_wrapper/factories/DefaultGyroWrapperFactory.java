package com.snobot.simulator.module_wrapper.factories;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiAnalogGyroWrapper;
import com.snobot.simulator.simulator_components.adx_family.ADXRS450_GyroWrapper;

public class DefaultGyroWrapperFactory extends BaseWrapperFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(DefaultGyroWrapperFactory.class);

    public boolean create(int aPort, String aType, boolean aIsStartup)
    {
        boolean success = true;

        if (WpiAnalogGyroWrapper.class.getName().equals(aType))
        {
            WpiAnalogGyroWrapper wrapper = new WpiAnalogGyroWrapper(aPort, "Analog Gyro");
            SensorActuatorRegistry.get().register(wrapper, aPort);
            logIfMissing(aIsStartup, SensorActuatorRegistry.get().getGyros(), aPort, getClass(), "WPI Analog Gyro simulator not registered");
        }
        else if (ADXRS450_GyroWrapper.class.getName().equals(aType))
        {
            // Set up elsewhere
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Could not create analog gyro of type " + aType);
            success = false;
        }

        return success;
    }
}

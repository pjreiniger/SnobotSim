package com.snobot.simulator.module_wrapper.factories;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiAnalogOutWrapper;

public class DefaultAnalogOutWrapperFactory extends BaseWrapperFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(DefaultAnalogOutWrapperFactory.class);

    @Override
    public boolean create(int aPort, String aType)
    {
        boolean success = true;

        if (WpiAnalogOutWrapper.class.getName().equals(aType))
        {
            SensorActuatorRegistry.get().register(new WpiAnalogOutWrapper(aPort), aPort);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Could not create analog source of type " + aType);
            success = false;
        }

        return success;
    }

}

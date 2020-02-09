package com.snobot.simulator.module_wrapper.factories;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiAddressableLedWrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultAddressableWrapperFactory extends BaseWrapperFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(DefaultAddressableWrapperFactory.class);

    @Override
    public boolean create(int aPort, String aType)
    {
        boolean success = true;

        if (WpiAddressableLedWrapper.class.getName().equals(aType))
        {
            SensorActuatorRegistry.get().register(new WpiAddressableLedWrapper(aPort), aPort);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Could not create addressable LED of type " + aType);
            success = false;
        }

        return success;
    }
}

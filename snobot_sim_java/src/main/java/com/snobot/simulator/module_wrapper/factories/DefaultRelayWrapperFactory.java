package com.snobot.simulator.module_wrapper.factories;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiRelayWrapper;
import com.snobot.simulator.wrapper_accessors.java.JavaRelayWrapperAccessor;

public class DefaultRelayWrapperFactory extends BaseWrapperFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(JavaRelayWrapperAccessor.class);

    public boolean create(int aPort, String aType)
    {
        boolean success = true;

        if (WpiRelayWrapper.class.getName().equals(aType))
        {
            SensorActuatorRegistry.get().register(new WpiRelayWrapper(aPort), aPort);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Could not create relay of type " + aType);
            success = false;
        }

        return success;
    }
}

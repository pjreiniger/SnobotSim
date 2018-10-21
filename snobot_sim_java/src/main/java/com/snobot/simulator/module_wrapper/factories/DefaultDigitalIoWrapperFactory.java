package com.snobot.simulator.module_wrapper.factories;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiDigitalIoWrapper;
import com.snobot.simulator.wrapper_accessors.java.JavaDigitalSourceWrapperAccessor;

public class DefaultDigitalIoWrapperFactory extends BaseWrapperFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(JavaDigitalSourceWrapperAccessor.class);

    @Override
    public boolean create(int aPort, String aType)
    {
        boolean success = true;

        if (WpiDigitalIoWrapper.class.getName().equals(aType))
        {
            SensorActuatorRegistry.get().register(new WpiDigitalIoWrapper(aPort), aPort);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Could not create digital source of type " + aType);
            success = false;
        }

        return success;
    }

}

package com.snobot.simulator.module_wrapper.factories;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiSolenoidWrapper;
import com.snobot.simulator.wrapper_accessors.java.JavaSolenoidWrapperAccessor;

public class DefaultSolenoidWrapperFactory extends BaseWrapperFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(JavaSolenoidWrapperAccessor.class);

    public boolean create(int aPort, String aType, boolean aIsStartup)
    {
        boolean success = true;

        if (WpiSolenoidWrapper.class.getName().equals(aType))
        {
            SensorActuatorRegistry.get().register(new WpiSolenoidWrapper(aPort), aPort);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Could not create solenoid of type " + aType);
            success = false;
        }

        return success;
    }

}

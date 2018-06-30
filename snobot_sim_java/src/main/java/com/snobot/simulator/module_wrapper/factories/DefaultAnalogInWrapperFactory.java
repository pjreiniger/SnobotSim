package com.snobot.simulator.module_wrapper.factories;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiAnalogInWrapper;
import com.snobot.simulator.simulator_components.ctre.CtreTalonSrxSpeedControllerSim;

public class DefaultAnalogInWrapperFactory extends BaseWrapperFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(DefaultAnalogInWrapperFactory.class);

    public boolean create(int aPort, String aType)
    {
        boolean success = true;

        if (WpiAnalogInWrapper.class.getName().equals(aType))
        {
            SensorActuatorRegistry.get().register(new WpiAnalogInWrapper(aPort), aPort);
        }
        else if (CtreTalonSrxSpeedControllerSim.CtreAnalogIn.class.getName().equals(aType))
        {
            SensorActuatorRegistry.get().register(new CtreTalonSrxSpeedControllerSim.CtreAnalogIn(aPort), aPort + 100);
            sLOGGER.log(Level.INFO, "Created CAN Encoder for port " + aPort);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Could not create analog source of type " + aType);
            success = false;
        }

        return success;
    }

}

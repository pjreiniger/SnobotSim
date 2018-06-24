package com.snobot.simulator.module_wrapper.factories;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiEncoderWrapper;
import com.snobot.simulator.simulator_components.ctre.CtreTalonSrxSpeedControllerSim;

public class DefaultEncoderWrapperFactory extends BaseWrapperFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(DefaultEncoderWrapperFactory.class);

    public boolean create(int aPort, String aType, boolean aIsStartup)
    {
        boolean success = true;

        if (WpiEncoderWrapper.class.getName().equals(aType))
        {
            SensorActuatorRegistry.get().register(new WpiEncoderWrapper(aPort), aPort);
        }
        else if (CtreTalonSrxSpeedControllerSim.CtreEncoder.class.getName().equals(aType))
        {
            SensorActuatorRegistry.get().register(new CtreTalonSrxSpeedControllerSim.CtreEncoder(aPort), aPort);
            sLOGGER.log(Level.INFO, "Created CAN Encoder for port " + aPort);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Could not create encoder of type " + aType);
            success = false;
        }

        return success;
    }

}

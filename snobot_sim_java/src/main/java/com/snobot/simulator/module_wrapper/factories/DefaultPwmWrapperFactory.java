package com.snobot.simulator.module_wrapper.factories;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiPwmWrapper;
import com.snobot.simulator.simulator_components.ctre.CtreTalonSrxSpeedControllerSim;
import com.snobot.simulator.simulator_components.rev.RevSpeedControllerSimWrapper;

public class DefaultPwmWrapperFactory extends BaseWrapperFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(DefaultPwmWrapperFactory.class);

    @Override
    public boolean create(int aPort, String aType)
    {
        boolean success = true;

        if (WpiPwmWrapper.class.getName().equals(aType))
        {
            SensorActuatorRegistry.get().register(new WpiPwmWrapper(aPort), aPort);
        }
        else if (CtreTalonSrxSpeedControllerSim.class.getName().equals(aType))
        {
            CtreTalonSrxSpeedControllerSim output = new CtreTalonSrxSpeedControllerSim(aPort);
            SensorActuatorRegistry.get().register(output, aPort);
        }
        else if (RevSpeedControllerSimWrapper.class.getName().equals(aType))
        {
            RevSpeedControllerSimWrapper output = new RevSpeedControllerSimWrapper(aPort);
            SensorActuatorRegistry.get().register(output, aPort);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Could not create speed controller of type " + aType);
            success = false;
        }

        return success;
    }
}

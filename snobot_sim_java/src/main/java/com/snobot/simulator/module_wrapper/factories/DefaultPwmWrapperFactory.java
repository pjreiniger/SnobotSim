package com.snobot.simulator.module_wrapper.factories;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiPwmWrapper;
import com.snobot.simulator.simulator_components.ctre.CtreTalonSrxSpeedControllerSim;

public class DefaultPwmWrapperFactory extends BaseWrapperFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(DefaultPwmWrapperFactory.class);

    public boolean create(int aPort, String aType, boolean aIsStartup)
    {
        boolean success = true;

        if (WpiPwmWrapper.class.getName().equals(aType))
        {
            SensorActuatorRegistry.get().register(new WpiPwmWrapper(aPort), aPort);
            logIfMissing(aIsStartup, SensorActuatorRegistry.get().getSpeedControllers(), aPort, getClass(), "WPI PWM simulator not registered");
        }
        else if (CtreTalonSrxSpeedControllerSim.class.getName().equals(aType))
        {
            CtreTalonSrxSpeedControllerSim output = new CtreTalonSrxSpeedControllerSim(aPort);
            logIfMissing(aIsStartup, SensorActuatorRegistry.get().getSpeedControllers(), aPort, getClass(), "CTRE PWM simulator not registered");
            SensorActuatorRegistry.get().register(output, aPort + 100);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Could not create speed controller of type " + aType);
            success = false;
        }

        return success;
    }

    public void tryCreate(int aPort, String aType)
    {
        // TODO Auto-generated method stub

    }
}

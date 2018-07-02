package com.snobot.simulator.module_wrapper.factories;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.jni.can.CanCallbackJni;
import com.snobot.simulator.module_wrapper.wpi.WpiAnalogGyroWrapper;
import com.snobot.simulator.simulator_components.adx_family.ADXRS450GyroWrapper;
import com.snobot.simulator.simulator_components.ctre.CtrePigeonImuSim;

public class DefaultGyroWrapperFactory extends BaseWrapperFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(DefaultGyroWrapperFactory.class);

    public boolean create(int aPort, String aType)
    {
        boolean success = true;

        if (WpiAnalogGyroWrapper.class.getName().equals(aType))
        {
            WpiAnalogGyroWrapper wrapper = new WpiAnalogGyroWrapper(aPort, "Analog Gyro");
            SensorActuatorRegistry.get().register(wrapper, aPort);
        }
        else if (ADXRS450GyroWrapper.class.getName().equals(aType))
        {
            sLOGGER.log(Level.DEBUG, "ADXRS450GyroWrapper is set up elsewhere");
        }
        else if (CtrePigeonImuSim.PigeonGyroWrapper.class.getName().equals(aType))
        {
            int portWithoutOffset = aPort - CtrePigeonImuSim.sCTRE_OFFSET;
            int basePort = portWithoutOffset / 3;

            if (portWithoutOffset % 3 == 0)
            {
                CanCallbackJni.sCAN_MANAGER.createPigeon(basePort);
            }
            else
            {
                sLOGGER.log(Level.DEBUG, "Pigeon was (correctly) already set up for " + basePort);
            }
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Could not create gyro of type " + aType);
            success = false;
        }

        return success;
    }
}

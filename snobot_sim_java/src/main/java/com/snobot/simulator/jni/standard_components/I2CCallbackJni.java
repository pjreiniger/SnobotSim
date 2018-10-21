package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;

import edu.wpi.first.hal.sim.mockdata.I2CDataJNI;
import edu.wpi.first.hal.sim.SimValue;

public final class I2CCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(I2CCallbackJni.class);

    private I2CCallbackJni()
    {

    }

    public static void reset()
    {
        for (int i = 0; i < 2; ++i)
        {
            I2CDataJNI.resetData(i);

            I2CDataJNI.registerInitializedCallback(i, new I2CCallback(i), false);
        }
    }

    private static class I2CCallback extends PortBasedNotifyCallback
    {
        public I2CCallback(int aIndex)
        {
            super(aIndex);
        }

        @Override
        public void callback(String aCallbackType, SimValue aHalValue)
        {
            if ("Initialized".equals(aCallbackType))
            {
                if (aHalValue.getBoolean())
                {
                    if (SensorActuatorRegistry.get().getI2CWrappers().containsKey(mPort))
                    {
                        SensorActuatorRegistry.get().getI2CWrappers().get(mPort).setInitialized(true);
                    }
                    else
                    {
                        sLOGGER.log(Level.ERROR, "No I2C simulator registered for port " + mPort);
                    }
                }
                else
                {
                    sLOGGER.log(Level.INFO, "Shutting down I2C port " + mPort);
                }
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown I2C callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }
}

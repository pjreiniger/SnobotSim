package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;

import edu.wpi.first.hal.sim.mockdata.SPIDataJNI;
import edu.wpi.first.hal.HALValue;

public final class SpiCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(SpiCallbackJni.class);

    private SpiCallbackJni()
    {

    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public static void reset()
    {
        for (int i = 0; i < 5; ++i)
        {
            SPIDataJNI.resetData(i);

            SPIDataJNI.registerInitializedCallback(i, new SpiCallback(i), false);
        }
    }

    private static class SpiCallback extends PortBasedNotifyCallback
    {
        public SpiCallback(int aIndex)
        {
            super(aIndex);
        }

        @Override
        public void callback(String aCallbackType, HALValue aHalValue)
        {
            if ("Initialized".equals(aCallbackType))
            {
                if (aHalValue.getBoolean())
                {
                    if (SensorActuatorRegistry.get().getSpiWrappers().containsKey(mPort))
                    {
                        SensorActuatorRegistry.get().getSpiWrappers().get(mPort).setInitialized(true);
                    }
                    else
                    {
                        sLOGGER.log(Level.ERROR, "No SPI simulator registered for port " + mPort);
                    }
                }
                else
                {
                    sLOGGER.log(Level.INFO, "Shutting down SPI port " + mPort);
                }
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown SPI callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }
}

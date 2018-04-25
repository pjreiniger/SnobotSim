package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.RelayWrapper;

import edu.wpi.first.hal.sim.mockdata.RelayDataJNI;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.sim.SimValue;

public final class RelayCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(RelayCallbackJni.class);

    private RelayCallbackJni()
    {

    }

    private static class RelayCallback extends PortBasedNotifyCallback
    {
        public RelayCallback(int aIndex)
        {
            super(aIndex);
        }

        @Override
        public void callback(String aCallbackType, SimValue aHalValue)
        {
            if ("InitializedForward".equals(aCallbackType))
            {
                SensorActuatorRegistry.get().register(new RelayWrapper(mPort), mPort);
            }
            else if ("InitializedReverse".equals(aCallbackType))
            { // NOPMD
              // Nothing to do, assume it was initialized in forwards call
            }
            else if ("Forward".equals(aCallbackType))
            {
                SensorActuatorRegistry.get().getRelays().get(mPort).setRelayForwards(aHalValue.getBoolean());
            }
            else if ("Reverse".equals(aCallbackType))
            {
                SensorActuatorRegistry.get().getRelays().get(mPort).setRelayReverse(aHalValue.getBoolean());
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown Relay callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }

    public static void reset()
    {
        for (int i = 0; i < SensorBase.kRelayChannels; ++i)
        {
            RelayDataJNI.resetData(i);

            RelayCallback callback = new RelayCallback(i);
            RelayDataJNI.registerInitializedForwardCallback(i, callback, false);
            RelayDataJNI.registerInitializedReverseCallback(i, callback, false);
            RelayDataJNI.registerForwardCallback(i, callback, false);
            RelayDataJNI.registerReverseCallback(i, callback, false);
        }
    }
}

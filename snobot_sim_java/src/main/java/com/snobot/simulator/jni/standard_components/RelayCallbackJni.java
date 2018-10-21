package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiRelayWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import edu.wpi.first.hal.sim.mockdata.RelayDataJNI;
import edu.wpi.first.wpilibj.SensorUtil;
import edu.wpi.first.hal.sim.SimValue;

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
                if (!DataAccessorFactory.getInstance().getRelayAccessor().getPortList().contains(mPort))
                {
                    DataAccessorFactory.getInstance().getRelayAccessor().createSimulator(mPort, WpiRelayWrapper.class.getName());
                    sLOGGER.log(Level.WARN, "Simulator on port " + mPort + " was not registerd before starting the robot");
                }
                SensorActuatorRegistry.get().getRelays().get(mPort).setInitialized(true);
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown Relay callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }

    public static void reset()
    {
        for (int i = 0; i < SensorUtil.kRelayChannels; ++i)
        {
            RelayDataJNI.resetData(i);

            RelayCallback callback = new RelayCallback(i);
            RelayDataJNI.registerInitializedForwardCallback(i, callback, false);
        }
    }
}

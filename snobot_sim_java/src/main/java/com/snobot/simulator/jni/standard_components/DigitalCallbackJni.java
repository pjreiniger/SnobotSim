package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiDigitalIoWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import edu.wpi.first.hal.HALValue;
import edu.wpi.first.hal.sim.mockdata.DIODataJNI;
import edu.wpi.first.wpilibj.SensorUtil;

public final class DigitalCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(DigitalCallbackJni.class);

    private DigitalCallbackJni()
    {

    }

    private static class DigitalIoCallback extends PortBasedNotifyCallback
    {
        public DigitalIoCallback(int aIndex)
        {
            super(aIndex);
        }

        @Override
        public void callback(String aCallbackType, HALValue aHalValue)
        {
            if ("Initialized".equals(aCallbackType))
            {
                if (!DataAccessorFactory.getInstance().getDigitalAccessor().getPortList().contains(mPort))
                {
                    DataAccessorFactory.getInstance().getDigitalAccessor().createSimulator(mPort, WpiDigitalIoWrapper.class.getName());
                    sLOGGER.log(Level.WARN, "Simulator on port " + mPort + " was not registerd before starting the robot");
                }
                SensorActuatorRegistry.get().getDigitalSources().get(mPort).setInitialized(true);
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown Digital callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public static void reset()
    {
        for (int i = 0; i < SensorUtil.kDigitalChannels; ++i)
        {
            DIODataJNI.resetData(i);

            DigitalIoCallback callback = new DigitalIoCallback(i);
            DIODataJNI.registerInitializedCallback(i, callback, false);
        }

    }
}

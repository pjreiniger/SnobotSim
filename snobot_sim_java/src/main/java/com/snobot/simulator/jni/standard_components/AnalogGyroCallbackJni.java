package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiAnalogGyroWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import edu.wpi.first.hal.sim.mockdata.AnalogGyroDataJNI;
import edu.wpi.first.hal.sim.SimValue;

public final class AnalogGyroCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(AnalogGyroCallbackJni.class);

    private AnalogGyroCallbackJni()
    {

    }

    private static class AnalogGyroCallback extends PortBasedNotifyCallback
    {
        public AnalogGyroCallback(int aIndex)
        {
            super(aIndex);
        }

        @Override
        public void callback(String aCallbackType, SimValue aHalValue)
        {
            if ("Initialized".equals(aCallbackType))
            {
                if (!DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(mPort))
                {
                    DataAccessorFactory.getInstance().getGyroAccessor().createSimulator(mPort, WpiAnalogGyroWrapper.class.getName());
                    sLOGGER.log(Level.WARN, "Simulator on port " + mPort + " was not registerd before starting the robot");
                }
                SensorActuatorRegistry.get().getAnalogIn().get(mPort).setWantsHidden(true);
                SensorActuatorRegistry.get().getGyros().get(mPort).setInitialized(true);
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown AnalogGyro callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }

    public static void reset()
    {
        for (int i = 0; i < 2; ++i)
        {
            AnalogGyroDataJNI.resetData(i);

            AnalogGyroDataJNI.registerInitializedCallback(i, new AnalogGyroCallback(i), false);
        }
    }
}

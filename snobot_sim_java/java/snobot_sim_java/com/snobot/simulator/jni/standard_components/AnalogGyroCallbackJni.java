package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.simulator_components.gyro.AnalogGyroWrapper;

import edu.wpi.first.hal.sim.mockdata.AnalogGyroDataJNI;
import edu.wpi.first.wpilibj.sim.SimValue;

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
                AnalogGyroWrapper wrapper = new AnalogGyroWrapper(mPort, "Analog Gyro");
                SensorActuatorRegistry.get().register(wrapper, mPort);
            }
            else if ("Angle".equals(aCallbackType))
            {
                SensorActuatorRegistry.get().getGyros().get(mPort).setAngle(aHalValue.getDouble());
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

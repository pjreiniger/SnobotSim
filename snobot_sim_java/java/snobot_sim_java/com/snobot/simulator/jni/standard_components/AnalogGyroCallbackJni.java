package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.simulator_components.gyro.AnalogGyroWrapper;

import edu.wpi.first.hal.sim.mockdata.AnalogInDataJNI;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.sim.NotifyCallback;
import edu.wpi.first.wpilibj.sim.SimValue;

public final class AnalogGyroCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(AnalogGyroCallbackJni.class);

    private AnalogGyroCallbackJni()
    {

    }

    private static final NotifyCallback mCallback = new NotifyCallback()
    {

        @Override
        public void callback(String aCallbackType, SimValue aHalValue)
        {
            int port = (int) aHalValue.getLong();
            if ("Initialized".equals(aCallbackType))
            {
                AnalogGyroWrapper wrapper = new AnalogGyroWrapper(port, "Analog Gyro");
                SensorActuatorRegistry.get().register(wrapper, port);
            }
            else if ("Angle".equals(aCallbackType))
            {
                SensorActuatorRegistry.get().getGyros().get(port).setAngle(aHalValue.getDouble());
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown AnalogGyro callback " + aCallbackType + " - " + aHalValue);
            }
        }
    };

    public static void reset()
    {
        for (int i = 0; i < SensorBase.kAnalogInputChannels; ++i)
        {
            AnalogInDataJNI.registerInitializedCallback(i, mCallback, false);
        }
    }
}

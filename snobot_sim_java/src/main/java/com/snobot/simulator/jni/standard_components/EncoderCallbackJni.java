package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiEncoderWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import edu.wpi.first.hal.sim.mockdata.EncoderDataJNI;
import edu.wpi.first.wpilibj.sim.SimValue;

public final class EncoderCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(EncoderCallbackJni.class);

    private EncoderCallbackJni()
    {

    }

    private static class EncoderCallback extends PortBasedNotifyCallback
    {
        public EncoderCallback(int aIndex)
        {
            super(aIndex);
        }

        @Override
        public void callback(String aCallbackType, SimValue aHalValue)
        {
            if ("Initialized".equals(aCallbackType))
            {
                if (!DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().contains(mPort))
                {
                    DataAccessorFactory.getInstance().getEncoderAccessor().createSimulator(mPort, WpiEncoderWrapper.class.getName());
                    sLOGGER.log(Level.WARN, "Simulator on port " + mPort + " was not registerd before starting the robot");
                }
                SensorActuatorRegistry.get().getEncoders().get(mPort).setInitialized(true);
            }
            else if ("Count".equals(aCallbackType))
            {
                sLOGGER.log(Level.DEBUG, "Ignoring count feedback");
            }
            else if ("Period".equals(aCallbackType))
            {
                sLOGGER.log(Level.DEBUG, "Ignoring period feedback");
            }
            else if ("Reset".equals(aCallbackType))
            {
                SensorActuatorRegistry.get().getEncoders().get(mPort).reset();
            }
            else if ("DistancePerPulse".equals(aCallbackType))
            {
                WpiEncoderWrapper wpiEncoder = (WpiEncoderWrapper) SensorActuatorRegistry.get().getEncoders().get(mPort);
                wpiEncoder.setDistancePerTick(aHalValue.getDouble());
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown Encoder callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }

    public static void reset()
    {
        for (int i = 0; i < 8; ++i)
        {
            EncoderDataJNI.resetData(i);

            EncoderCallback callback = new EncoderCallback(i);
            EncoderDataJNI.registerInitializedCallback(i, callback, false);
            EncoderDataJNI.registerResetCallback(i, callback, false);
        }
    }

}

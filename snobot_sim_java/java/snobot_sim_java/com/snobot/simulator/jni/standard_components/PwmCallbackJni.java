package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.PwmWrapper;

import edu.wpi.first.hal.sim.mockdata.PWMDataJNI;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.sim.SimValue;

public final class PwmCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(PwmCallbackJni.class);

    private PwmCallbackJni()
    {

    }

    private static class PwmCallback extends PortBasedNotifyCallback
    {
        public PwmCallback(int aIndex)
        {
            super(aIndex);
        }

        @Override
        public void callback(String aCallbackType, SimValue aHalValue)
        {
            if ("Initialized".equals(aCallbackType))
            {
                SensorActuatorRegistry.get().register(new PwmWrapper(mPort), mPort);
            }
            else if ("Speed".equals(aCallbackType))
            {
                SensorActuatorRegistry.get().getSpeedControllers().get(mPort).set(aHalValue.getDouble());
            }
            else if ("ZeroLatch".equals(aCallbackType))
            {
                sLOGGER.log(Level.DEBUG, "ZeroLatch ignored");
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown PWM callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }

    public static void reset()
    {
        for (int i = 0; i < SensorBase.kPwmChannels; ++i)
        {
            PWMDataJNI.resetData(i);

            PwmCallback callback = new PwmCallback(i);
            PWMDataJNI.registerInitializedCallback(i, callback, false);
            PWMDataJNI.registerRawValueCallback(i, callback, false);
            PWMDataJNI.registerSpeedCallback(i, callback, false);
            PWMDataJNI.registerPositionCallback(i, callback, false);
            PWMDataJNI.registerPeriodScaleCallback(i, callback, false);
            PWMDataJNI.registerZeroLatchCallback(i, callback, false);
        }
    }

}

package com.snobot.simulator.jni.standard_components;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.jni.HalCallbackValue;
import com.snobot.simulator.module_wrapper.EncoderWrapper;
import com.snobot.simulator.module_wrapper.EncoderWrapper.DistanceSetterHelper;
import com.snobot.simulator.module_wrapper.EncoderWrapper.ResetHelper;

public final class EncoderCallbackJni
{
    private static final Logger sLOGGER = Logger.getLogger(EncoderCallbackJni.class);

    private EncoderCallbackJni()
    {

    }

    public static native void setEncoderDistance(int aHandle, double aAngle);

    public static native void registerEncoderCallback(String aFunctionName);

    public static void registerEncoderCallback()
    {
        registerEncoderCallback("encoderCallback");
    }

    public static native void reset();

    public static void encoderCallback(String aCallbackType, int aPort, HalCallbackValue aHalValue)
    {
        if ("Initialized".equals(aCallbackType))
        {
            DistanceSetterHelper setterHelper = new DistanceSetterHelper()
            {

                @Override
                public void setDistance(double aDistance)
                {
                    setEncoderDistance(aPort, aDistance);
                }
            };

            // TODO this should be removed when WPI fixes their dependency
            ResetHelper resetHelper = new ResetHelper()
            {

                @Override
                public void onReset()
                {
                    clearResetFlag(aPort);
                }
            };

            SensorActuatorRegistry.get().register(new EncoderWrapper(aPort, setterHelper, resetHelper), aPort);
        }
        else if ("Count".equals(aCallbackType))
        {
            sLOGGER.log(Level.DEBUG, "Ignoring count feedback");
        }
        else if ("Reset".equals(aCallbackType))
        {
            SensorActuatorRegistry.get().getEncoders().get(aPort).reset();
        }
        else if ("DistancePerPulse".equals(aCallbackType))
        {
            SensorActuatorRegistry.get().getEncoders().get(aPort).setDistancePerTick(aHalValue.mDouble);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Unknown Encoder callback " + aCallbackType + " - " + aHalValue);
        }
    }

    private static native void clearResetFlag(int aPort);
}

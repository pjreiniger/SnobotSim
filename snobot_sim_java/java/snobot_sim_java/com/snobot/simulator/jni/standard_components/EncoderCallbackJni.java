package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.EncoderWrapper;
import com.snobot.simulator.module_wrapper.EncoderWrapper.DistanceSetterHelper;
import com.snobot.simulator.module_wrapper.EncoderWrapper.ResetHelper;

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
                DistanceSetterHelper setterHelper = new DistanceSetterHelper()
                {

                    @Override
                    public void setDistance(double aDistance)
                    {
                        EncoderDataJNI.setCount(mPort, (int) aDistance);
                    }

                    @Override
                    public void setVelocity(double aVelocity)
                    {
                        EncoderDataJNI.setPeriod(mPort, 1 / aVelocity);
                    }
                };

                // TODO this should be removed when WPI fixes their dependency
                ResetHelper resetHelper = new ResetHelper()
                {

                    @Override
                    public void onReset()
                    {
                        EncoderDataJNI.setReset(mPort, false);
                    }
                };

                SensorActuatorRegistry.get().register(new EncoderWrapper(mPort, setterHelper, resetHelper), mPort);
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
                SensorActuatorRegistry.get().getEncoders().get(mPort).setDistancePerTick(aHalValue.getDouble());
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
            // EncoderDataJNI.registerInitializedCallback(i, callback, false);
            // EncoderDataJNI.registerCountCallback(i, callback, false);
            // EncoderDataJNI.registerPeriodCallback(i, callback, false);
            // EncoderDataJNI.registerResetCallback(i, callback, false);
            // EncoderDataJNI.registerMaxPeriodCallback(i, callback, false);
            // EncoderDataJNI.registerDirectionCallback(i, callback, false);
            // EncoderDataJNI.registerReverseDirectionCallback(i, callback,
            // false);
            // EncoderDataJNI.registerSamplesToAverageCallback(i, callback,
            // false);
            EncoderDataJNI.registerAllCallbacks(i, callback, false);
        }
    }

}

package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.AnalogWrapper;
import com.snobot.simulator.module_wrapper.AnalogWrapper.VoltageSetterHelper;

import edu.wpi.first.hal.sim.mockdata.AnalogInDataJNI;
import edu.wpi.first.hal.sim.mockdata.AnalogOutDataJNI;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.sim.SimValue;

public final class AnalogCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(AnalogCallbackJni.class);

    private AnalogCallbackJni()
    {

    }

    private static class AnalogCallback extends PortBasedNotifyCallback
    {
        public AnalogCallback(int aIndex)
        {
            super(aIndex);
        }

        @Override
        public void callback(String aCallbackType, SimValue aHalValue)
        {
            if ("Initialized".equals(aCallbackType))
            {
                SensorActuatorRegistry.get().register(new AnalogWrapper(mPort, new VoltageSetterHelper()
                {

                    @Override
                    public void setVoltage(double aVoltage)
                    {
                        AnalogInDataJNI.setVoltage(mPort, aVoltage);
                    }
                }), mPort);
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown Analog callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }

    public static void reset()
    {
        for (int i = 0; i < SensorBase.kAnalogInputChannels; ++i)
        {
            AnalogInDataJNI.resetData(i);

            AnalogCallback callback = new AnalogCallback(i);
            AnalogInDataJNI.registerInitializedCallback(i, callback, false);
            AnalogInDataJNI.registerAverageBitsCallback(i, callback, false);
            AnalogInDataJNI.registerOversampleBitsCallback(i, callback, false);
            AnalogInDataJNI.registerVoltageCallback(i, callback, false);
            AnalogInDataJNI.registerAccumulatorInitializedCallback(i, callback, false);
            AnalogInDataJNI.registerAccumulatorValueCallback(i, callback, false);
            AnalogInDataJNI.registerAccumulatorCountCallback(i, callback, false);
            AnalogInDataJNI.registerAccumulatorCenterCallback(i, callback, false);
            AnalogInDataJNI.registerAccumulatorDeadbandCallback(i, callback, false);
        }
        for (int i = 0; i < SensorBase.kAnalogOutputChannels; ++i)
        {
            AnalogOutDataJNI.resetData(i);

            AnalogCallback callback = new AnalogCallback(i);
            AnalogOutDataJNI.registerInitializedCallback(i, callback, false);
            AnalogOutDataJNI.registerVoltageCallback(i, callback, false);
        }
    }
}

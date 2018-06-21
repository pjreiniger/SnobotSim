package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.module_wrapper.wpi.WpiAnalogInWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import edu.wpi.first.hal.sim.mockdata.AnalogInDataJNI;
import edu.wpi.first.wpilibj.SensorUtil;
import edu.wpi.first.wpilibj.sim.SimValue;

public final class AnalogInCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(AnalogInCallbackJni.class);

    private AnalogInCallbackJni()
    {

    }

    private static class AnalogInCallback extends PortBasedNotifyCallback
    {
        public AnalogInCallback(int aIndex)
        {
            super(aIndex);
        }

        @Override
        public void callback(String aCallbackType, SimValue aHalValue)
        {
            if ("Initialized".equals(aCallbackType))
            {
                if (!DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList().contains(mPort))
                {
                    DataAccessorFactory.getInstance().getAnalogInAccessor().createSimulator(mPort, WpiAnalogInWrapper.class.getName(), false);
                    sLOGGER.log(Level.WARN, "Simulator on port " + mPort + " was not registerd before starting the robot");
                }
            }
            else if ("Voltage".equals(aCallbackType))
            {
                sLOGGER.log(Level.DEBUG, "Ignoring voltage feedback");
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown Analog callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }

    public static void reset()
    {
        for (int i = 0; i < SensorUtil.kAnalogInputChannels; ++i)
        {
            AnalogInDataJNI.resetData(i);

            AnalogInCallback callback = new AnalogInCallback(i);
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
    }
}

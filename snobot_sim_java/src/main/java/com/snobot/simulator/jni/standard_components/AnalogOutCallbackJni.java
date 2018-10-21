package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiAnalogOutWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import edu.wpi.first.hal.sim.mockdata.AnalogOutDataJNI;
import edu.wpi.first.wpilibj.SensorUtil;
import edu.wpi.first.hal.sim.SimValue;

public final class AnalogOutCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(AnalogOutCallbackJni.class);

    private AnalogOutCallbackJni()
    {

    }

    private static class AnalogOutCallback extends PortBasedNotifyCallback
    {
        public AnalogOutCallback(int aIndex)
        {
            super(aIndex);
        }

        @Override
        public void callback(String aCallbackType, SimValue aHalValue)
        {
            if ("Initialized".equals(aCallbackType))
            {
                if (!DataAccessorFactory.getInstance().getAnalogOutAccessor().getPortList().contains(mPort))
                {
                    DataAccessorFactory.getInstance().getAnalogOutAccessor().createSimulator(mPort, WpiAnalogOutWrapper.class.getName());
                    sLOGGER.log(Level.WARN, "Simulator on port " + mPort + " was not registerd before starting the robot");
                }
                SensorActuatorRegistry.get().getAnalogOut().get(mPort).setInitialized(true);
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown Analog callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }

    public static void reset()
    {
        for (int i = 0; i < SensorUtil.kAnalogOutputChannels; ++i)
        {
            AnalogOutDataJNI.resetData(i);

            AnalogOutCallback callback = new AnalogOutCallback(i);
            AnalogOutDataJNI.registerInitializedCallback(i, callback, false);
        }
    }
}

package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiSolenoidWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import edu.wpi.first.hal.HALValue;
import edu.wpi.first.hal.sim.NotifyCallback;
import edu.wpi.first.hal.sim.mockdata.PCMDataJNI;
import edu.wpi.first.wpilibj.SensorUtil;

public final class PcmCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(PcmCallbackJni.class);

    private PcmCallbackJni()
    {

    }

    public static class SolenoidCallback implements NotifyCallback
    {
        protected final int mIndex;
        protected final int mChannel;

        public SolenoidCallback(int aIndex, int aChannel)
        {
            mIndex = aIndex;
            mChannel = aChannel;
        }

        @Override
        public void callback(String aCallbackType, HALValue aHalValue)
        {
            int fullChannel = mIndex * SensorUtil.kSolenoidChannels + mChannel;
            if ("SolenoidInitialized".equals(aCallbackType))
            {
                if (!DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList().contains(fullChannel))
                {
                    DataAccessorFactory.getInstance().getSolenoidAccessor().createSimulator(fullChannel, WpiSolenoidWrapper.class.getName());
                    sLOGGER.log(Level.WARN, "Simulator on port " + fullChannel + " was not registerd before starting the robot");
                }
                SensorActuatorRegistry.get().getSolenoids().get(fullChannel).setInitialized(true);
            }
            else if ("SolenoidOutput".equals(aCallbackType))
            {
                SensorActuatorRegistry.get().getSolenoids().get(fullChannel).set(aHalValue.getBoolean());
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown PCM callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }

    public static void reset()
    {

        for (int module = 0; module < SensorUtil.kPCMModules; ++module)
        {
            PCMDataJNI.resetData(module);

            for (int channel = 0; channel < SensorUtil.kSolenoidChannels; ++channel)
            {
                SolenoidCallback callback = new SolenoidCallback(module, channel);
                PCMDataJNI.registerAllSolenoidCallbacks(module, channel, callback, false);
            }
        }
    }

}

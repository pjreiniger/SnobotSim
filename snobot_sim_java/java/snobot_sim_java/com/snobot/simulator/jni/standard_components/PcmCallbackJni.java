package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.SolenoidWrapper;

import edu.wpi.first.hal.sim.mockdata.PCMDataJNI;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.sim.NotifyCallback;
import edu.wpi.first.wpilibj.sim.SimValue;

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
        public void callback(String aCallbackType, SimValue aHalValue)
        {
            if ("SolenoidInitialized".equals(aCallbackType))
            {
                SensorActuatorRegistry.get().register(new SolenoidWrapper(mChannel), mChannel);
            }
            else if ("SolenoidOutput".equals(aCallbackType))
            {
                SensorActuatorRegistry.get().getSolenoids().get(mChannel).set(aHalValue.getBoolean());
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown PCM callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }

    public static void reset()
    {

        for (int module = 0; module < SensorBase.kPCMModules; ++module)
        {
            PCMDataJNI.resetData(module);

            for (int channel = 0; channel < SensorBase.kSolenoidChannels; ++channel)
            {
                SolenoidCallback callback = new SolenoidCallback(module, channel);
                PCMDataJNI.registerAllSolenoidCallbacks(module, channel, callback, false);
            }
        }
    }

}

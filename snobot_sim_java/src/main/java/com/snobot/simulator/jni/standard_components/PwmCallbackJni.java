package com.snobot.simulator.jni.standard_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiPwmWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import edu.wpi.first.hal.HALValue;
import edu.wpi.first.hal.sim.mockdata.PWMDataJNI;
import edu.wpi.first.wpilibj.SensorUtil;

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
        public void callback(String aCallbackType, HALValue aHalValue)
        {
            if ("Initialized".equals(aCallbackType))
            {
                if (!DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().contains(mPort))
                {
                    DataAccessorFactory.getInstance().getSpeedControllerAccessor().createSimulator(mPort, WpiPwmWrapper.class.getName());
                    sLOGGER.log(Level.WARN, "Simulator on port " + mPort + " was not registerd before starting the robot");
                }
                SensorActuatorRegistry.get().getSpeedControllers().get(mPort).setInitialized(true);
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
        for (int i = 0; i < SensorUtil.kPwmChannels; ++i)
        {
            PWMDataJNI.resetData(i);

            PwmCallback callback = new PwmCallback(i);
            PWMDataJNI.registerInitializedCallback(i, callback, false);
            PWMDataJNI.registerSpeedCallback(i, callback, false);
        }
    }

}

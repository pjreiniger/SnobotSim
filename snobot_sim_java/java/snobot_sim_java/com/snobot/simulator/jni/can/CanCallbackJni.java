package com.snobot.simulator.jni.can;

import java.nio.ByteBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.ctre.CtreCallback;
import com.snobot.simulator.ctre.CtreJni;
import com.snobot.simulator.simulator_components.ctre.CtreManager;

public final class CanCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(CanCallbackJni.class);

    public static final CtreManager sCAN_MANAGER = new CtreManager();

    private CanCallbackJni()
    {

    }

    private static class CtreMotorControllerCallback implements CtreCallback
    {
        @Override
        public void callback(String aName, int aDeviceId, ByteBuffer aBuffer, int aCount)
        {
            sCAN_MANAGER.handleMotorControllerMessage(aName, aDeviceId, aBuffer);
        }
    }

    private static class CtrePigeonImuCallback implements CtreCallback
    {
        @Override
        public void callback(String aName, int aDeviceId, ByteBuffer aBuffer, int aCount)
        {
            sCAN_MANAGER.handlePigeonMessage(aName, aDeviceId, aBuffer);
        }
    }

    private static final CtreMotorControllerCallback MOTOR_CALL = new CtreMotorControllerCallback();

    public static void reset()
    {
        CtreJni.registerCanMotorCallback(MOTOR_CALL);
        CtreJni.registerCanPigeonImuCallback(new CtrePigeonImuCallback());
    }

}

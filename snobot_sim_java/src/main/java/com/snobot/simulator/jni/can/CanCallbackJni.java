package com.snobot.simulator.jni.can;

import java.nio.ByteBuffer;

import com.snobot.simulator.ctre.CtreCallback;
import com.snobot.simulator.ctre.CtreJni;
import com.snobot.simulator.simulator_components.ctre.CtreManager;

public final class CanCallbackJni
{
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

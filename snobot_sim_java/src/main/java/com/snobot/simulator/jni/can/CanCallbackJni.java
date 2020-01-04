package com.snobot.simulator.jni.can;

import java.nio.ByteBuffer;

import com.snobot.simulator.ctre.CtreCallback;
import com.snobot.simulator.ctre.CtreJni;
import com.snobot.simulator.rev.RevCallback;
import com.snobot.simulator.rev.RevSimJni;
import com.snobot.simulator.simulator_components.ctre.CtreManager;
import com.snobot.simulator.simulator_components.rev.RevManager;

public final class CanCallbackJni
{
    public static final CtreManager sCTRE_MANAGER = new CtreManager();
    public static final RevManager sREV_MANAGER = new RevManager();

    private CanCallbackJni()
    {

    }

    private static class CtreMotorControllerCallback implements CtreCallback
    {
        @Override
        public void callback(String aName, int aDeviceId, ByteBuffer aBuffer, int aCount)
        {
            sCTRE_MANAGER.handleMotorControllerMessage(aName, aDeviceId, aBuffer);
        }
    }

    private static class CtrePigeonImuCallback implements CtreCallback
    {
        @Override
        public void callback(String aName, int aDeviceId, ByteBuffer aBuffer, int aCount)
        {
            sCTRE_MANAGER.handlePigeonMessage(aName, aDeviceId, aBuffer);
        }
    }

    private static class CtreCanifierCallback implements CtreCallback
    {
        @Override
        public void callback(String aName, int aDeviceId, ByteBuffer aBuffer, int aCount)
        {
            sCTRE_MANAGER.handleCanifierMessage(aName, aDeviceId, aBuffer);
        }
    }

    private static class CtreBuffTrajPointStreamCallback implements CtreCallback
    {
        @Override
        public void callback(String aName, int aDeviceId, ByteBuffer aBuffer, int aCount)
        {
            sCTRE_MANAGER.handleBuffTrajPointStreamMessage(aName, aDeviceId, aBuffer);
        }
    }

    private static class RevContollerCallback implements RevCallback
    {
        @Override
        public void callback(String aName, int aDeviceId, ByteBuffer aBuffer, int aCount)
        {
            sREV_MANAGER.handleMessage(aName, aDeviceId, aBuffer);
        }
    }

    public static void reset()
    {
        sCTRE_MANAGER.reset();
        CtreJni.registerCanMotorCallback(new CtreMotorControllerCallback());
        CtreJni.registerCanPigeonImuCallback(new CtrePigeonImuCallback());
        CtreJni.registerCanCanifierCallback(new CtreCanifierCallback());
        CtreJni.registerCanBuffTrajPointStreamCallback(new CtreBuffTrajPointStreamCallback());

        sREV_MANAGER.reset();
        RevSimJni.registerRevCallback(new RevContollerCallback());
    }

}

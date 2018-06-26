package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultEncoderWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IEncoderWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.wrapper_accessors.EncoderWrapperAccessor;

public class JavaEncoderWrapperAccessor extends BaseWrapperAccessor<IEncoderWrapper> implements EncoderWrapperAccessor
{
    private static final Logger sLOGGER = LogManager.getLogger(JavaEncoderWrapperAccessor.class);

    private final DefaultEncoderWrapperFactory mFactory;

    public JavaEncoderWrapperAccessor()
    {
        mFactory = new DefaultEncoderWrapperFactory();
    }

    @Override
    public boolean isInitialized(int aPort)
    {
        return getValue(aPort).isInitialized();
    }

    @Override
    public void setInitialized(int aPort, boolean aInitialized)
    {
        getValue(aPort).setInitialized(aInitialized);
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return mFactory.create(aPort, aType);
    }

    @Override
    protected Map<Integer, IEncoderWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getEncoders();
    }

    @Override
    public boolean connectSpeedController(int aEncoderHandle, int aSpeedControllerHandle)
    {
        boolean success = false;

        IEncoderWrapper encoder = SensorActuatorRegistry.get().getEncoders().get(aEncoderHandle);
        IPwmWrapper speedController = SensorActuatorRegistry.get().getSpeedControllers().get(aSpeedControllerHandle);
        if (encoder == null || speedController == null)
        {
            sLOGGER.log(Level.ERROR, "Could not conenct SC to ENC... " + aEncoderHandle + ", " + aSpeedControllerHandle);
        }
        else
        {
            speedController.setFeedbackSensor(encoder);
            success = true;
        }

        return success;
    }

    private IPwmWrapper getConnectedPwm(int aPort)
    {
        IEncoderWrapper encWrapper = getValue(aPort);
        for (IPwmWrapper pwmWrapper : SensorActuatorRegistry.get().getSpeedControllers().values())
        {
            if (pwmWrapper.getFeedbackSensor().equals(encWrapper))
            {
                return pwmWrapper;
            }
        }
        return null;
    }

    @Override
    public boolean isHookedUp(int aPort)
    {
        return getConnectedPwm(aPort) != null;
    }

    @Override
    public int getHookedUpId(int aPort)
    {
        IPwmWrapper wrapper = getConnectedPwm(aPort);
        if (wrapper != null)
        {
            return wrapper.getHandle();
        }
        return -1;
    }

    @Override
    public double getDistance(int aPort)
    {
        return getValue(aPort).getPosition();
    }
}

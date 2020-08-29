package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.interfaces.IEncoderWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseEncoderWrapper extends ASensorWrapper implements IEncoderWrapper
{
    private static final Logger sLOGGER = LogManager.getLogger(BaseEncoderWrapper.class);

    protected double mPosition;
    protected double mVelocity;

    public BaseEncoderWrapper(String aName)
    {
        super(aName);
    }

    @Override
    public void setPosition(double aPosition)
    {
        mPosition = aPosition;
    }

    @Override
    public void setVelocity(double aVelocity)
    {
        mVelocity = aVelocity;
    }

    @Override
    public double getPosition()
    {
        return mPosition;
    }

    @Override
    public double getVelocity()
    {
        return mVelocity;
    }

    @Override
    public void reset()
    {
        for (IPwmWrapper pwmWrapper : SensorActuatorRegistry.get().getSpeedControllers().values())
        {
            if (pwmWrapper.getFeedbackSensor().equals(this))
            {
                pwmWrapper.reset();
            }
        }

        mPosition = 0;
        mVelocity = 0;
    }

    @Override
    public boolean connectSpeedController(int aSpeedControllerHandle)
    {
        IPwmWrapper speedController = SensorActuatorRegistry.get().getSpeedControllers().get(aSpeedControllerHandle);
        if (speedController == null)
        {
            sLOGGER.log(Level.ERROR, "Could not conenct SC (" + aSpeedControllerHandle + " to encoder... ");

            return false;
        }

        speedController.setFeedbackSensor(this);
        return true;
    }

    @Override
    public boolean connectSpeedController(IPwmWrapper aSpeedController)
    {
        return connectSpeedController(aSpeedController.getHandle());
    }

    @Override
    public boolean isHookedUp()
    {
        return getConnectedPwm() != null;
    }

    @Override
    public int getHookedUpId()
    {
        IPwmWrapper wrapper = getConnectedPwm();
        if (wrapper != null)
        {
            return wrapper.getHandle();
        }
        return -1;
    }

    private IPwmWrapper getConnectedPwm()
    {
        for (IPwmWrapper pwmWrapper : SensorActuatorRegistry.get().getSpeedControllers().values())
        {
            if (pwmWrapper.getFeedbackSensor().equals(this))
            {
                return pwmWrapper;
            }
        }
        return null;
    }
}

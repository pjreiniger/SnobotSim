package com.snobot.simulator.simulator_components.rev;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.simulator_components.smart_sc.SmartScAnalogIn;
import com.snobot.simulator.simulator_components.smart_sc.SmartScEncoder;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.simulator_components.smart_sc.BaseCanSmartSpeedController;

public class RevSpeedControllerSimWrapper extends BaseCanSmartSpeedController
{
    private static final Logger sLOGGER = LogManager.getLogger(RevSpeedControllerSimWrapper.class);

    public RevSpeedControllerSimWrapper(int aCanHandle)
    {
        super(aCanHandle, "Rev", 4);
    }

    @Override
    protected void registerFeedbackSensor()
    {
        switch (mFeedbackDevice)
        {
        case Encoder:
            if (!DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().contains(mHandle))
            {
                DataAccessorFactory.getInstance().getEncoderAccessor().createSimulator(mHandle, SmartScEncoder.class.getName());
                DataAccessorFactory.getInstance().getEncoderAccessor().connectSpeedController(getHandle(), getHandle());
                sLOGGER.log(Level.WARN, "REV Encoder on port " + mCanHandle + " was not registerd before starting the robot");
            }
            SensorActuatorRegistry.get().getEncoders().get(getHandle()).setInitialized(true);
            break;
        case Analog:
            if (!DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList().contains(mHandle))
            {
                DataAccessorFactory.getInstance().getAnalogInAccessor().createSimulator(mHandle, SmartScAnalogIn.class.getName());
                sLOGGER.log(Level.WARN, "REV Analog on port " + mCanHandle + " was not registerd before starting the robot");
            }
            SensorActuatorRegistry.get().getAnalogIn().get(getHandle()).setInitialized(true);
            break;
        default:
            sLOGGER.log(Level.ERROR, "Unsupported feedback device " + mFeedbackDevice);
            break;
        }
    }

    @Override
    protected double getPositionUnitConversion()
    {
        return 1;
    }

    @Override
    protected double getMotionMagicAccelerationUnitConversion()
    {
        return 1;
    }

    @Override
    protected double getMotionMagicVelocityUnitConversion()
    {
        return 1;
    }

    @Override
    protected double calculateMotionProfileOutput(double aCurrentPosition, double aCurrentVelocity, int aModeType)
    {
        throw new IllegalStateException("Not supported");
    }

    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    public void setCanFeedbackDevice(int aFeedbackDevice)
    {
        FeedbackDevice newDevice = null;
        if (aFeedbackDevice == 1 || aFeedbackDevice == 2)
        {
            newDevice = FeedbackDevice.Encoder;
        }
        else
        {
            sLOGGER.log(Level.WARN, "Unsupported feedback device " + aFeedbackDevice);
        }

        setCanFeedbackDevice(newDevice);
    }
}

package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.simulator_components.IMotorFeedbackSensor;

public class EncoderWrapper extends ASensorWrapper implements IMotorFeedbackSensor
{
    private final DistanceSetterHelper mSetterHelper;
    private final ResetHelper mResetHelper;
    private double mDistancePerTick;
    private double mEncodingFactor;
    private double mPosition;
    private double mVelocity;

    public static interface DistanceSetterHelper
    {
        public void setDistance(double aDistance);

        public void setVelocity(double aVelocity);
    }

    private static class NullDistanceSetterHelper implements DistanceSetterHelper
    {
        public void setDistance(double aDistance)
        {
            // Nothing to do
        }

        public void setVelocity(double aVelocity)
        {
            // Nothing to do
        }
    }

    public static interface ResetHelper
    {
        public void onReset();
    }

    private static class NullResetHelper implements ResetHelper
    {
        public void onReset()
        {
            // Nothing to do
        }
    }

    public EncoderWrapper(String aName)
    {
        this(aName, new NullDistanceSetterHelper(), new NullResetHelper());
    }

    public EncoderWrapper(int aIndex, DistanceSetterHelper aSetterHelper, ResetHelper aResetHelper)
    {
        this("Encoder " + aIndex, aSetterHelper, aResetHelper);
    }

    public EncoderWrapper(String aName, DistanceSetterHelper aSetterHelper, ResetHelper aResetHelper)
    {
        super(aName);

        mSetterHelper = aSetterHelper;
        mResetHelper = aResetHelper;
        mPosition = 0;
        mEncodingFactor = 4;
        mDistancePerTick = 1;
    }

    public int getRaw()
    {
        return (int) (getPosition() / mEncodingFactor);
    }

    @Override
    public void setPosition(double aPosition)
    {
        boolean isUpdate = aPosition != mPosition;
        mPosition = aPosition;

        if (isUpdate)
        {
            mSetterHelper.setDistance(mPosition / mDistancePerTick);
        }
    }

    @Override
    public void setVelocity(double aVelocity)
    {
        boolean isUpdate = aVelocity != mVelocity;
        mVelocity = aVelocity;

        if (isUpdate)
        {
            mSetterHelper.setVelocity(mVelocity / mDistancePerTick);
        }
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

    public void reset()
    {
        for (PwmWrapper pwmWrapper : SensorActuatorRegistry.get().getSpeedControllers().values())
        {
            if (pwmWrapper.getFeedbackSensor().equals(this))
            {
                pwmWrapper.reset();
                setPosition(0);
            }
        }

        mResetHelper.onReset();
    }

    public void setDistancePerTick(double aDistancePerTick)
    {
        mDistancePerTick = aDistancePerTick;
    }
}

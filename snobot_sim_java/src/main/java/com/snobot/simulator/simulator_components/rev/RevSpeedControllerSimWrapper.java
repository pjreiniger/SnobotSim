package com.snobot.simulator.simulator_components.rev;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.simulator_components.smart_sc.BaseCanSmartSpeedController;

public class RevSpeedControllerSimWrapper extends BaseCanSmartSpeedController
{
    private static final Logger sLOGGER = LogManager.getLogger(RevSpeedControllerSimWrapper.class);

    public RevSpeedControllerSimWrapper(int aCanHandle)
    {
        super(aCanHandle, "Rev", 1);
    }

    @Override
    protected void registerFeedbackSensor()
    {
        sLOGGER.log(Level.ERROR, "Unsupported");
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

}

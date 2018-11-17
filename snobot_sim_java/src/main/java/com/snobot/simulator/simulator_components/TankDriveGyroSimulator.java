package com.snobot.simulator.simulator_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.module_wrapper.interfaces.ISimulatorUpdater;

public class TankDriveGyroSimulator implements ISimulatorUpdater
{
    private static final Logger sLOGGER = LogManager.getLogger(TankDriveGyroSimulator.class);

    private final com.snobot.simulator.simulator_components.config.TankDriveConfig mConfig;
    private final IPwmWrapper mLeftMotor;
    private final IPwmWrapper mRightMotor;
    private final IGyroWrapper mGyroWrapper;
    private final double mKP;
    private final boolean mIsSetup;

    private double mAngle; // degrees


    public TankDriveGyroSimulator(com.snobot.simulator.simulator_components.config.TankDriveConfig aConfig)
    {
        mConfig = aConfig;
        mRightMotor = SensorActuatorRegistry.get().getSpeedControllers().get(aConfig.getmRightMotorHandle());
        mLeftMotor = SensorActuatorRegistry.get().getSpeedControllers().get(aConfig.getmLeftMotorHandle());
        mGyroWrapper = SensorActuatorRegistry.get().getGyros().get(aConfig.getmGyroHandle());
        mKP = aConfig.getmTurnKp();

        mIsSetup = mLeftMotor != null && mRightMotor != null && mGyroWrapper != null;

        if (!mIsSetup)
        {
            sLOGGER.log(Level.ERROR, "Can't simulate gyro, some inputs are null: "
                    + "Left SpeedController (" + mLeftMotor + "), Right SpeedController (" + mRightMotor + ")" + "Gyro (" + mGyroWrapper
                    + ").  Available SpeedControllers: " + SensorActuatorRegistry.get().getSpeedControllers().keySet() + ", Available Gyros: "
                    + SensorActuatorRegistry.get().getGyros().keySet());
        }

        SensorActuatorRegistry.get().register(this);
    }

    @Override
    public void update()
    {
        if (mIsSetup)
        {
            double rightDist = mRightMotor.getPosition();
            double leftDist = mLeftMotor.getPosition();

            mAngle = (leftDist - rightDist) / (Math.PI * mKP) * 180.0;

            mGyroWrapper.setAngle(mAngle);
            sLOGGER.log(Level.TRACE,
                    "SIMULATOR : angle=" + mAngle + ", right=" + rightDist + ", left=" + leftDist);
        }
    }

    public boolean isSetup()
    {
        return mIsSetup;
    }

    @Override
    public Object getConfig()
    {
        return mConfig;
    }

    // Backwards compatibility
    public static class TankDriveConfig extends com.snobot.simulator.simulator_components.config.TankDriveConfig
    {

    }

}

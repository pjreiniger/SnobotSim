package com.snobot.simulator.simulator_components;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.interfaces.IEncoderWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.ISimulatorUpdater;

public class TankDriveGyroSimulator implements ISimulatorUpdater
{
    private static final Logger sLOGGER = LogManager.getLogger(TankDriveGyroSimulator.class);

    private final com.snobot.simulator.simulator_components.config.TankDriveConfig mConfig;
    private final IEncoderWrapper mLeftEncoder;
    private final IEncoderWrapper mRightEncoder;
    private final IGyroWrapper mGyroWrapper;
    private final double mKP;
    private final boolean mIsSetup;

    private double mAngle; // degrees


    public TankDriveGyroSimulator(com.snobot.simulator.simulator_components.config.TankDriveConfig aConfig)
    {
        mConfig = aConfig;
        mRightEncoder = SensorActuatorRegistry.get().getEncoders().get(aConfig.getmRightEncoderHandle());
        mLeftEncoder = SensorActuatorRegistry.get().getEncoders().get(aConfig.getmLeftEncoderHandle());
        mGyroWrapper = SensorActuatorRegistry.get().getGyros().get(aConfig.getmGyroHandle());
        mKP = aConfig.getmTurnKp();

        mIsSetup = mLeftEncoder != null && mRightEncoder != null && mGyroWrapper != null;

        if (!mIsSetup)
        {
            sLOGGER.log(Level.ERROR, "Can't simulate gyro, some inputs are null: "
                    + "Left Encoder (" + mLeftEncoder + "), Right Encoder (" + mRightEncoder + ")" + "Gyro (" + mGyroWrapper
                    + ").  Available Encoders: " + SensorActuatorRegistry.get().getEncoders().keySet() + ", Available Gyros: "
                    + SensorActuatorRegistry.get().getGyros().keySet());
        }

        SensorActuatorRegistry.get().register(this);
    }

    @Override
    public void update()
    {
        if (mIsSetup)
        {

            double rightDist = mRightEncoder.getPosition();
            double leftDist = mLeftEncoder.getPosition();

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

    public Object getConfig()
    {
        return mConfig;
    }

    // Backwards compatibility
    public static class TankDriveConfig extends com.snobot.simulator.simulator_components.config.TankDriveConfig
    {

    }

}

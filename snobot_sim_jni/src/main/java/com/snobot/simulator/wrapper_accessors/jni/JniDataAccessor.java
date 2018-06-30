package com.snobot.simulator.wrapper_accessors.jni;

import com.snobot.simulator.LogConfigurator;
import com.snobot.simulator.wrapper_accessors.AccelerometerWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.AnalogSourceWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.DigitalSourceWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.EncoderWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.GyroWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.IDataAccessor;
import com.snobot.simulator.wrapper_accessors.RelayWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.SimulatorDataAccessor;
import com.snobot.simulator.wrapper_accessors.SolenoidWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;

public class JniDataAccessor implements IDataAccessor
{
    private final AccelerometerWrapperAccessor mAccelerometer;
    private final GyroWrapperAccessor mGyro;
    private final AnalogSourceWrapperAccessor mAnalogIn;
    private final AnalogSourceWrapperAccessor mAnalogOut;
    private final DigitalSourceWrapperAccessor mDigital;
    private final EncoderWrapperAccessor mEncoder;
    private final RelayWrapperAccessor mRelay;
    private final SolenoidWrapperAccessor mSolenoid;
    private final SpeedControllerWrapperAccessor mPwm;
    private final SimulatorDataAccessor mSimulator;

    public JniDataAccessor()
    {
        LogConfigurator.loadLog4jConfig();

        mAccelerometer = new JniAccelerometerWrapperAccessor();
        mGyro = new JniGyroWrapperAccessor();
        mAnalogIn = new JniAnalogInWrapperAccessor();
        mAnalogOut = new JniAnalogOutWrapperAccessor();
        mDigital = new JniDigitalSourceWrapperAccessor();
        mEncoder = new JniEncoderWrapperAccessor();
        mRelay = new JniRelayWrapperAccessor();
        mSolenoid = new JniSolenoidWrapperAccessor();
        mPwm = new JniSpeedControllerWrapperAccessor();
        mSimulator = new JniSimulatorDataAccessor();
    }

    @Override
    public String getAccessorType()
    {
        return "CPP";
    }

    @Override
    public AccelerometerWrapperAccessor getAccelerometerAccessor()
    {
        return mAccelerometer;
    }

    @Override
    public GyroWrapperAccessor getGyroAccessor()
    {
        return mGyro;
    }

    @Override
    public AnalogSourceWrapperAccessor getAnalogInAccessor()
    {
        return mAnalogIn;
    }

    @Override
    public AnalogSourceWrapperAccessor getAnalogOutAccessor()
    {
        return mAnalogOut;
    }

    @Override
    public DigitalSourceWrapperAccessor getDigitalAccessor()
    {
        return mDigital;
    }

    @Override
    public EncoderWrapperAccessor getEncoderAccessor()
    {
        return mEncoder;
    }

    @Override
    public RelayWrapperAccessor getRelayAccessor()
    {
        return mRelay;
    }

    @Override
    public SolenoidWrapperAccessor getSolenoidAccessor()
    {
        return mSolenoid;
    }

    @Override
    public SpeedControllerWrapperAccessor getSpeedControllerAccessor()
    {
        return mPwm;
    }

    @Override
    public SimulatorDataAccessor getSimulatorDataAccessor()
    {
        return mSimulator;
    }

    @Override
    public String getInitializationErrors()
    {
        return null;
    }

}

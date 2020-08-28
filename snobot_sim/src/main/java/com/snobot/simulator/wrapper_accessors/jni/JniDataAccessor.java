package com.snobot.simulator.wrapper_accessors.jni;

import com.snobot.simulator.LogConfigurator;
import com.snobot.simulator.wrapper_accessors.AccelerometerWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.AnalogInWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.AnalogOutputWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.DigitalSourceWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.DriverStationDataAccessor;
import com.snobot.simulator.wrapper_accessors.EncoderWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.GyroWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.I2CWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.IDataAccessor;
import com.snobot.simulator.wrapper_accessors.RelayWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.SimulatorDataAccessor;
import com.snobot.simulator.wrapper_accessors.SolenoidWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.SpiWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.AddressableLedWrapperAccessor;

public class JniDataAccessor implements IDataAccessor
{
    private final AccelerometerWrapperAccessor mAccelerometer;
    private final GyroWrapperAccessor mGyro;
    private final AnalogInWrapperAccessor mAnalogIn;
    private final AnalogOutputWrapperAccessor mAnalogOut;
    private final DigitalSourceWrapperAccessor mDigital;
    private final EncoderWrapperAccessor mEncoder;
    private final RelayWrapperAccessor mRelay;
    private final SolenoidWrapperAccessor mSolenoid;
    private final SpeedControllerWrapperAccessor mPwm;
    private final I2CWrapperAccessor mI2C;
    private final SpiWrapperAccessor mSpi;
    private final DriverStationDataAccessor mDriverStation;
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
        mSpi = new JniSpiWrapperAccessor();
        mI2C = new JniI2CWrapperAccessor();
        mDriverStation = new JniDriverStationWrapperAccessor();
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
    public AnalogInWrapperAccessor getAnalogInAccessor()
    {
        return mAnalogIn;
    }

    @Override
    public AnalogOutputWrapperAccessor getAnalogOutAccessor()
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
    public SpiWrapperAccessor getSpiAccessor()
    {
        return mSpi;
    }

    @Override
    public I2CWrapperAccessor getI2CAccessor()
    {
        return mI2C;
    }

    @Override
    public DriverStationDataAccessor getDriverStationAccessor()
    {
        return mDriverStation;
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

    @Override
    public AddressableLedWrapperAccessor getAddressableLedAccessor()
    {
        return null;
    }

}

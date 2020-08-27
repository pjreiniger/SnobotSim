package com.snobot.simulator.wrapper_accessors.java;

import com.snobot.simulator.LogConfigurator;
import com.snobot.simulator.jni.RegisterCallbacksJni;
import com.snobot.simulator.wrapper_accessors.AccelerometerWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.AnalogInWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.AnalogOutputWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.DigitalSourceWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.DriverStationDataAccessor;
import com.snobot.simulator.wrapper_accessors.EncoderWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.GyroWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.I2CWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.AddressableLedWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.IBasicSensorActuatorWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.IDataAccessor;
import com.snobot.simulator.wrapper_accessors.RelayWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.SimulatorDataAccessor;
import com.snobot.simulator.wrapper_accessors.SolenoidWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.SpiWrapperAccessor;

public class JavaDataAccessor implements IDataAccessor
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
    private final AddressableLedWrapperAccessor mLed;
    private final I2CWrapperAccessor mI2C;
    private final SpiWrapperAccessor mSpi;
    private final DriverStationDataAccessor mDriverStation;
    private final SimulatorDataAccessor mSimulator;

    public JavaDataAccessor()
    {
        LogConfigurator.loadLog4jConfig();
        RegisterCallbacksJni.reset();

        mAccelerometer = new JavaAccelerometerWrapperAccessor();
        mGyro = new JavaGyroWrapperAccessor();
        mAnalogIn = new JavaAnalogInWrapperAccessor();
        mAnalogOut = new JavaAnalogOutWrapperAccessor();
        mDigital = new JavaDigitalSourceWrapperAccessor();
        mEncoder = new JavaEncoderWrapperAccessor();
        mRelay = new JavaRelayWrapperAccessor();
        mSolenoid = new JavaSolenoidWrapperAccessor();
        mPwm = new JavaSpeedControllerWrapperAccessor();
        mLed = new JavaAddressableWrapperAccessor();
        mSpi = new JavaSpiWrapperAccessor();
        mI2C = new JavaI2CWrapperAccessor();
        mDriverStation = new JavaDriverStationWrapperAccessor();
        mSimulator = new JavaSimulatorDataAccessor();
    }

    @Override
    public String getAccessorType()
    {
        return "Java";
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
    public AddressableLedWrapperAccessor getAddressableLedAccessor()
    {
        return mLed;
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

    private String getInitializationError(String aName, IBasicSensorActuatorWrapperAccessor aAccessor)
    {
        StringBuilder errorMessage = new StringBuilder(64);

        for (int port : aAccessor.getPortList())
        {
            if (!aAccessor.isInitialized(port))
            {
                aAccessor.removeSimulator(port);
                errorMessage.append("  <li>").append(aName).append(port).append("</li>\n");
            }
        }

        return errorMessage.toString();
    }

    @Override
    public String getInitializationErrors()
    {
        StringBuilder errorMessage = new StringBuilder(256);

        errorMessage
                .append(getInitializationError("Accelerometer ", DataAccessorFactory.getInstance().getAccelerometerAccessor()))
                .append(getInitializationError("Gyro ", DataAccessorFactory.getInstance().getGyroAccessor()))
                .append(getInitializationError("Analog In ", DataAccessorFactory.getInstance().getAnalogInAccessor()))
                .append(getInitializationError("Analog Out ", DataAccessorFactory.getInstance().getAnalogOutAccessor()))
                .append(getInitializationError("Digital IO ", DataAccessorFactory.getInstance().getDigitalAccessor()))
                .append(getInitializationError("Encoder ", DataAccessorFactory.getInstance().getEncoderAccessor()))
                .append(getInitializationError("Relay ", DataAccessorFactory.getInstance().getRelayAccessor()))
                .append(getInitializationError("Solenoid ", DataAccessorFactory.getInstance().getSolenoidAccessor()))
                .append(getInitializationError("Speed Controller ", DataAccessorFactory.getInstance().getSpeedControllerAccessor()));

        return errorMessage.toString();
    }
}

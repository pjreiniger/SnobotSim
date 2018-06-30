package com.snobot.simulator.wrapper_accessors.java;

import com.snobot.simulator.LogConfigurator;
import com.snobot.simulator.jni.RegisterCallbacksJni;
import com.snobot.simulator.wrapper_accessors.AccelerometerWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.AnalogSourceWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.DigitalSourceWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.EncoderWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.GyroWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.IDataAccessor;
import com.snobot.simulator.wrapper_accessors.RelayWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.SimulatorDataAccessor;
import com.snobot.simulator.wrapper_accessors.SolenoidWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;

public class JavaDataAccessor implements IDataAccessor
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

    public JavaDataAccessor()
    {
        LogConfigurator.loadLog4jConfig();
        RegisterCallbacksJni.registerAllCallbacks();

        mAccelerometer = new JavaAccelerometerWrapperAccessor();
        mGyro = new JavaGyroWrapperAccessor();
        mAnalogIn = new JavaAnalogInWrapperAccessor();
        mAnalogOut = new JavaAnalogOutWrapperAccessor();
        mDigital = new JavaDigitalSourceWrapperAccessor();
        mEncoder = new JavaEncoderWrapperAccessor();
        mRelay = new JavaRelayWrapperAccessor();
        mSolenoid = new JavaSolenoidWrapperAccessor();
        mPwm = new JavaSpeedControllerWrapperAccessor();
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
        StringBuilder errorMessage = new StringBuilder(256);

        for (int port : DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList())
        {
            if (!DataAccessorFactory.getInstance().getAccelerometerAccessor().isInitialized(port))
            {
                DataAccessorFactory.getInstance().getAccelerometerAccessor().removeSimluator(port);
                errorMessage.append("  <li>").append("Accelerometer ").append(port).append("</li>\n");
            }
        }

        for (int port : DataAccessorFactory.getInstance().getGyroAccessor().getPortList())
        {
            if (!DataAccessorFactory.getInstance().getGyroAccessor().isInitialized(port))
            {
                DataAccessorFactory.getInstance().getGyroAccessor().removeSimluator(port);
                errorMessage.append("  <li>").append("Gyro ").append(port).append("</li>\n");
            }
        }

        for (int port : DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList())
        {
            if (!DataAccessorFactory.getInstance().getAnalogInAccessor().isInitialized(port))
            {
                DataAccessorFactory.getInstance().getAnalogInAccessor().removeSimluator(port);
                errorMessage.append("  <li>").append("Analog In ").append(port).append("</li>\n");
            }
        }

        for (int port : DataAccessorFactory.getInstance().getAnalogOutAccessor().getPortList())
        {
            if (!DataAccessorFactory.getInstance().getAnalogOutAccessor().isInitialized(port))
            {
                DataAccessorFactory.getInstance().getAnalogOutAccessor().removeSimluator(port);
                errorMessage.append("  <li>").append("Analog Out ").append(port).append("</li>\n");
            }
        }

        for (int port : DataAccessorFactory.getInstance().getDigitalAccessor().getPortList())
        {
            if (!DataAccessorFactory.getInstance().getDigitalAccessor().isInitialized(port))
            {
                DataAccessorFactory.getInstance().getDigitalAccessor().removeSimluator(port);
                errorMessage.append("  <li>").append("Digital IO ").append(port).append("</li>\n");
            }
        }

        for (int port : DataAccessorFactory.getInstance().getEncoderAccessor().getPortList())
        {
            if (!DataAccessorFactory.getInstance().getEncoderAccessor().isInitialized(port))
            {
                DataAccessorFactory.getInstance().getEncoderAccessor().removeSimluator(port);
                errorMessage.append("  <li>").append("Digital IO ").append(port).append("</li>\n");
            }
        }

        for (int port : DataAccessorFactory.getInstance().getRelayAccessor().getPortList())
        {
            if (!DataAccessorFactory.getInstance().getRelayAccessor().isInitialized(port))
            {
                DataAccessorFactory.getInstance().getRelayAccessor().removeSimluator(port);
                errorMessage.append("  <li>").append("Relay ").append(port).append("</li>\n");
            }
        }

        for (int port : DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList())
        {
            if (!DataAccessorFactory.getInstance().getSolenoidAccessor().isInitialized(port))
            {
                DataAccessorFactory.getInstance().getSolenoidAccessor().removeSimluator(port);
                errorMessage.append("  <li>").append("Solenoid ").append(port).append("</li>\n");
            }
        }

        for (int port : DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList())
        {
            if (!DataAccessorFactory.getInstance().getSpeedControllerAccessor().isInitialized(port))
            {
                DataAccessorFactory.getInstance().getSpeedControllerAccessor().removeSimluator(port);
                errorMessage.append("  <li>").append("Speed Controller ").append(port).append("</li>\n");
            }
        }

        return errorMessage.toString();
    }
}

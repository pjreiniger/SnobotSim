package com.snobot.simulator.jni;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.AnalogWrapper;
import com.snobot.simulator.module_wrapper.AnalogWrapper.VoltageSetterHelper;
import com.snobot.simulator.module_wrapper.DigitalSourceWrapper;
import com.snobot.simulator.module_wrapper.DigitalSourceWrapper.StateSetterHelper;
import com.snobot.simulator.module_wrapper.EncoderWrapper;
import com.snobot.simulator.module_wrapper.EncoderWrapper.DistanceSetterHelper;
import com.snobot.simulator.module_wrapper.PwmWrapper;
import com.snobot.simulator.module_wrapper.RelayWrapper;
import com.snobot.simulator.module_wrapper.SolenoidWrapper;
import com.snobot.simulator.simulator_components.II2CWrapper;
import com.snobot.simulator.simulator_components.ISpiWrapper;
import com.snobot.simulator.simulator_components.components_factory.DefaultI2CSimulatorFactory;
import com.snobot.simulator.simulator_components.components_factory.DefaultSpiSimulatorFactory;
import com.snobot.simulator.simulator_components.components_factory.II2cSimulatorFactory;
import com.snobot.simulator.simulator_components.components_factory.ISpiSimulatorFactory;
import com.snobot.simulator.simulator_components.ctre.CanManager;
import com.snobot.simulator.simulator_components.gyro.GyroWrapper;
import com.snobot.simulator.simulator_components.gyro.GyroWrapper.AngleSetterHelper;

public class RegisterCallbacksJni extends BaseSnobotJni
{
    private static final Logger sLOGGER = Logger.getLogger(RegisterCallbacksJni.class);
    public static final ISpiSimulatorFactory sSPI_FACTORY = new DefaultSpiSimulatorFactory();
    public static final II2cSimulatorFactory sI2C_FACTORY = new DefaultI2CSimulatorFactory();
    public static final CanManager sCAN_MANAGER = new CanManager();

    public static native void reset();

    public static native void registerAnalogCallback(String functionName);

    public static native void registerAnalogGyroCallback(String functionName);

    public static native void registerCanCallback(String functionName);

    public static native void registerDigitalCallback(String functionName);

    public static native void registerEncoderCallback(String functionName);

    public static native void registerI2CCallback(String functionName);

    public static native void registerPcmCallback(String functionName);

    public static native void registerPdpCallback(String functionName);

    public static native void registerPwmCallback(String functionName);

    public static native void registerRelayCallback(String functionName);

    public static native void registerSpiCallback(String functionName);

    public static void registerAllCallbacks()
    {
        registerAnalogCallback();
        registerAnalogGyroCallback();
        registerCanCallback();
        registerDigitalCallback();
        registerEncoderCallback();
        registerI2CCallback();
        registerPcmCallback();
        registerPdpCallback();
        registerPwmCallback();
        registerRelayCallback();
        registerSpiCallback();
    }

    public static void registerAnalogCallback()
    {
        registerAnalogCallback("analogCallback");
    }

    public static void registerAnalogGyroCallback()
    {
        registerAnalogGyroCallback("analogGyroCallback");
    }

    public static void registerCanCallback()
    {
        registerCanCallback("canCallback");
    }

    public static void registerDigitalCallback()
    {
        registerDigitalCallback("digitalCallback");
    }

    public static void registerEncoderCallback()
    {
        registerEncoderCallback("encoderCallback");
    }

    public static void registerI2CCallback()
    {
        registerI2CCallback("i2cCallback");
    }

    public static void registerPcmCallback()
    {
        registerPcmCallback("pcmCallback");
    }

    public static void registerPdpCallback()
    {
        registerPdpCallback("pdpCallback");
    }

    public static void registerPwmCallback()
    {
        registerPwmCallback("pwmCallback");
    }

    public static void registerRelayCallback()
    {
        registerRelayCallback("relayCallback");
    }

    public static void registerSpiCallback()
    {
        registerSpiCallback("spiCallback");
    }

    public static void analogCallback(String callbackType, int port, HalCallbackValue halValue)
    {
        if ("Initialized".equals(callbackType))
        {
            SensorActuatorRegistry.get().register(new AnalogWrapper(port, new VoltageSetterHelper()
            {

                @Override
                public void setVoltage(double aVoltage)
                {
                    SensorFeedbackJni.setAnalogVoltage(port, aVoltage);
                }
            }), port);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Unknown Analog callback " + callbackType + " - " + halValue);
        }
    }

    public static void analogGyroCallback(String callbackType, int port, HalCallbackValue halValue)
    {
        if ("Initialized".equals(callbackType))
        {
            GyroWrapper wrapper = new GyroWrapper("Analog Gyro", new AngleSetterHelper()
            {
                @Override
                public void updateAngle(double aAngle)
                {
                    SensorFeedbackJni.setAnalogGyroAngle(port, aAngle);
                }
            });
            SensorActuatorRegistry.get().register(wrapper, port);
        }
        else if ("Angle".equals(callbackType))
        {
            SensorActuatorRegistry.get().getGyros().get(port).setAngle(halValue.mDouble);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Unknown AnalogGyro callback " + callbackType + " - " + halValue);
        }
    }

    public static void canCallback(String callbackType, int port, HalCallbackValue halValue)
    {
        sCAN_MANAGER.handleIncomingMessage(callbackType, halValue.mInt);
    }

    public static void digitalCallback(String callbackType, int port, HalCallbackValue halValue)
    {
        if ("Initialized".equals(callbackType))
        {
            SensorActuatorRegistry.get().register(new DigitalSourceWrapper(port, new StateSetterHelper()
            {

                @Override
                public void setState(boolean aState)
                {
                    SensorFeedbackJni.setDigitalInput(port, aState);
                }
            }), port);
        }
        else if ("Value".equals(callbackType))
        {
            SensorActuatorRegistry.get().getDigitalSources().get(port).set(halValue.mBoolean);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Unknown Digital callback " + callbackType + " - " + halValue);
        }
    }

    public static void encoderCallback(String callbackType, int port, HalCallbackValue halValue)
    {
        if ("Initialized".equals(callbackType))
        {
            SensorActuatorRegistry.get().register(new EncoderWrapper(port, new DistanceSetterHelper()
            {

                @Override
                public void setDistance(double aDistance)
                {
                    SensorFeedbackJni.setEncoderDistance(port, aDistance);
                }
            }), port);
        }
        else if ("Count".equals(callbackType))
        {
            sLOGGER.log(Level.DEBUG, "Ignoring count feedback");
        }
        else if ("Reset".equals(callbackType))
        {
            SensorActuatorRegistry.get().getEncoders().get(port).reset();
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Unknown Encoder callback " + callbackType + " - " + halValue);
        }
    }

    public static void i2cCallback(String callbackType, int port, HalCallbackValue halValue)
    {
        if ("Initialized".equals(callbackType))
        {
            II2CWrapper wrapper = sI2C_FACTORY.createI2CWrapper(port);
            SensorActuatorRegistry.get().register(wrapper, port);
        }
        else if ("Read".equals(callbackType))
        {
            SensorActuatorRegistry.get().getI2CWrapperss().get(port).handleRead();
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Unknown I2C callback " + callbackType + " - " + halValue);
        }
    }

    public static void pcmCallback(String callbackType, int port, HalCallbackValue halValue)
    {
        if ("SolenoidInitialized".equals(callbackType))
        {
            SensorActuatorRegistry.get().register(new SolenoidWrapper(port), port);
        }
        else if ("SolenoidOutput".equals(callbackType))
        {
            SensorActuatorRegistry.get().getSolenoids().get(port).set(halValue.mBoolean);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Unknown PCM callback " + callbackType + " - " + halValue);
        }
    }

    public static void pdpCallback(String callbackType, int port, HalCallbackValue halValue)
    {
        if ("Initialized".equals(callbackType))
        {

        }
        else
        {
            sLOGGER.log(Level.ERROR, "Unknown PDP callback " + callbackType + " - " + halValue);
        }
    }

    public static void pwmCallback(String callbackType, int port, HalCallbackValue halValue)
    {
        if ("Initialized".equals(callbackType))
        {
            SensorActuatorRegistry.get().register(new PwmWrapper(port), port);
        }
        else if ("Speed".equals(callbackType))
        {
            SensorActuatorRegistry.get().getSpeedControllers().get(port).set(halValue.mDouble);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Unknown PWM callback " + callbackType + " - " + halValue);
        }
    }

    public static void relayCallback(String callbackType, int port, HalCallbackValue halValue)
    {
        if ("InitializedForward".equals(callbackType))
        {
            SensorActuatorRegistry.get().register(new RelayWrapper(port), port);
        }
        else if ("InitializedReverse".equals(callbackType))
        {
            // Nothing to do, assume it was initialized in forwards call
        }
        else if ("Forward".equals(callbackType))
        {
            SensorActuatorRegistry.get().getRelays().get(port).setRelayForwards(halValue.mBoolean);
        }
        else if ("Reverse".equals(callbackType))
        {
            SensorActuatorRegistry.get().getRelays().get(port).setRelayReverse(halValue.mBoolean);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Unknown Relay callback " + callbackType + " - " + halValue);
        }
    }

    public static void spiCallback(String callbackType, int port, HalCallbackValue halValue)
    {
        if ("Initialized".equals(callbackType))
        {
            ISpiWrapper wrapper = sSPI_FACTORY.createSpiWrapper(port);
            SensorActuatorRegistry.get().register(wrapper, port);
        }
        else if ("Read".equals(callbackType))
        {
            SensorActuatorRegistry.get().getSpiWrappers().get(port).handleRead();
        }
        else if ("Write".equals(callbackType))
        {
            SensorActuatorRegistry.get().getSpiWrappers().get(port).handleWrite();
        }
        else if ("Transaction".equals(callbackType))
        {
            SensorActuatorRegistry.get().getSpiWrappers().get(port).handleTransaction();
        }
        else if ("ResetAccumulator".equals(callbackType))
        {
            SensorActuatorRegistry.get().getSpiWrappers().get(port).resetAccumulator();
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Unknown SPI callback " + callbackType + " - " + halValue);
        }
    }
}
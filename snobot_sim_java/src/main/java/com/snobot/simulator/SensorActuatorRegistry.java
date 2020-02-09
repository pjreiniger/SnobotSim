package com.snobot.simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import com.snobot.simulator.module_wrapper.interfaces.IAddressableLedWrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.module_wrapper.interfaces.IAccelerometerWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogInWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogOutWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IDigitalIoWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IEncoderWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.II2CWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IRelayWrapper;
import com.snobot.simulator.module_wrapper.interfaces.ISimulatorUpdater;
import com.snobot.simulator.module_wrapper.interfaces.ISolenoidWrapper;
import com.snobot.simulator.module_wrapper.interfaces.ISpiWrapper;

@SuppressWarnings("PMD.TooManyMethods")
public final class SensorActuatorRegistry
{
    private static final Logger sLOGGER = LogManager.getLogger(SensorActuatorRegistry.class);

    private static SensorActuatorRegistry mInstance = new SensorActuatorRegistry();

    private final Map<Integer, IPwmWrapper> mSpeedControllerMap = new TreeMap<>();
    private final Map<Integer, IRelayWrapper> mRelayWrapperMap = new TreeMap<>();
    private final Map<Integer, IDigitalIoWrapper> mDigitalSourceWrapperMap = new TreeMap<>();
    private final Map<Integer, IAnalogInWrapper> mAnalogInWrapperMap = new TreeMap<>();
    private final Map<Integer, IAnalogOutWrapper> mAnalogOutWrapperMap = new TreeMap<>();
    private final Map<Integer, ISolenoidWrapper> mSolenoidWrapperMap = new TreeMap<>();
    private final Map<Integer, IEncoderWrapper> mEncoderWrapperMap = new TreeMap<>();
    private final Map<Integer, IAddressableLedWrapper> mAddressableLedMap = new TreeMap<>();

    private final Map<Integer, IGyroWrapper> mGyroWrapperMap = new TreeMap<>();
    private final Map<Integer, IAccelerometerWrapper> mAccelerometerWrapperMap = new TreeMap<>();
    private final Map<Integer, II2CWrapper> mI2CWrapperMap = new TreeMap<>();
    private final Map<Integer, ISpiWrapper> mSpiWrapperMap = new TreeMap<>();

    private final Collection<ISimulatorUpdater> mSimulatorComponents = new ArrayList<>();

    private SensorActuatorRegistry()
    {

    }

    public static SensorActuatorRegistry get()
    {
        return mInstance;
    }

    public <ItemType> boolean registerItem(ItemType aItem, int aPort, Map<Integer, ItemType> aMap, String aMessage)
    {
        if (aMap.containsKey(aPort))
        {
            sLOGGER.log(Level.WARN, "Simulator already exists for port " + aPort);
        }
        aMap.put(aPort, aItem);
        return true;
    }

    public boolean register(IAnalogInWrapper aActuator, int aPort)
    {
        return registerItem(aActuator, aPort, mAnalogInWrapperMap, "Analog");
    }

    public boolean register(IAnalogOutWrapper aActuator, int aPort)
    {
        return registerItem(aActuator, aPort, mAnalogOutWrapperMap, "Analog");
    }

    public boolean register(IPwmWrapper aActuator, int aPort)
    {
        return registerItem(aActuator, aPort, mSpeedControllerMap, "Speed Controller");
    }

    public boolean register(IDigitalIoWrapper aSensor, int aPort)
    {
        return registerItem(aSensor, aPort, mDigitalSourceWrapperMap, "Digital IO");
    }

    public boolean register(ISolenoidWrapper aActuator, int aPort)
    {
        return registerItem(aActuator, aPort, mSolenoidWrapperMap, "Solenoid");
    }

    public boolean register(IRelayWrapper aActuator, int aPort)
    {
        return registerItem(aActuator, aPort, mRelayWrapperMap, "Relay");
    }

    public boolean register(IEncoderWrapper aEncoder, Integer aPort)
    {
        return registerItem(aEncoder, aPort, mEncoderWrapperMap, "Encoder");
    }

    public boolean register(IAccelerometerWrapper aSensor, Integer aPort)
    {
        return registerItem(aSensor, aPort, mAccelerometerWrapperMap, "Accelerometer");
    }

    public boolean register(IGyroWrapper aSensor, Integer aPort)
    {
        return registerItem(aSensor, aPort, mGyroWrapperMap, "Gyro");
    }

    public boolean register(II2CWrapper aSensor, Integer aPort)
    {
        return registerItem(aSensor, aPort, mI2CWrapperMap, "I2C");
    }

    public boolean register(ISpiWrapper aSensor, Integer aPort)
    {
        return registerItem(aSensor, aPort, mSpiWrapperMap, "SPI");
    }

    public boolean register(IAddressableLedWrapper aSensor, int aPort)
    {
        return registerItem(aSensor, aPort, mAddressableLedMap, "LED");
    }

    public boolean register(ISimulatorUpdater aUpdater)
    {
        mSimulatorComponents.add(aUpdater);
        return true;
    }

    public Map<Integer, IPwmWrapper> getSpeedControllers()
    {
        return mSpeedControllerMap;
    }

    public Map<Integer, ISolenoidWrapper> getSolenoids()
    {
        return mSolenoidWrapperMap;
    }

    public Map<Integer, IDigitalIoWrapper> getDigitalSources()
    {
        return mDigitalSourceWrapperMap;
    }

    public Map<Integer, IRelayWrapper> getRelays()
    {
        return mRelayWrapperMap;
    }

    public Map<Integer, IAnalogInWrapper> getAnalogIn()
    {
        return mAnalogInWrapperMap;
    }

    public Map<Integer, IAnalogOutWrapper> getAnalogOut()
    {
        return mAnalogOutWrapperMap;
    }

    public Map<Integer, IEncoderWrapper> getEncoders()
    {
        return mEncoderWrapperMap;
    }

    public Map<Integer, IAddressableLedWrapper> getLeds()
    {
        return mAddressableLedMap;
    }

    public Map<Integer, IAccelerometerWrapper> getAccelerometers()
    {
        return mAccelerometerWrapperMap;
    }

    public Map<Integer, IGyroWrapper> getGyros()
    {
        return mGyroWrapperMap;
    }

    public Map<Integer, II2CWrapper> getI2CWrappers()
    {
        return mI2CWrapperMap;
    }

    public Map<Integer, ISpiWrapper> getSpiWrappers()
    {
        return mSpiWrapperMap;
    }

    public Collection<ISimulatorUpdater> getSimulatorComponents()
    {
        return mSimulatorComponents;
    }

    public void reset()
    {
        mSpeedControllerMap.clear();
        mRelayWrapperMap.clear();
        mDigitalSourceWrapperMap.clear();
        mAnalogInWrapperMap.clear();
        mAnalogOutWrapperMap.clear();
        mSolenoidWrapperMap.clear();
        mEncoderWrapperMap.clear();
        mAddressableLedMap.clear();

        mGyroWrapperMap.clear();
        mAccelerometerWrapperMap.clear();
        mI2CWrapperMap.clear();
        mSpiWrapperMap.clear();

        mSimulatorComponents.clear();
    }
}

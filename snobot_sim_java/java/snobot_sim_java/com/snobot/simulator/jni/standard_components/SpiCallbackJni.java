package com.snobot.simulator.jni.standard_components;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.jni.HalCallbackValue;
import com.snobot.simulator.simulator_components.ISpiWrapper;
import com.snobot.simulator.simulator_components.components_factory.DefaultSpiSimulatorFactory;
import com.snobot.simulator.simulator_components.components_factory.ISpiSimulatorFactory;

import edu.wpi.first.hal.sim.mockdata.SPIDataJNI;
import edu.wpi.first.wpilibj.sim.BufferCallback;
import edu.wpi.first.wpilibj.sim.ConstBufferCallback;
import edu.wpi.first.wpilibj.sim.SimValue;

public final class SpiCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(SpiCallbackJni.class);
    private static ISpiSimulatorFactory sSPI_FACTORY = new DefaultSpiSimulatorFactory();
    private static final Map<Integer, ISpiWrapper> sCUSTOM_WRAPPERS = new HashMap<>();

    private static final String sUNKNOWN_CALLBACK_STR = "Unknown SPI callback ";

    private SpiCallbackJni()
    {

    }

    public static void setSpiFactory(ISpiSimulatorFactory aFactory)
    {
        sSPI_FACTORY = aFactory;
    }

    public static void reset()
    {
        sCUSTOM_WRAPPERS.clear();

        for (int i = 0; i < 5; ++i)
        {
            SPIDataJNI.resetData(i);

            SPIDataJNI.registerInitializedCallback(i, new SpiCallback(i), false);
        }
    }

    public static void registerSpiReadWriteCallback(int aPort, ISpiWrapper aWrapper)
    {
        SPIDataJNI.registerReadCallback(aPort, new SpiReadCallback(aPort));
        SPIDataJNI.registerWriteCallback(aPort, new SpiWriteCallback(aPort));
        sCUSTOM_WRAPPERS.put(aPort, aWrapper);
    }

    public static void spiCallback(String aCallbackType, int aPort, HalCallbackValue aHalValue)
    {
        if ("Initialized".equals(aCallbackType))
        {
            ISpiWrapper wrapper = sSPI_FACTORY.createSpiWrapper(aPort);
            SensorActuatorRegistry.get().register(wrapper, aPort);
        }
        else
        {
            sLOGGER.log(Level.ERROR, sUNKNOWN_CALLBACK_STR + aCallbackType + " - " + aHalValue);
        }
    }

    public static void setDefaultWrapper(int aPort, String aType)
    {
        sSPI_FACTORY.setDefaultWrapper(aPort, aType);
    }

    public static Collection<String> getAvailableTypes()
    {
        return sSPI_FACTORY.getAvailableTypes();
    }

    public static Map<Integer, String> getDefaults()
    {
        return sSPI_FACTORY.getDefaults();
    }

    private static class SpiWriteCallback implements ConstBufferCallback
    {
        private final int mPort;

        public SpiWriteCallback(int aIndex)
        {
            mPort = aIndex;
        }

        @Override
        public void callback(String aCallbackType, byte[] aBuffer, int aCount)
        {
            if (sCUSTOM_WRAPPERS.containsKey(mPort))
            {
                ISpiWrapper wrapper = sCUSTOM_WRAPPERS.get(mPort);
                if ("Write".equals(aCallbackType))
                {
                    wrapper.handleWrite(ByteBuffer.wrap(aBuffer));
                }
                else
                {
                    sLOGGER.log(Level.ERROR, sUNKNOWN_CALLBACK_STR + aCallbackType);
                }

            }
            else
            {
                sLOGGER.log(Level.ERROR, "Calling read/write for unregistered wrapper " + mPort);
            }
        }
    }

    private static class SpiReadCallback implements BufferCallback
    {
        private final int mPort;

        public SpiReadCallback(int aIndex)
        {
            mPort = aIndex;
        }

        @Override
        public void callback(String aCallbackType, byte[] aBuffer, int aCount)
        {
            if (sCUSTOM_WRAPPERS.containsKey(mPort))
            {
                ISpiWrapper wrapper = sCUSTOM_WRAPPERS.get(mPort);
                if ("Read".equals(aCallbackType))
                {
                    wrapper.handleRead(ByteBuffer.wrap(aBuffer));
                }
                else
                {
                    sLOGGER.log(Level.ERROR, sUNKNOWN_CALLBACK_STR + aCallbackType);
                }

            }
            else
            {
                sLOGGER.log(Level.ERROR, "Calling read/write for unregistered wrapper " + mPort);
            }
        }
    }

    private static class SpiCallback extends PortBasedNotifyCallback
    {
        public SpiCallback(int aIndex)
        {
            super(aIndex);
        }

        @Override
        public void callback(String aCallbackType, SimValue aHalValue)
        {
            if ("Initialized".equals(aCallbackType))
            {
                ISpiWrapper wrapper = sSPI_FACTORY.createSpiWrapper(mPort);
                SensorActuatorRegistry.get().register(wrapper, mPort);
            }
            else
            {
                sLOGGER.log(Level.ERROR, sUNKNOWN_CALLBACK_STR + aCallbackType + " - " + aHalValue);
            }
        }
    }
}

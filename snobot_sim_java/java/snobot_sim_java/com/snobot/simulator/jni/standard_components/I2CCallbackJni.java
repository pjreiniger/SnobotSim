package com.snobot.simulator.jni.standard_components;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.simulator_components.II2CWrapper;
import com.snobot.simulator.simulator_components.components_factory.DefaultI2CSimulatorFactory;
import com.snobot.simulator.simulator_components.components_factory.II2cSimulatorFactory;

import edu.wpi.first.hal.sim.mockdata.I2CDataJNI;
import edu.wpi.first.wpilibj.sim.BufferCallback;
import edu.wpi.first.wpilibj.sim.ConstBufferCallback;
import edu.wpi.first.wpilibj.sim.SimValue;

public final class I2CCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(I2CCallbackJni.class);
    private static II2cSimulatorFactory sI2C_FACTORY = new DefaultI2CSimulatorFactory();
    private static final Map<Integer, II2CWrapper> sCUSTOM_WRAPPERS = new HashMap<>();

    private I2CCallbackJni()
    {

    }

    public static void reset()
    {
        sCUSTOM_WRAPPERS.clear();

        for (int i = 0; i < 2; ++i)
        {
            I2CDataJNI.resetData(i);

            I2CDataJNI.registerInitializedCallback(i, new I2CCallback(i), false);
        }
    }

    public static void setI2CFactory(II2cSimulatorFactory aFactory)
    {
        sI2C_FACTORY = aFactory;
    }

    public static void registerReadWriteCallbacks(int aPort, II2CWrapper aWrapper)
    {
        I2CDataJNI.registerReadCallback(aPort, new I2CReadCallback(aPort));
        I2CDataJNI.registerWriteCallback(aPort, new I2CWriteCallback(aPort));
        sCUSTOM_WRAPPERS.put(aPort, aWrapper);
    }

    private static class I2CWriteCallback implements ConstBufferCallback
    {
        private final int mPort;

        public I2CWriteCallback(int aIndex)
        {
            mPort = aIndex;
        }

        @Override
        public void callback(String aCallbackType, byte[] aBuffer, int aCount)
        {
            if (sCUSTOM_WRAPPERS.containsKey(mPort))
            {
                II2CWrapper wrapper = sCUSTOM_WRAPPERS.get(mPort);
                if ("Write".equals(aCallbackType))
                {
                    wrapper.handleWrite(ByteBuffer.wrap(aBuffer));
                }
                else
                {
                    sLOGGER.log(Level.ERROR, "Unknown I2C callback " + aCallbackType);
                }

            }
            else
            {
                sLOGGER.log(Level.ERROR, "Calling read/write for unregistered wrapper " + mPort);
            }
        }
    }

    private static class I2CReadCallback implements BufferCallback
    {
        private final int mPort;

        public I2CReadCallback(int aIndex)
        {
            mPort = aIndex;
        }

        @Override
        public void callback(String aCallbackType, byte[] aBuffer, int aCount)
        {
            if (sCUSTOM_WRAPPERS.containsKey(mPort))
            {
                II2CWrapper wrapper = sCUSTOM_WRAPPERS.get(mPort);
                if ("Read".equals(aCallbackType))
                {
                    wrapper.handleRead(ByteBuffer.wrap(aBuffer));
                }
                else
                {
                    sLOGGER.log(Level.ERROR, "Unknown I2C callback " + aCallbackType);
                }

            }
            else
            {
                sLOGGER.log(Level.ERROR, "Calling read/write for unregistered wrapper " + mPort);
            }
        }
    }

    private static class I2CCallback extends PortBasedNotifyCallback
    {
        public I2CCallback(int aIndex)
        {
            super(aIndex);
        }

        @Override
        public void callback(String aCallbackType, SimValue aHalValue)
        {
            if ("Initialized".equals(aCallbackType))
            {
                II2CWrapper wrapper = sI2C_FACTORY.createI2CWrapper(mPort);
                SensorActuatorRegistry.get().register(wrapper, mPort);
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown I2C callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }

    public static void setDefaultWrapper(int aPort, String aType)
    {
        sI2C_FACTORY.setDefaultWrapper(aPort, aType);
    }

    public static Collection<String> getAvailableTypes()
    {
        return sI2C_FACTORY.getAvailableTypes();
    }

    public static Map<Integer, String> getDefaults()
    {
        return sI2C_FACTORY.getDefaults();
    }
}

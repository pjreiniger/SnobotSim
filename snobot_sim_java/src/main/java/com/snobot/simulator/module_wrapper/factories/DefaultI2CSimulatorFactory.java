package com.snobot.simulator.module_wrapper.factories;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.interfaces.II2CWrapper;
import com.snobot.simulator.simulator_components.adx_family.ADXFamily3AxisAccelerometer;
import com.snobot.simulator.simulator_components.adx_family.ADXL345I2CWrapper;
import com.snobot.simulator.simulator_components.navx.BaseNavxSimulatorWrapper;
import com.snobot.simulator.simulator_components.navx.I2CNavxSimulatorWrapper;

public class DefaultI2CSimulatorFactory extends BaseWrapperFactory implements II2cSimulatorFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(DefaultI2CSimulatorFactory.class);

    public static final String sNAVX_TYPE = "NavX";
    public static final String sADXL345_TYPE = "ADXL345";

    @Override
    public Map<Integer, String> getI2CWrapperTypes()
    {
        Map<Integer, String> output = new HashMap<>();

        for (Entry<Integer, II2CWrapper> pair : SensorActuatorRegistry.get().getI2CWrappers().entrySet())
        {
            String type = getNameForType(pair.getValue());
            if (type != null)
            {
                output.put(pair.getKey(), type);
            }
        }

        return output;
    }

    protected String getNameForType(II2CWrapper aType)
    {
        if (aType instanceof BaseNavxSimulatorWrapper)
        {
            return sNAVX_TYPE;
        }
        else if (aType instanceof ADXFamily3AxisAccelerometer)
        {
            return sADXL345_TYPE;
        }

        return null;
    }

    @Override
    public boolean create(int aPort, String aType)
    {
        return create(aPort, aType, true);
    }

    @Override
    public boolean create(int aPort, String aType, boolean aIsStartup)
    {
        String fullType = "I2C " + aType;

        II2CWrapper simulator = null;

        if (sNAVX_TYPE.equals(aType))
        {
            simulator = new I2CNavxSimulatorWrapper(fullType, aPort, 250 + aPort * 3);
        }
        else if (sADXL345_TYPE.equals(aType))
        {
            simulator = new ADXL345I2CWrapper(fullType, aPort, 50 + aPort * 3);
        }

        if (simulator == null)
        {
            sLOGGER.log(Level.ERROR, "Unknown I2C Type " + aType);
        }
        else
        {
            SensorActuatorRegistry.get().register(simulator, aPort);
        }

        return simulator == null;
    }

    @Override
    public Collection<String> getAvailableTypes()
    {
        return Arrays.asList(sNAVX_TYPE, sADXL345_TYPE);
    }
}

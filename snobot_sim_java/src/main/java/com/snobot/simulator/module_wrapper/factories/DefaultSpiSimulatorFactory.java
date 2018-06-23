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
import com.snobot.simulator.module_wrapper.interfaces.ISpiWrapper;
import com.snobot.simulator.simulator_components.adx_family.ADXFamily3AxisAccelerometer;
import com.snobot.simulator.simulator_components.adx_family.ADXL345SpiWrapper;
import com.snobot.simulator.simulator_components.adx_family.ADXL362SpiWrapper;
import com.snobot.simulator.simulator_components.adx_family.ADXRS450GyroWrapper;
import com.snobot.simulator.simulator_components.navx.BaseNavxSimulatorWrapper;
import com.snobot.simulator.simulator_components.navx.SpiNavxSimulatorWrapper;

import edu.wpi.first.wpilibj.sim.ADXRS450_GyroSim;

public class DefaultSpiSimulatorFactory extends BaseWrapperFactory implements ISpiSimulatorFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(DefaultSpiSimulatorFactory.class);

    public static final String sNAVX_TYPE = "NavX";
    public static final String sADXRS450_TYPE = "ADXRS450";
    public static final String sADXL345_TYPE = "ADXL345";
    public static final String sADXL362_TYPE = "ADXL362";

    @Override
    public Map<Integer, String> getSpiWrapperTypes()
    {
        Map<Integer, String> output = new HashMap<>();

        for (Entry<Integer, ISpiWrapper> pair : SensorActuatorRegistry.get().getSpiWrappers().entrySet())
        {
            String type = getNameForType(pair.getValue());
            if (type != null)
            {
                output.put(pair.getKey(), type);
            }
        }

        return output;
    }

    protected String getNameForType(ISpiWrapper aType)
    {
        if (aType instanceof BaseNavxSimulatorWrapper)
        {
            return sNAVX_TYPE;
        }
        else if (aType instanceof ADXFamily3AxisAccelerometer)
        {
            return sADXL345_TYPE;
        }
        else if (aType instanceof ADXRS450GyroWrapper)
        {
            return sADXRS450_TYPE;
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
        String fullType = "SPI " + aType;

        ISpiWrapper simulator = null;

        if (sNAVX_TYPE.equals(aType))
        {
            simulator = new SpiNavxSimulatorWrapper(fullType, aPort, 200 + aPort * 3);
        }
        else if (sADXRS450_TYPE.equals(aType))
        {
            simulator = new ADXRS450GyroWrapper(new ADXRS450_GyroSim(aPort), 100 + aPort);
        }
        else if (sADXL345_TYPE.equals(aType))
        {
            simulator = new ADXL345SpiWrapper(fullType, aPort, 100 + aPort * 3);
        }
        else if (sADXL362_TYPE.equals(aType))
        {
            simulator = new ADXL362SpiWrapper(fullType, aPort, 150 + aPort * 3);
        }

        if (simulator == null)
        {
            sLOGGER.log(Level.ERROR, "Could not create simulator for SPI on port " + aPort + " for type " + aType);
        }
        else
        {
            SensorActuatorRegistry.get().register(simulator, aPort);
        }

        return simulator == null;
    }

    public Collection<String> getAvailableTypes()
    {
        return Arrays.asList(sNAVX_TYPE, sADXRS450_TYPE, sADXL345_TYPE, sADXL362_TYPE);
    }
}

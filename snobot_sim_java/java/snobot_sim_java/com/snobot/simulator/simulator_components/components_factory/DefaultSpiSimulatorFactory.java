package com.snobot.simulator.simulator_components.components_factory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.navx.SpiNavxSimulator;
import com.snobot.simulator.simulator_components.ISpiWrapper;
import com.snobot.simulator.simulator_components.accelerometer.ADXFamily3AxisAccelerometer;
import com.snobot.simulator.simulator_components.gyro.SpiGyro;
import com.snobot.simulator.simulator_components.navx.NavxSimulatorWrapper;

import edu.wpi.first.wpilibj.sim.ADXL345_SpiSim;
import edu.wpi.first.wpilibj.sim.ADXL362Sim;
import edu.wpi.first.wpilibj.sim.ADXRS450_GyroSim;

public class DefaultSpiSimulatorFactory implements ISpiSimulatorFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(DefaultSpiSimulatorFactory.class);

    protected Map<Integer, String> mDefaults;

    public DefaultSpiSimulatorFactory()
    {
        mDefaults = new HashMap<>();
    }

    @Override
    public ISpiWrapper createSpiWrapper(int aPort)
    {
        ISpiWrapper output = null;

        if (mDefaults.containsKey(aPort))
        {
            output = createSimulator(aPort, mDefaults.get(aPort));
        }

        if (output == null)
        {
            sLOGGER.log(Level.ERROR, "Could not create simulator for SPI on port " + aPort);
        }

        return output;
    }

    @Override
    public void setDefaultWrapper(int aPort, String aType)
    {
        sLOGGER.log(Level.DEBUG, "Setting default on SPI port " + aPort + " to '" + aType + "'");
        mDefaults.put(aPort, aType);
    }

    public Collection<String> getAvailableTypes()
    {
        return Arrays.asList("NavX", "ADXRS450", "ADXL345", "ADXL362");
    }

    protected ISpiWrapper createSimulator(int aPort, String aType)
    {
        String fullType = "SPI " + aType;

        if ("NavX".equals(aType))
        {
            return new NavxSimulatorWrapper(fullType, new SpiNavxSimulator(aPort), 200 + aPort * 3);
        }
        else if ("ADXRS450".equals(aType))
        {
            return new SpiGyro(new ADXRS450_GyroSim(aPort), 100 + aPort);
        }
        else if ("ADXL345".equals(aType))
        {
            return new ADXFamily3AxisAccelerometer(fullType, new ADXL345_SpiSim(aPort), 100 + aPort * 3);
        }
        else if ("ADXL362".equals(aType))
        {
            return new ADXFamily3AxisAccelerometer(fullType, new ADXL362Sim(aPort), 150 + aPort * 3);
        }

        return null;
    }

    @Override
    public Map<Integer, String> getDefaults()
    {
        return mDefaults;
    }

}

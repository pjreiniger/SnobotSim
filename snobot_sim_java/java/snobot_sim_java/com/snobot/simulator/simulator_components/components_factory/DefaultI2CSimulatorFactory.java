package com.snobot.simulator.simulator_components.components_factory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.navx.I2CNavxSimulator;
import com.snobot.simulator.simulator_components.II2CWrapper;
import com.snobot.simulator.simulator_components.accelerometer.ADXFamily3AxisAccelerometer;
import com.snobot.simulator.simulator_components.navx.NavxSimulatorWrapper;

import edu.wpi.first.wpilibj.sim.ADXL345_I2CSim;

public class DefaultI2CSimulatorFactory implements II2cSimulatorFactory
{
    private static final Logger sLOGGER = LogManager.getLogger(DefaultI2CSimulatorFactory.class);
    protected Map<Integer, String> mDefaults;

    public DefaultI2CSimulatorFactory()
    {
        mDefaults = new HashMap<>();
    }

    @Override
    public II2CWrapper createI2CWrapper(int aPort)
    {
        II2CWrapper output = null;

        if (mDefaults.containsKey(aPort))
        {
            output = createWrapper(aPort, mDefaults.get(aPort));
        }

        if (output == null)
        {
            sLOGGER.log(Level.ERROR, "Could not create simulator for I2C on port " + aPort);
        }

        return output;
    }

    @Override
    public void setDefaultWrapper(int aPort, String aType)
    {
        sLOGGER.log(Level.DEBUG, "Setting I2C default for port " + aPort + " to " + aType);
        mDefaults.put(aPort, aType);
    }

    @Override
    public Collection<String> getAvailableTypes()
    {
        return Arrays.asList("NavX", "ADXL345");
    }

    protected II2CWrapper createWrapper(int aPort, String aType)
    {
        String fullType = "I2C " + aType;
        if ("NavX".equals(aType))
        {
            return new NavxSimulatorWrapper(fullType, new I2CNavxSimulator(aPort), 250 + aPort * 3);
        }
        if ("ADXL345".equals(aType))
        {
            return new ADXFamily3AxisAccelerometer(fullType, new ADXL345_I2CSim(aPort), 50 + aPort * 3);
        }

        return null;
    }

    @Override
    public Map<Integer, String> getDefaults()
    {
        return mDefaults;
    }

}

package com.snobot.simulator.wrapper_accessors.jni;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.snobot.simulator.jni.SimulationConnectorJni;
import com.snobot.simulator.wrapper_accessors.I2CWrapperAccessor;

public class JniI2CWrapperAccessor implements I2CWrapperAccessor
{

    @Override
    public Collection<String> getAvailableI2CSimulators()
    {
        return Arrays.asList("NavX", "ADXL345");
    }

    @Override
    public boolean createI2CSimulator(int aPort, String aType)
    {
        SimulationConnectorJni.setI2CDefault(aPort, aType);
        return true;
    }

    @Override
    public Map<Integer, String> getI2CWrapperTypes()
    {
        // TODO Auto-generated method stub
        return null;
    }
}

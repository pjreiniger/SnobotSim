package com.snobot.simulator.wrapper_accessors.jni;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.snobot.simulator.jni.SimulationConnectorJni;
import com.snobot.simulator.wrapper_accessors.SpiWrapperAccessor;

public class JniSpiWrapperAccessor implements SpiWrapperAccessor
{

    @Override
    public Collection<String> getAvailableSpiSimulators()
    {
        return Arrays.asList("NavX", "ADXRS450", "ADXL345", "ADXL362");
    }

    @Override
    public boolean createSpiSimulator(int aPort, String aType)
    {
        SimulationConnectorJni.setSpiDefault(aPort, aType);
        return true;
    }

    @Override
    public Map<Integer, String> getSpiWrapperTypes()
    {
        // TODO Auto-generated method stub
        return null;
    }
}

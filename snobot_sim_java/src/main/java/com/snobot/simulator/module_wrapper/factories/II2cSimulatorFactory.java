package com.snobot.simulator.module_wrapper.factories;

import java.util.Collection;
import java.util.Map;

public interface II2cSimulatorFactory
{
    public boolean create(int aPort, String aType);

    public Collection<String> getAvailableTypes();

    public Map<Integer, String> getI2CWrapperTypes();
}

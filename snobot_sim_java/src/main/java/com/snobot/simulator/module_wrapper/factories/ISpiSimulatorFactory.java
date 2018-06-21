package com.snobot.simulator.module_wrapper.factories;

import java.util.Collection;
import java.util.Map;

public interface ISpiSimulatorFactory
{
    public boolean create(int aPort, String aType);

    public Collection<String> getAvailableTypes();

    public Map<Integer, String> getSpiWrapperTypes();
}

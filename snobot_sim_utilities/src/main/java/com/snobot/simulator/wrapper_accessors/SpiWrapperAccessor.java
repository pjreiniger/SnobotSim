package com.snobot.simulator.wrapper_accessors;

import java.util.Collection;
import java.util.Map;

public interface SpiWrapperAccessor
{

    Map<Integer, String> getSpiWrapperTypes();

    boolean createSpiSimulator(int aPort, String aType);

    Collection<String> getAvailableSpiSimulators();
}

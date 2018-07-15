package com.snobot.simulator.wrapper_accessors;

import java.util.Collection;
import java.util.Map;

public interface I2CWrapperAccessor
{

    Map<Integer, String> getI2CWrapperTypes();

    boolean createI2CSimulator(int aPort, String aType);

    Collection<String> getAvailableI2CSimulators();
}

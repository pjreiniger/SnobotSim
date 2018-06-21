package com.snobot.simulator.simulator_components.navx;

import com.snobot.simulator.navx.SpiNavxSimulator;

public class SpiNavxSimulatorWrapper extends BaseNavxSimulatorWrapper
{
    public SpiNavxSimulatorWrapper(String aBaseName, int aPort, int aBasePort)
    {
        super(aBaseName, new SpiNavxSimulator(aPort), aBasePort);
    }
}

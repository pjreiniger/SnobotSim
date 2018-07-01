package com.snobot.simulator.simulator_components.navx;

import com.snobot.simulator.navx.SpiNavxSimulator;

public class SpiNavxSimulatorWrapper extends BaseNavxSimulatorWrapper
{
    public SpiNavxSimulatorWrapper(String aBaseName, int aPort)
    {
        super(aBaseName, new SpiNavxSimulator(aPort), 200 + aPort * 3);
    }
}

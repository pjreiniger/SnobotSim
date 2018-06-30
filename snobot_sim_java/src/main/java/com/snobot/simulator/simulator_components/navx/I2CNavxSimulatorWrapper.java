package com.snobot.simulator.simulator_components.navx;

import com.snobot.simulator.navx.I2CNavxSimulator;

public class I2CNavxSimulatorWrapper extends BaseNavxSimulatorWrapper
{
    public I2CNavxSimulatorWrapper(String aBaseName, int aPort, int aBasePort)
    {
        super(aBaseName, new I2CNavxSimulator(aPort), aBasePort);
    }

}

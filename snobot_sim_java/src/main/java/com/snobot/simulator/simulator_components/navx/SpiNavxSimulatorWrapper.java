package com.snobot.simulator.simulator_components.navx;

public class SpiNavxSimulatorWrapper extends BaseNavxSimulatorWrapper
{
    public SpiNavxSimulatorWrapper(String aBaseName, String aDeviceName, int aPort)
    {
        super(aBaseName, aDeviceName, 200 + aPort * 3);
    }
}

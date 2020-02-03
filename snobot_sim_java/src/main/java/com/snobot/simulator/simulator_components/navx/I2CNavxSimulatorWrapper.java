package com.snobot.simulator.simulator_components.navx;

public class I2CNavxSimulatorWrapper extends BaseNavxSimulatorWrapper
{
    public I2CNavxSimulatorWrapper(String aBaseName, String aDeviceName, int aPort)
    {
        super(aBaseName, aDeviceName, 250 + aPort * 3);
    }

}

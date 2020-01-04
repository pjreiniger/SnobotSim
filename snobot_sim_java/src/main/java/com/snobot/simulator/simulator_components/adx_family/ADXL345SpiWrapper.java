package com.snobot.simulator.simulator_components.adx_family;

public class ADXL345SpiWrapper extends ADXFamily3AxisAccelerometer
{

    public ADXL345SpiWrapper(String aBaseName, String aDeviceName, int aPort)
    {
        super(aBaseName, aDeviceName, 100 + aPort * 3);
    }

}

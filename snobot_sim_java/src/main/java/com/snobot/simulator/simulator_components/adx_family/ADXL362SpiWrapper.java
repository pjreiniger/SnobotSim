package com.snobot.simulator.simulator_components.adx_family;



public class ADXL362SpiWrapper extends ADXFamily3AxisAccelerometer
{

    public ADXL362SpiWrapper(String aBaseName, String aDeviceName, int aPort)
    {
        super(aBaseName, aDeviceName, 150 + aPort * 3);
    }

}

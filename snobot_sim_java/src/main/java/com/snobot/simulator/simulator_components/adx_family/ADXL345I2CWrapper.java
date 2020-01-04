package com.snobot.simulator.simulator_components.adx_family;

public class ADXL345I2CWrapper extends ADXFamily3AxisAccelerometer
{

    public ADXL345I2CWrapper(String aBaseName, String aDeviceName, int aPort)
    {
        super(aBaseName, aDeviceName, 50 + aPort * 3);
    }

}

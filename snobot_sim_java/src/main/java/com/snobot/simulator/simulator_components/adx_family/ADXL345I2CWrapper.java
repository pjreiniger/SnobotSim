package com.snobot.simulator.simulator_components.adx_family;

public class ADXL345I2CWrapper extends ADXFamily3AxisAccelerometer
{

    public ADXL345I2CWrapper(String aBaseName, int aPort)
    {
        super(aBaseName, aBaseName, 50 + aPort * 3);
    }

}

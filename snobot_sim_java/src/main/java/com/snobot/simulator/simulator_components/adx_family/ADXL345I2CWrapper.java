package com.snobot.simulator.simulator_components.adx_family;

import edu.wpi.first.wpilibj.sim.ADXL345_I2CSim;

public class ADXL345I2CWrapper extends ADXFamily3AxisAccelerometer
{

    public ADXL345I2CWrapper(String aBaseName, int aPort)
    {
        super(aBaseName, new ADXL345_I2CSim(aPort), 50 + aPort * 3);
    }

}

package com.snobot.simulator.simulator_components.adx_family;

import edu.wpi.first.wpilibj.sim.ADXL345_SpiSim;

public class ADXL345SpiWrapper extends ADXFamily3AxisAccelerometer
{

    public ADXL345SpiWrapper(String aBaseName, int aPort, int aBasePort)
    {
        super(aBaseName, new ADXL345_SpiSim(aPort), aBasePort);
    }

}

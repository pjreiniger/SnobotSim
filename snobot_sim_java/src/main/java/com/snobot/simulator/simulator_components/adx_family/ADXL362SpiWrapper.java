package com.snobot.simulator.simulator_components.adx_family;


import edu.wpi.first.wpilibj.sim.ADXL362Sim;

public class ADXL362SpiWrapper extends ADXFamily3AxisAccelerometer
{

    public ADXL362SpiWrapper(String aBaseName, int aPort)
    {
        super(aBaseName, new ADXL362Sim(aPort), 150 + aPort * 3);
    }

}

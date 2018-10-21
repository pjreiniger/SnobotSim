package com.snobot.simulator.simulator_components.adx_family;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.BaseGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.ISpiWrapper;
import edu.wpi.first.wpilibj.sim.ADXRS450_GyroSim;


public class ADXRS450GyroWrapper extends BaseGyroWrapper implements ISpiWrapper
{
    private final ADXRS450_GyroSim mWpiSim;

    public ADXRS450GyroWrapper(ADXRS450_GyroSim aWpiSim, int aPort)
    {
        super("ADXRS450 Gyro", aWpiSim::getAngle, aWpiSim::setAngle);

        mWpiSim = aWpiSim;
        SensorActuatorRegistry.get().register((IGyroWrapper) this, 100 + aPort);
    }

    @Override
    public void close() throws Exception
    {
        super.close();
        mWpiSim.close();
    }
}

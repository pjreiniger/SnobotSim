package com.snobot.simulator.simulator_components.adx_family;

import java.nio.ByteBuffer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.BaseGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.ISpiWrapper;

import edu.wpi.first.wpilibj.sim.ADXRS450_GyroSim;

public class ADXRS450GyroWrapper extends BaseGyroWrapper implements ISpiWrapper
{
    private static final Logger sLOGGER = LogManager.getLogger(ADXRS450GyroWrapper.class);

    public ADXRS450GyroWrapper(ADXRS450_GyroSim aWpiSim, int aBasePort)
    {
        super("ADXRS450 Gyro", aWpiSim::getAngle, aWpiSim::setAngle);

        SensorActuatorRegistry.get().register((IGyroWrapper) this, aBasePort);
    }

    @Override
    public void handleRead(ByteBuffer aBuffer)
    {
        sLOGGER.log(Level.ERROR, "This shouldn't be called directly");
    }

    @Override
    public void handleWrite(ByteBuffer aBuffer)
    {
        sLOGGER.log(Level.ERROR, "This shouldn't be called directly");
    }
}

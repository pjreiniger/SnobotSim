package com.snobot.simulator.simulator_components.gyro;

import java.nio.ByteBuffer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.simulator_components.ISpiWrapper;

import edu.wpi.first.wpilibj.sim.ADXRS450_GyroSim;

public class SpiGyro extends BaseGyroWrapper implements ISpiWrapper
{
    private static final Logger sLOGGER = LogManager.getLogger(SpiGyro.class);

    public SpiGyro(ADXRS450_GyroSim aWpiSim, int aBasePort)
    {
        super("", aWpiSim::getAngle, aWpiSim::setAngle);

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

    @Override
    public void shutdown()
    {
        // Nothing to do
    }
}

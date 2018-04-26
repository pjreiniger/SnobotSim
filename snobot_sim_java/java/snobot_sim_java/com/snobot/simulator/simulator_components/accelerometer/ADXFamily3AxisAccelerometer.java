package com.snobot.simulator.simulator_components.accelerometer;

import java.nio.ByteBuffer;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.simulator_components.II2CWrapper;
import com.snobot.simulator.simulator_components.ISpiWrapper;

import edu.wpi.first.wpilibj.sim.IThreeAxisAccelerometer;

public class ADXFamily3AxisAccelerometer implements II2CWrapper, ISpiWrapper
{
    public ADXFamily3AxisAccelerometer(String aBaseName, IThreeAxisAccelerometer aWpiAccel, int aBasePort)
    {
        IAccelerometerWrapper xWrapper = new BaseAccelerometerWrapper(aBaseName + " X Accel", aWpiAccel::getX, aWpiAccel::setX);
        IAccelerometerWrapper yWrapper = new BaseAccelerometerWrapper(aBaseName + " Y Accel", aWpiAccel::getY, aWpiAccel::setY);
        IAccelerometerWrapper zWrapper = new BaseAccelerometerWrapper(aBaseName + " Z Accel", aWpiAccel::getZ, aWpiAccel::setZ);

        SensorActuatorRegistry.get().register(xWrapper, aBasePort + 0);
        SensorActuatorRegistry.get().register(yWrapper, aBasePort + 1);
        SensorActuatorRegistry.get().register(zWrapper, aBasePort + 2);
    }

    @Override
    public void shutdown()
    {
        // Nothing to do
    }

    @Override
    public void handleRead(ByteBuffer aBuffer)
    {
        // Nothing to do
    }

    @Override
    public void handleWrite(ByteBuffer aBuffer)
    {
        // Nothing to do
    }

}

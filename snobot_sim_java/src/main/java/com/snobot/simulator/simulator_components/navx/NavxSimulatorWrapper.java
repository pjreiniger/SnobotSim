package com.snobot.simulator.simulator_components.navx;

import java.nio.ByteBuffer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.navx.INavxSimulator;
import com.snobot.simulator.simulator_components.II2CWrapper;
import com.snobot.simulator.simulator_components.ISpiWrapper;
import com.snobot.simulator.simulator_components.accelerometer.BaseAccelerometerWrapper;
import com.snobot.simulator.simulator_components.accelerometer.IAccelerometerWrapper;
import com.snobot.simulator.simulator_components.gyro.BaseGyroWrapper;
import com.snobot.simulator.simulator_components.gyro.IGyroWrapper;

public class NavxSimulatorWrapper implements ISpiWrapper, II2CWrapper
{
    private static final Logger sLOGGER = LogManager.getLogger(NavxSimulatorWrapper.class);

    public NavxSimulatorWrapper(String aBaseName, INavxSimulator aNavxWrapper, int aBasePort)
    {
        IAccelerometerWrapper xWrapper = new BaseAccelerometerWrapper(aBaseName + " X Accel", aNavxWrapper::getXAccel, aNavxWrapper::setXAccel);
        IAccelerometerWrapper yWrapper = new BaseAccelerometerWrapper(aBaseName + " Y Accel", aNavxWrapper::getYAccel, aNavxWrapper::setYAccel);
        IAccelerometerWrapper zWrapper = new BaseAccelerometerWrapper(aBaseName + " Z Accel", aNavxWrapper::getZAccel, aNavxWrapper::setZAccel);
        IGyroWrapper yawWrapper = new BaseGyroWrapper(aBaseName + " Yaw", aNavxWrapper::getYaw, aNavxWrapper::setYaw);
        IGyroWrapper pitchWrapper = new BaseGyroWrapper(aBaseName + " Pitch", aNavxWrapper::getPitch, aNavxWrapper::setPitch);
        IGyroWrapper rollWrapper = new BaseGyroWrapper(aBaseName + " Roll", aNavxWrapper::getRoll, aNavxWrapper::setRoll);

        SensorActuatorRegistry.get().register(xWrapper, aBasePort + 0);
        SensorActuatorRegistry.get().register(yWrapper, aBasePort + 1);
        SensorActuatorRegistry.get().register(zWrapper, aBasePort + 2);

        SensorActuatorRegistry.get().register(yawWrapper, aBasePort + 0);
        SensorActuatorRegistry.get().register(pitchWrapper, aBasePort + 1);
        SensorActuatorRegistry.get().register(rollWrapper, aBasePort + 2);
    }

    @Override
    public void shutdown()
    {
        // Nothing to do
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
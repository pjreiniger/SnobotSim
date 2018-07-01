package com.snobot.simulator.simulator_components.navx;

import java.io.IOException;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.ASensorWrapper;
import com.snobot.simulator.module_wrapper.BaseAccelerometerWrapper;
import com.snobot.simulator.module_wrapper.BaseGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IAccelerometerWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.II2CWrapper;
import com.snobot.simulator.module_wrapper.interfaces.ISpiWrapper;
import com.snobot.simulator.navx.INavxSimulator;

public abstract class BaseNavxSimulatorWrapper extends ASensorWrapper implements ISpiWrapper, II2CWrapper
{
    protected final INavxSimulator mNavxWrapper;
    protected final IAccelerometerWrapper mXWrapper;
    protected final IAccelerometerWrapper mYWrapper;
    protected final IAccelerometerWrapper mZWrapper;
    protected final IGyroWrapper mYawWrapper;
    protected final IGyroWrapper mPitchWrapper;
    protected final IGyroWrapper mRollWrapper;

    public BaseNavxSimulatorWrapper(String aBaseName, INavxSimulator aNavxWrapper, int aBasePort)
    {
        super("NotUsed");

        mNavxWrapper = aNavxWrapper;

        mXWrapper = new BaseAccelerometerWrapper(aBaseName + " X Accel", aNavxWrapper::getXAccel, aNavxWrapper::setXAccel);
        mYWrapper = new BaseAccelerometerWrapper(aBaseName + " Y Accel", aNavxWrapper::getYAccel, aNavxWrapper::setYAccel);
        mZWrapper = new BaseAccelerometerWrapper(aBaseName + " Z Accel", aNavxWrapper::getZAccel, aNavxWrapper::setZAccel);
        mYawWrapper = new BaseGyroWrapper(aBaseName + " Yaw", aNavxWrapper::getYaw, aNavxWrapper::setYaw);
        mPitchWrapper = new BaseGyroWrapper(aBaseName + " Pitch", aNavxWrapper::getPitch, aNavxWrapper::setPitch);
        mRollWrapper = new BaseGyroWrapper(aBaseName + " Roll", aNavxWrapper::getRoll, aNavxWrapper::setRoll);

        SensorActuatorRegistry.get().register(mXWrapper, aBasePort + 0);
        SensorActuatorRegistry.get().register(mYWrapper, aBasePort + 1);
        SensorActuatorRegistry.get().register(mZWrapper, aBasePort + 2);

        SensorActuatorRegistry.get().register(mYawWrapper, aBasePort + 0);
        SensorActuatorRegistry.get().register(mPitchWrapper, aBasePort + 1);
        SensorActuatorRegistry.get().register(mRollWrapper, aBasePort + 2);
    }

    @Override
    public void setInitialized(boolean aInitialized)
    {
        super.setInitialized(aInitialized);

        mXWrapper.setInitialized(aInitialized);
        mYWrapper.setInitialized(aInitialized);
        mZWrapper.setInitialized(aInitialized);

        mYawWrapper.setInitialized(aInitialized);
        mPitchWrapper.setInitialized(aInitialized);
        mRollWrapper.setInitialized(aInitialized);
    }

    @Override
    public void close() throws IOException
    {
        mNavxWrapper.close();
    }
}

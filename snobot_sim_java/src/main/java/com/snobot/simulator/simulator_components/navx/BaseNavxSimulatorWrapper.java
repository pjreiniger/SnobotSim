package com.snobot.simulator.simulator_components.navx;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.ASensorWrapper;
import com.snobot.simulator.module_wrapper.BaseAccelerometerWrapper;
import com.snobot.simulator.module_wrapper.BaseGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IAccelerometerWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.II2CWrapper;
import com.snobot.simulator.module_wrapper.interfaces.ISpiWrapper;
import com.snobot.simulator.simulator_components.LazySimDoubleWrapper;

public abstract class BaseNavxSimulatorWrapper extends ASensorWrapper implements ISpiWrapper, II2CWrapper
{
    protected final IAccelerometerWrapper mXWrapper;
    protected final IAccelerometerWrapper mYWrapper;
    protected final IAccelerometerWrapper mZWrapper;
    protected final IGyroWrapper mYawWrapper;
    protected final IGyroWrapper mPitchWrapper;
    protected final IGyroWrapper mRollWrapper;

    public BaseNavxSimulatorWrapper(String aBaseName, String aDeviceName, int aBasePort)
    {
        super("NotUsed");


        LazySimDoubleWrapper xWrapper = new LazySimDoubleWrapper(aDeviceName, "X Accel");
        LazySimDoubleWrapper yWrapper = new LazySimDoubleWrapper(aDeviceName, "Y Accel");
        LazySimDoubleWrapper zWrapper = new LazySimDoubleWrapper(aDeviceName, "Z Accel");

        LazySimDoubleWrapper yawWrapper = new LazySimDoubleWrapper(aDeviceName, "Yaw");
        LazySimDoubleWrapper pitchWrapper = new LazySimDoubleWrapper(aDeviceName, "Pitch");
        LazySimDoubleWrapper rollWrapper = new LazySimDoubleWrapper(aDeviceName, "Roll");

        mXWrapper = new BaseAccelerometerWrapper(aBaseName + " X Accel", xWrapper::get, xWrapper::set);
        mYWrapper = new BaseAccelerometerWrapper(aBaseName + " Y Accel", yWrapper::get, yWrapper::set);
        mZWrapper = new BaseAccelerometerWrapper(aBaseName + " Z Accel", zWrapper::get, zWrapper::set);
        mYawWrapper = new BaseGyroWrapper(aBaseName + " Yaw", yawWrapper::get, yawWrapper::set);
        mPitchWrapper = new BaseGyroWrapper(aBaseName + " Pitch", pitchWrapper::get, pitchWrapper::set);
        mRollWrapper = new BaseGyroWrapper(aBaseName + " Roll", rollWrapper::get, rollWrapper::set);

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
}

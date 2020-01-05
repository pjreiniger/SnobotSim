package com.snobot.simulator.simulator_components.adx_family;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.ASensorWrapper;
import com.snobot.simulator.module_wrapper.BaseAccelerometerWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IAccelerometerWrapper;
import com.snobot.simulator.module_wrapper.interfaces.II2CWrapper;
import com.snobot.simulator.module_wrapper.interfaces.ISpiWrapper;
import com.snobot.simulator.simulator_components.LazySimDoubleWrapper;

public abstract class ADXFamily3AxisAccelerometer extends ASensorWrapper implements II2CWrapper, ISpiWrapper
{
    protected final IAccelerometerWrapper mXWrapper;
    protected final IAccelerometerWrapper mYWrapper;
    protected final IAccelerometerWrapper mZWrapper;

    public ADXFamily3AxisAccelerometer(String aBaseName, String aDeviceName, int aBasePort)
    {
        super("NotUsed");

        LazySimDoubleWrapper xWrapper = new LazySimDoubleWrapper(aDeviceName, "X Accel");
        LazySimDoubleWrapper yWrapper = new LazySimDoubleWrapper(aDeviceName, "Y Accel");
        LazySimDoubleWrapper zWrapper = new LazySimDoubleWrapper(aDeviceName, "Z Accel");

        mXWrapper = new BaseAccelerometerWrapper(aBaseName + " X Accel", xWrapper::get, xWrapper::set);
        mYWrapper = new BaseAccelerometerWrapper(aBaseName + " Y Accel", yWrapper::get, yWrapper::set);
        mZWrapper = new BaseAccelerometerWrapper(aBaseName + " Z Accel", zWrapper::get, zWrapper::set);

        SensorActuatorRegistry.get().register(mXWrapper, aBasePort + 0);
        SensorActuatorRegistry.get().register(mYWrapper, aBasePort + 1);
        SensorActuatorRegistry.get().register(mZWrapper, aBasePort + 2);
    }

    @Override
    public void setInitialized(boolean aInitialized)
    {
        super.setInitialized(aInitialized);

        mXWrapper.setInitialized(aInitialized);
        mYWrapper.setInitialized(aInitialized);
        mZWrapper.setInitialized(aInitialized);
    }
}

package com.snobot.simulator.simulator_components.adx_family;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.ASensorWrapper;
import com.snobot.simulator.module_wrapper.BaseAccelerometerWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IAccelerometerWrapper;
import com.snobot.simulator.module_wrapper.interfaces.II2CWrapper;
import com.snobot.simulator.module_wrapper.interfaces.ISpiWrapper;

import edu.wpi.first.wpilibj.sim.IThreeAxisAccelerometer;

public abstract class ADXFamily3AxisAccelerometer extends ASensorWrapper implements II2CWrapper, ISpiWrapper
{
    protected final IAccelerometerWrapper mXWrapper;
    protected final IAccelerometerWrapper mYWrapper;
    protected final IAccelerometerWrapper mZWrapper;
    protected final IThreeAxisAccelerometer mWpiAccel;

    public ADXFamily3AxisAccelerometer(String aBaseName, IThreeAxisAccelerometer aWpiAccel, int aBasePort)
    {
        super("NotUsed");

        mWpiAccel = aWpiAccel;
        mXWrapper = new BaseAccelerometerWrapper(aBaseName + " X Accel", aWpiAccel::getX, aWpiAccel::setX);
        mYWrapper = new BaseAccelerometerWrapper(aBaseName + " Y Accel", aWpiAccel::getY, aWpiAccel::setY);
        mZWrapper = new BaseAccelerometerWrapper(aBaseName + " Z Accel", aWpiAccel::getZ, aWpiAccel::setZ);

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

    @Override
    public void close() throws Exception
    {
        super.close();
        mWpiAccel.close();
    }
}

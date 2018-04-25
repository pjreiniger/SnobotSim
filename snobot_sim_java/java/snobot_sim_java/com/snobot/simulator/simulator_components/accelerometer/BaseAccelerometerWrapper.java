package com.snobot.simulator.simulator_components.accelerometer;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.snobot.simulator.module_wrapper.ASensorWrapper;

public class BaseAccelerometerWrapper extends ASensorWrapper implements IAccelerometerWrapper
{
    private final Supplier<Double> mGetter;
    private final Consumer<Double> mSetter;

    public BaseAccelerometerWrapper(String aExtraName, Supplier<Double> aGetter, Consumer<Double> aSetter)
    {
        super(aExtraName);

        mGetter = aGetter;
        mSetter = aSetter;
    }

    @Override
    public void setAcceleration(double aAcceleration)
    {
        mSetter.accept(aAcceleration);
    }

    @Override
    public double getAcceleration()
    {
        return mGetter.get();
    }

}

package com.snobot.simulator.module_wrapper;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.snobot.simulator.module_wrapper.interfaces.IAccelerometerWrapper;

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

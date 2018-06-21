package com.snobot.simulator.module_wrapper;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;

public class BaseGyroWrapper extends ASensorWrapper implements IGyroWrapper
{
    private final Supplier<Double> mGetter;
    private final Consumer<Double> mSetter;

    public BaseGyroWrapper(String aExtraName, Supplier<Double> aGetter, Consumer<Double> aSetter)
    {
        super(aExtraName);

        mGetter = aGetter;
        mSetter = aSetter;
    }

    @Override
    public double getAngle()
    {
        return mGetter.get();
    }

    @Override
    public void setAngle(double aAngle)
    {
        mSetter.accept(aAngle);
    }

}

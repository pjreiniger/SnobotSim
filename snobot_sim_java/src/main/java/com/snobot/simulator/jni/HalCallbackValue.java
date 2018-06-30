package com.snobot.simulator.jni;

public class HalCallbackValue
{
    public int mType;
    public boolean mBoolean;
    public int mInt;
    public long mLong;
    public double mDouble;

    public HalCallbackValue(
            int aType,
            boolean aBoolean,
            int aInt,
            long aLong,
            double aDouble)
    {
        this.mType = aType;
        this.mBoolean = aBoolean;
        this.mInt = aInt;
        this.mLong = aLong;
        this.mDouble = aDouble;
    }

    @Override
    public String toString()
    {
        return "HalCallbackValue [mType=" + mType + ", mBoolean=" + mBoolean + ", mInt=" + mInt + ", mLong=" + mLong + ", mDouble=" + mDouble + "]";
    }


}

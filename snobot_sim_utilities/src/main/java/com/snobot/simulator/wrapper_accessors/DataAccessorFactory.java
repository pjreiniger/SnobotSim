package com.snobot.simulator.wrapper_accessors;

public final class DataAccessorFactory
{
    private static IDataAccessor sInstance = null;

    private DataAccessorFactory()
    {

    }

    public static void setAccessor(IDataAccessor aAccessor)
    {
        sInstance = aAccessor;
    }

    public static IDataAccessor getInstance()
    {
        return sInstance;
    }
}

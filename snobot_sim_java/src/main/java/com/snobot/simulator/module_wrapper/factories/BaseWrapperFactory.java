package com.snobot.simulator.module_wrapper.factories;

import java.util.Map;

public abstract class BaseWrapperFactory
{
    public abstract boolean create(int aPort, String aType, boolean aIsStartup);

    protected <T> void logIfMissing(boolean aIsStartup, Map<Integer, T> aMap, int aPort, Class<?> aClazz, String aErrorMessage)
    {
        // if (!aIsStartup && aMap.containsKey(aPort))
        // {
        // System.out.println(aErrorMessage);
        // }
    }
}

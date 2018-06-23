package com.snobot.simulator.module_wrapper.factories;

public abstract class BaseWrapperFactory
{
    public abstract boolean create(int aPort, String aType, boolean aIsStartup);
}

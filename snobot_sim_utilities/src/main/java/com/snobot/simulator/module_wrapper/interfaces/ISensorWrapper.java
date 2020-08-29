package com.snobot.simulator.module_wrapper.interfaces;

public interface ISensorWrapper extends AutoCloseable
{

    boolean isInitialized();

    void setInitialized(boolean aInitialized);

    String getName();

    void setName(String aName);

    boolean getWantsHidden();

    void setWantsHidden(boolean aVisible);

    String getType();
}

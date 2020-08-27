package com.snobot.simulator.module_wrapper.interfaces;

public interface ISolenoidWrapper extends ISensorWrapper
{

    void set(boolean aState);

    boolean get();

}

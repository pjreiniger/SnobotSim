package com.snobot.simulator.module_wrapper.interfaces;

public interface IDigitalIoWrapper extends ISensorWrapper
{

    boolean get();

    void set(boolean aState);

}

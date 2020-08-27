package com.snobot.simulator.module_wrapper.interfaces;

public interface IGyroWrapper extends ISensorWrapper
{
    double getAngle();

    void setAngle(double aAngle);
}

package com.snobot.simulator.module_wrapper.interfaces;

public interface IAnalogInWrapper extends ISensorWrapper
{

    void setVoltage(double aVoltage);

    double getVoltage();
}

package com.snobot.simulator.module_wrapper.interfaces;

public interface IAnalogOutWrapper extends ISensorWrapper
{
    void setVoltage(double aVoltage);

    double getVoltage();
}

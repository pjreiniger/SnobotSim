package com.snobot.simulator.module_wrapper.interfaces;

public interface IPwmWrapper extends ISensorWrapper
{

    IMotorSimulator getMotorSimulator();

    void setMotorSimulator(IMotorSimulator aSimulator);

    void reset(double aPosition, double aVelocity, double aCurrent);

    void reset();

    double getPosition();

    double getVelocity();

    double getAcceleration();

    double getCurrent();

    void setVoltagePercentage(double aSpeed);

    double getVoltagePercentage();

    void update(double aWaitTime);

    void setFeedbackSensor(IMotorFeedbackSensor aFeedbackSensor);

    IMotorFeedbackSensor getFeedbackSensor();

    int getHandle();

}

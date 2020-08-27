package com.snobot.simulator.module_wrapper.interfaces;

public interface IMotorFeedbackSensor
{
    void setPosition(double aPosition);

    void setVelocity(double aVelocity);

    double getPosition();

    double getVelocity();

    public static class NullFeedbackSensor implements IMotorFeedbackSensor
    {
        @Override
        public double getPosition()
        {
            return 0;
        }

        @Override
        public double getVelocity()
        {
            return 0;
        }

        @Override
        public void setVelocity(double aVelocity)
        {
            // Nothing to do
        }

        @Override
        public void setPosition(double aPosition)
        {
            // Nothing to do
        }
    }

}

package com.snobot.simulator.module_wrapper.interfaces;

public interface IMotorSimulator
{

    void setVoltagePercentage(double aSpeed);

    double getVoltagePercentage();

    double getAcceleration();

    double getVelocity();

    double getCurrent();

    double getPosition();

    void reset();

    void reset(double aPosition, double aVelocity, double aCurrent);

    void update(double aCycleTime);

    public class NullMotorSimulator implements IMotorSimulator
    {
        private double mSpeed;

        @Override
        public void setVoltagePercentage(double aSpeed)
        {
            mSpeed = aSpeed;
        }

        @Override
        public double getVoltagePercentage()
        {
            return mSpeed;
        }

        @Override
        public double getAcceleration()
        {
            return 0;
        }

        @Override
        public double getVelocity()
        {
            return 0;
        }

        @Override
        public double getPosition()
        {
            return 0;
        }

        @Override
        public double getCurrent()
        {
            return 0;
        }

        @Override
        public void reset()
        {
            // Nothing to do
        }

        @Override
        public void reset(double aPosition, double aVelocity, double aCurrent)
        {
            // Nothing to do
        }

        @Override
        public void update(double aCycleTime)
        {
            // Nothing to do
        }

    }
}

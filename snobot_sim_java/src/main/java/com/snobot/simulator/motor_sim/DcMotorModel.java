package com.snobot.simulator.motor_sim;

//https://github.com/Team254/Sim-FRC-2015/blob/master/src/com/team254/frc2015/sim/DCMotor.java
public class DcMotorModel
{
    protected final DcMotorModelConfig mConfig;

    // Current motor state
    protected double mPosition;
    protected double mVelocity;
    protected double mAcceleration;
    protected double mCurrent;


    public DcMotorModel(DcMotorModelConfig aMotorConfig)
    {
        mConfig = aMotorConfig;
    }

    /**
     * Reset the motor to a specified state.
     *
     * @param aPosition
     *            The new position
     * @param aVelocity
     *            The new velocity
     * @param aCurrent
     *            The new current
     */
    public void reset(double aPosition, double aVelocity, double aCurrent)
    {
        mPosition = aPosition;
        mVelocity = aVelocity;
        mCurrent = aCurrent;
        mAcceleration = 0;
    }

    /**
     * Simulate applying a given voltage and load for a specified period of
     * time.
     *
     * @param aLoad
     *            Load applied to the motor (kg*m^2)
     * @param aTimestep
     *            How long the input is applied (s)
     */
    public void step(double aAppliedVoltage, double aLoad, double aExternalTorque, double aTimestep) // NOPMD
    {
        if (mConfig.mFactoryParams.ismInverted())
        {
            aAppliedVoltage *= -1;
        }

        /*
         * Using the 971-style first order system model. V = I * R + Kv * w
         * torque = Kt * I
         *
         * V = torque / Kt * R + Kv * w torque = J * dw/dt + external_torque
         *
         * dw/dt = (V - Kv * w) * Kt / (R * J) - external_torque / J
         */

        if (mConfig.mFactoryParams.ismHasBrake() && aAppliedVoltage == 0)
        {
            mAcceleration = 0;
            mVelocity = 0;
            mCurrent = 0;
        }
        else
        {
            aLoad += mConfig.mMotorParams.mMortorInertia;
            mAcceleration = (aAppliedVoltage - mVelocity / mConfig.mMotorParams.mKV) * mConfig.mMotorParams.mKT
                    / (mConfig.mMotorParams.mResistance * aLoad) + aExternalTorque / aLoad;
            mVelocity += mAcceleration * aTimestep;
            mPosition += mVelocity * aTimestep + .5 * mAcceleration * aTimestep * aTimestep;
            mCurrent = aLoad * mAcceleration * Math.signum(aAppliedVoltage) / mConfig.mMotorParams.mKT;
        }
    }

    public double getPosition()
    {
        return mPosition;
    }

    public double getVelocity()
    {
        return mVelocity;
    }

    public double getCurrent()
    {
        return mCurrent;
    }

    public double getAcceleration()
    {
        return mAcceleration;
    }

    public DcMotorModelConfig getMotorConfig()
    {
        return mConfig;
    }
}

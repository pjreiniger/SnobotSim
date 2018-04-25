package com.snobot.simulator.motor_sim;

public class DcMotorModelConfig
{
    public static class FactoryParams
    {
        public String mMotorType;
        public int mNumMotors;
        public double mGearReduction;
        public double mGearboxEfficiency;

        // Indicates you want a positive voltage to move the system backwards
        // instead of forwards
        private boolean mInverted;

        // Indicates the motor has a brake, i.e. when givin 0 volts it will stay
        // put
        private boolean mHasBrake;

        @SuppressWarnings("unused")
        private FactoryParams()
        {
            this("", 0, 0, 0, false, false);
        }

        public FactoryParams(String aMotorType, int aNumMotors, double aGearReduction, double aGearboxEfficiency, boolean aInverted,
                boolean aHasBrake)
        {
            mMotorType = aMotorType;
            mNumMotors = aNumMotors;
            mGearReduction = aGearReduction;
            mGearboxEfficiency = aGearboxEfficiency;

            mHasBrake = aHasBrake;
            mInverted = aInverted;
        }

        public String getmMotorType()
        {
            return mMotorType;
        }

        public int getmNumMotors()
        {
            return mNumMotors;
        }

        public double getmGearReduction()
        {
            return mGearReduction;
        }

        public double getmGearboxEfficiency()
        {
            return mGearboxEfficiency;
        }

        public boolean ismInverted()
        {
            return mInverted;
        }

        public void setmInverted(boolean aInverted)
        {
            this.mInverted = aInverted;
        }

        public void setmMotorType(String aMotorType)
        {
            this.mMotorType = aMotorType;
        }

        public void setmNumMotors(int aNumMotors)
        {
            this.mNumMotors = aNumMotors;
        }

        public void setmGearReduction(double aGearReduction)
        {
            this.mGearReduction = aGearReduction;
        }

        public void setmGearboxEfficiency(double aGearboxEfficiency)
        {
            this.mGearboxEfficiency = aGearboxEfficiency;
        }

        public void setInverted(boolean aInverted)
        {
            mInverted = aInverted;
        }

        public void setHasBrake(boolean aHasBrake)
        {
            mHasBrake = aHasBrake;
        }

        public boolean ismHasBrake()
        {
            return mHasBrake;
        }

        public void setmHasBrake(boolean aHasBrake)
        {
            this.mHasBrake = aHasBrake;
        }

        @Override
        public String toString()
        {
            return "FactoryParams [mMotorType=" + mMotorType + ", mNumMotors=" + mNumMotors + ", mGearReduction=" + mGearReduction
                    + ", mGearboxEfficiency=" + mGearboxEfficiency + "]";
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            long temp;
            temp = Double.doubleToLongBits(mGearReduction);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(mGearboxEfficiency);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            result = prime * result + ((mMotorType == null) ? 0 : mMotorType.hashCode());
            result = prime * result + mNumMotors;
            return result;
        }

        @Override
        public boolean equals(Object aObj)
        {
            if (this == aObj)
            {
                return true;
            }
            if (aObj == null)
            {
                return false;
            }
            if (getClass() != aObj.getClass())
            {
                return false;
            }
            FactoryParams other = (FactoryParams) aObj;
            if (Double.doubleToLongBits(mGearReduction) != Double.doubleToLongBits(other.mGearReduction))
            {
                return false;
            }
            if (Double.doubleToLongBits(mGearboxEfficiency) != Double.doubleToLongBits(other.mGearboxEfficiency))
            {
                return false;
            }
            if (mMotorType == null)
            {
                if (other.mMotorType != null)
                {
                    return false;
                }
            }
            else if (!mMotorType.equals(other.mMotorType))
            {
                return false;
            }
            if (mNumMotors != other.mNumMotors) // NOPMD
            {
                return false;
            }
            return true;
        }

    }

    public static class MotorParams
    {
        public final double mNominalVoltage;
        public final double mFreeSpeedRpm;
        public final double mFreeCurrent;
        public final double mStallTorque;
        public final double mStallCurrent;
        public final double mMortorInertia;
        public final double mKT;
        public final double mKV;
        public final double mResistance;

        public MotorParams(double aNominalVoltage, double aFreeSpeedRpm, double aFreeCurrent, double aStallTorque, double aStallCurrent)
        {
            this(aNominalVoltage, aFreeSpeedRpm, aFreeCurrent, aStallTorque, aStallCurrent, 0);
        }

        public MotorParams(double aNominalVoltage, double aFreeSpeedRpm, double aFreeCurrent, double aStallTorque, double aStallCurrent,
                double aMotorInertia)
        {
            this(aNominalVoltage, aFreeSpeedRpm, aFreeCurrent, aStallTorque, aStallCurrent, aMotorInertia, 1);
        }

        public MotorParams(
                double aNominalVoltage,
                double aFreeSpeedRpm,
                double aFreeCurrent,
                double aStallTorque,
                double aStallCurrent,
                double aMotorInertia,
                double aKtScaler)
        {
            this(aNominalVoltage, aFreeSpeedRpm, aFreeCurrent, aStallTorque, aStallCurrent,
                    aMotorInertia,
                    (aStallTorque / aStallCurrent) * aKtScaler,
                    (aFreeSpeedRpm / aNominalVoltage) * (Math.PI * 2.0) / 60.0,
                    aNominalVoltage / aStallCurrent);
        }

        public MotorParams(
                double aNominalVoltage,
                double aFreeSpeedRpm,
                double aFreeCurrent,
                double aStallTorque,
                double aStallCurrent,
                double aMotorInertia,
                double aKt,
                double aKv,
                double aResistance)
        {
            mNominalVoltage = aNominalVoltage;
            mFreeSpeedRpm = aFreeSpeedRpm;
            mFreeCurrent = aFreeCurrent;
            mStallTorque = aStallTorque;
            mStallCurrent = aStallCurrent;
            mMortorInertia = aMotorInertia;

            mKT = aKt;
            mKV = aKv;
            mResistance = aResistance;
        }

        public double calculateKt()
        {
            return mStallTorque / mStallCurrent;
        }

        public double calculateKv()
        {
            return (mFreeSpeedRpm / mNominalVoltage) * (Math.PI * 2.0) / 60.0;
        }

        public double calculateResistance()
        {
            return mNominalVoltage / mStallCurrent;
        }

        @Override
        public String toString()
        {
            return "MotorParams [NOMINAL_VOLTAGE=" + mNominalVoltage + ", FREE_SPEED_RPM=" + mFreeSpeedRpm + ", FREE_CURRENT=" + mFreeCurrent
                    + ", STALL_TORQUE=" + mStallTorque + ", STALL_CURRENT=" + mStallCurrent + ", MOTOR_INERTIA=" + mMortorInertia + ", mKT=" + mKT
                    + ", mKV=" + mKV + ", mResistance=" + mResistance + "]";
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            long temp;
            temp = Double.doubleToLongBits(mFreeCurrent);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(mFreeSpeedRpm);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(mMortorInertia);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(mNominalVoltage);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(mStallCurrent);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(mStallTorque);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(mKT);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(mKV);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(mResistance);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object aObj)
        {
            if (this == aObj)
            {
                return true;
            }
            if (aObj == null)
            {
                return false;
            }
            if (getClass() != aObj.getClass())
            {
                return false;
            }
            MotorParams other = (MotorParams) aObj;
            if (Double.doubleToLongBits(mFreeCurrent) != Double.doubleToLongBits(other.mFreeCurrent))
            {
                return false;
            }
            if (Double.doubleToLongBits(mFreeSpeedRpm) != Double.doubleToLongBits(other.mFreeSpeedRpm))
            {
                return false;
            }
            if (Double.doubleToLongBits(mMortorInertia) != Double.doubleToLongBits(other.mMortorInertia))
            {
                return false;
            }
            if (Double.doubleToLongBits(mNominalVoltage) != Double.doubleToLongBits(other.mNominalVoltage))
            {
                return false;
            }
            if (Double.doubleToLongBits(mStallCurrent) != Double.doubleToLongBits(other.mStallCurrent))
            {
                return false;
            }
            if (Double.doubleToLongBits(mStallTorque) != Double.doubleToLongBits(other.mStallTorque))
            {
                return false;
            }
            if (Double.doubleToLongBits(mKT) != Double.doubleToLongBits(other.mKT))
            {
                return false;
            }
            if (Double.doubleToLongBits(mKV) != Double.doubleToLongBits(other.mKV))
            {
                return false;
            }
            if (Double.doubleToLongBits(mResistance) != Double.doubleToLongBits(other.mResistance)) // NOPMD
            {
                return false;
            }
            return true;
        }

    }

    public final FactoryParams mFactoryParams;
    public final MotorParams mMotorParams;

    public DcMotorModelConfig(
            FactoryParams aFactoryParams,
            MotorParams aMotorParams)
    {
        mFactoryParams = aFactoryParams;
        mMotorParams = aMotorParams;
    }

    @Override
    public String toString()
    {
        return "DcMotorModelConfig [mFactoryParams=" + mFactoryParams + ", mMotorParams=" + mMotorParams + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mFactoryParams == null) ? 0 : mFactoryParams.hashCode());
        result = prime * result + ((mMotorParams == null) ? 0 : mMotorParams.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object aObj)
    {
        if (this == aObj)
        {
            return true;
        }
        if (aObj == null)
        {
            return false;
        }
        if (getClass() != aObj.getClass())
        {
            return false;
        }
        DcMotorModelConfig other = (DcMotorModelConfig) aObj;
        if (mFactoryParams == null)
        {
            if (other.mFactoryParams != null)
            {
                return false;
            }
        }
        else if (!mFactoryParams.equals(other.mFactoryParams))
        {
            return false;
        }
        if (mMotorParams == null)
        {
            if (other.mMotorParams != null)
            {
                return false;
            }
        }
        else if (!mMotorParams.equals(other.mMotorParams))
        {
            return false;
        }
        return true;
    }


}

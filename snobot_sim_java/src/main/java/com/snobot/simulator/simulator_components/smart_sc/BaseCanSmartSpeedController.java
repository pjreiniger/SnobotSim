package com.snobot.simulator.simulator_components.smart_sc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.module_wrapper.BasePwmWrapper;

@SuppressWarnings("PMD.AvoidReassigningParameters")
public abstract class BaseCanSmartSpeedController extends BasePwmWrapper
{
    private static final Logger sLOGGER = LogManager.getLogger(BaseCanSmartSpeedController.class);

    public static final int sCAN_SC_OFFSET = 100;

    protected static class PIDFConstants
    {
        public double mP;
        public double mI;
        public double mD;
        public double mF;
        public double mIZone;

        public PIDFConstants()
        {
            mP = 0;
            mI = 0;
            mD = 0;
            mF = 0;
            mIZone = 0;
        }
    }

    public enum ControlType
    {
        Raw, Position, Speed, MotionMagic, MotionProfile
    }

    public enum FeedbackDevice
    {
        QuadEncoder, Encoder, Analog
    }

    protected final int mCanHandle; // The handle without the offset applied

    protected ControlType mControlType;
    protected boolean mInverted;

    // Feedback control
    protected PIDFConstants[] mPidConstants;
    protected int mCurrentPidProfile;
    protected FeedbackDevice mFeedbackDevice;
    protected double mControlGoal;
    protected double mSumError;
    protected double mLastError;

    // Motion Magic
    protected double mMotionMagicMaxAcceleration;
    protected double mMotionMagicMaxVelocity;

    protected List<BaseCanSmartSpeedController> mFollowers;

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public BaseCanSmartSpeedController(int aCanHandle, String aBaseName, int aPidSlots)
    {
        super(aCanHandle, aBaseName + " SC " + (aCanHandle - sCAN_SC_OFFSET));

        mCanHandle = aCanHandle - sCAN_SC_OFFSET;

        mPidConstants = new PIDFConstants[aPidSlots];
        for (int i = 0; i < aPidSlots; ++i)
        {
            mPidConstants[i] = new PIDFConstants();
        }

        mControlType = ControlType.Raw;
        mFollowers = new ArrayList<>();
    }

    public void setInverted(boolean aInverted)
    {
        this.mInverted = aInverted;
    }

    public void setPGain(int aSlot, double aP)
    {
        mPidConstants[aSlot].mP = aP;
    }

    public void setIGain(int aSlot, double aI)
    {
        mPidConstants[aSlot].mI = aI;
    }

    public void setDGain(int aSlot, double aD)
    {
        mPidConstants[aSlot].mD = aD;
    }

    public void setFGain(int aSlot, double aF)
    {
        mPidConstants[aSlot].mF = aF;
    }

    public void setIZone(int aSlot, double aIzone)
    {
        mPidConstants[aSlot].mIZone = aIzone;
    }

    public PIDFConstants getPidConstants(int aSlot)
    {
        return mPidConstants[aSlot];
    }

    public void setPositionGoal(double aDemand)
    {
        if (mInverted)
        {
            aDemand = -aDemand;
        }

        double position = aDemand / getPositionUnitConversion();

        mControlType = ControlType.Position;
        mControlGoal = position;
    }

    public void setSpeedGoal(double aSpeed)
    {
        if (mInverted)
        {
            aSpeed = -aSpeed;
        }

        mControlType = ControlType.Speed;
        mControlGoal = aSpeed;
    }

    public void setRawGoal(double aRawOutput)
    {
        mControlType = ControlType.Raw;
        set(aRawOutput);
    }

    public void setMotionMagicGoal(double aDemand)
    {
        if (mInverted)
        {
            aDemand = -aDemand;
        }

        double goal = aDemand / getPositionUnitConversion();

        mControlType = ControlType.MotionMagic;
        mControlGoal = goal;
    }

    @Override
    public void update(double aWaitTime)
    {
        switch (mControlType)
        {
        case Position:
        {
            double output = calculateFeedbackOutput(getPosition(), mControlGoal);
            super.set(output);
            break;
        }
        case Speed:
        {
            double output = calculateFeedbackOutput(getVelocity(), mControlGoal);
            super.set(output);
            break;
        }
        case MotionMagic:
        {
            double output = calculateMotionMagicOutput(getPosition(), getVelocity(), mControlGoal);
            super.set(output);
            break;
        }
        case MotionProfile:
        {
            double output = calculateMotionProfileOutput(getPosition(), getVelocity(), (int) mControlGoal);
            super.set(output);
            break;
        }
        // Just use normal update
        case Raw:
            break;
        default:
            sLOGGER.log(Level.ERROR, "Unsupported control type : " + mControlType);
            break;
        }
        super.update(aWaitTime);
    }

    public void setMotionMagicMaxAcceleration(int aAccel)
    {
        mMotionMagicMaxAcceleration = aAccel / getMotionMagicAccelerationUnitConversion();
    }

    public void setMotionMagicMaxVelocity(int aVel)
    {
        mMotionMagicMaxVelocity = aVel / getMotionMagicVelocityUnitConversion();
    }

    private double calculateMotionMagicOutput(double aCurrentPosition, double aCurrentVelocity, double aControlGoal)
    {
        double error = aControlGoal - aCurrentPosition;
        double dErr = error - mLastError;

        double velocity = Math.copySign(mMotionMagicMaxVelocity, error);
        if (Math.abs(error) < Math.abs(velocity))
        {
            velocity = error;
        }

        double pComp = mPidConstants[mCurrentPidProfile].mP * error;
        double iComp = mPidConstants[mCurrentPidProfile].mI * mSumError;
        double dComp = mPidConstants[mCurrentPidProfile].mD * dErr;
        double fComp = mPidConstants[mCurrentPidProfile].mF * velocity;

        double output = pComp + iComp + dComp + fComp;

        output = Math.min(output, 1.0);
        output = Math.max(output, -1.0);

        mLastError = error;

        if (sLOGGER.isDebugEnabled())
        {
            DecimalFormat df = new DecimalFormat("#.##");
            sLOGGER.log(Level.DEBUG,
                    "Motion Magic... " + "Goal: " + aControlGoal + ", " + "CurPos: " + df.format(aCurrentPosition) + ", " + "CurVel: "
                            + df.format(aCurrentVelocity) + ", " + "err: " + df.format(error) + ", " + "maxa: "
                            + df.format(mMotionMagicMaxAcceleration) + ", " + "maxv: " + df.format(mMotionMagicMaxVelocity) + " -- Output: "
                            + output);
        }

        return output;
    }

    @Override
    public void set(double aVoltagePercentage)
    {
        // followers will have their own inverted flag
        for (BaseCanSmartSpeedController follower : mFollowers)
        {
            follower.set(aVoltagePercentage);
        }

        if (mInverted)
        {
            aVoltagePercentage = -aVoltagePercentage;
        }

        super.set(aVoltagePercentage);
    }

    protected double calculateFeedbackOutput(double aCurrent, double aGoal)
    {
        double error = aGoal - aCurrent;
        double dErr = error - mLastError;

        mSumError += error;
        if (error > mPidConstants[mCurrentPidProfile].mIZone)
        {
            mSumError = 0;
        }

        double pComp = mPidConstants[mCurrentPidProfile].mP * error;
        double iComp = mPidConstants[mCurrentPidProfile].mI * mSumError;
        double dComp = mPidConstants[mCurrentPidProfile].mD * dErr;
        double fComp = mPidConstants[mCurrentPidProfile].mF * aGoal;

        double output = pComp + iComp + dComp + fComp;

        output = Math.min(output, 1.0);
        output = Math.max(output, -1.0);

        mLastError = error;

        sLOGGER.log(Level.DEBUG, "Updating CAN PID: Error: " + error + ", Output: " + output + " (Cur: " + aCurrent + ", Goal: " + aGoal + ") "
                + " (P: " + pComp + ", I: " + iComp + ", D: " + dComp + ", F: " + fComp + ")");

        return output;

    }

    public void addFollower(BaseCanSmartSpeedController aWrapper)
    {
        mFollowers.add(aWrapper);
    }

    protected void setCanFeedbackDevice(FeedbackDevice aNewDevice)
    {
        if (aNewDevice != mFeedbackDevice)
        {
            if (mFeedbackDevice == null)
            {
                mFeedbackDevice = aNewDevice;
                registerFeedbackSensor();
                sLOGGER.log(Level.DEBUG, "Setting feedback device to " + aNewDevice);
            }
            else
            {
                sLOGGER.log(Level.ERROR, "The simulator does not like you changing the feedback device attached to talon " + mCanHandle + " from "
                        + mFeedbackDevice + " to " + aNewDevice);
            }
        }
    }

    protected abstract void registerFeedbackSensor();

    protected abstract double getPositionUnitConversion();

    protected abstract double getMotionMagicAccelerationUnitConversion();

    protected abstract double getMotionMagicVelocityUnitConversion();

    protected abstract double calculateMotionProfileOutput(double aCurrentPosition, double aCurrentVelocity, int aModeType);

    @Override
    public String toString()
    {
        return "BaseCanSmartSpeedController [mCanHandle=" + mCanHandle + "]";
    }

}

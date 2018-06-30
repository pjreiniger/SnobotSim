package com.snobot.simulator.simulator_components.ctre;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.module_wrapper.ASensorWrapper;
import com.snobot.simulator.module_wrapper.BaseEncoderWrapper;
import com.snobot.simulator.module_wrapper.BasePwmWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogInWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

public class CtreTalonSrxSpeedControllerSim extends BasePwmWrapper
{
    private static final Logger sLOGGER = LogManager.getLogger(CtreTalonSrxSpeedControllerSim.class);

    public static final int sCTRE_OFFSET = 100;

    private boolean mLoggedCantOverrideFwdLimitSwitch = false;
    private boolean mLoggedCantOverrideRevLimitSwitch = false;

    public enum ControlType
    {
        Raw, Position, Speed, MotionMagic, MotionProfile
    }

    public enum FeedbackDevice
    {
        QuadEncoder, Encoder, Analog
    }

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

    public static class MotionProfilePoint
    {
        public final int mIndex;
        public final double mPosition;
        public final double mVelocity;

        public MotionProfilePoint(int aIndex, double aPosition, double aVelocity)
        {
            mIndex = aIndex;
            mPosition = aPosition;
            mVelocity = aVelocity;
        }

        @Override
        public String toString()
        {
            return "MotionProfilePoint [mIndex=" + mIndex + ", mPosition=" + mPosition + ", mVelocity=" + mVelocity + "]";
        }

    }

    protected final int mCanHandle; // The handle without the offset applied
    protected ControlType mControlType;

    // Feedback control
    protected PIDFConstants[] mPidConstants = {new PIDFConstants(), new PIDFConstants()};
    protected int mCurrentPidProfile;
    protected FeedbackDevice mFeedbackDevice;
    protected double mControlGoal;
    protected double mSumError;
    protected double mLastError;

    // Motion Profile
    protected List<MotionProfilePoint> mMotionProfilePoints;
    protected int mMotionProfileProcessedCounter;
    protected int mMotionProfileCurrentPointIndex;

    // Motion Magic
    protected double mMotionMagicMaxAcceleration;
    protected double mMotionMagicMaxVelocity;

    protected List<CtreTalonSrxSpeedControllerSim> mFollowers;

    public CtreTalonSrxSpeedControllerSim(int aCanHandle)
    {
        super(aCanHandle, "CAN SC " + (aCanHandle - sCTRE_OFFSET));

        mCanHandle = aCanHandle - sCTRE_OFFSET;

        mCurrentPidProfile = 0;
        mControlType = ControlType.Raw;

        mMotionProfilePoints = new LinkedList<>();
        mMotionProfileProcessedCounter = 0;

        mFollowers = new ArrayList<>();
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

    public void setPositionGoal(int aDemand)
    {
        double position = aDemand / getPositionUnitConversion();

        mControlType = ControlType.Position;
        mControlGoal = position;
    }

    public void setSpeedGoal(double aSpeed)
    {
        mControlType = ControlType.Speed;
        mControlGoal = aSpeed;
    }

    public void setMotionMagicGoal(int aDemand)
    {
        double goal = aDemand / getPositionUnitConversion();

        mControlType = ControlType.MotionMagic;
        mControlGoal = goal;
    }

    public void setMotionProfilingCommand(int aDemand)
    {
        mControlType = ControlType.MotionProfile;
        mControlGoal = aDemand;
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

    @Override
    public void set(double aVoltagePercentage)
    {
        super.set(aVoltagePercentage);
        for (CtreTalonSrxSpeedControllerSim follower : mFollowers)
        {
            follower.set(aVoltagePercentage);
        }
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

        sLOGGER.log(Level.DEBUG, "Updating CAN PID: Error: " + error + ", Output: " + output
                + " (Cur: " + aCurrent + ", Goal: " + aGoal + ") "
                + " (P: " + pComp + ", I: " + iComp + ", D: " + dComp + ", F: " + fComp + ")");

        return output;

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

        double pComp = mPidConstants[mCurrentPidProfile].mP * error;
        double iComp = mPidConstants[mCurrentPidProfile].mI * mSumError;
        double dComp = mPidConstants[mCurrentPidProfile].mD * dErr;
        double fComp = mPidConstants[mCurrentPidProfile].mF * mMotionMagicMaxVelocity;

        double output = pComp + iComp + dComp + fComp;

        output = Math.min(output, 1.0);
        output = Math.max(output, -1.0);

        mLastError = error;

        if (sLOGGER.isDebugEnabled())
        {
            DecimalFormat df = new DecimalFormat("#.##");
            sLOGGER.log(Level.DEBUG, "Motion Magic... "
                    + "Goal: " + aControlGoal + ", "
                    + "CurPos: " + df.format(aCurrentPosition) + ", "
                    + "CurVel: " + df.format(aCurrentVelocity) + ", "
                    + "err: " + df.format(error) + ", "
                    + "maxa: " + df.format(mMotionMagicMaxAcceleration) + ", " + "maxv: " + df.format(mMotionMagicMaxVelocity));
        }

        return output;
    }

    private double calculateMotionProfileOutput(double aCurrentPosition, double aCurrentVelocity, int aModeType)
    {
        if (aModeType != 1)
        {
            // System.out.println(mMotionProfilePoints);
            return 0;
        }

        if (mMotionProfileCurrentPointIndex >= mMotionProfilePoints.size())
        {
            return 0;
        }

        MotionProfilePoint currentPoint = mMotionProfilePoints.get(mMotionProfileCurrentPointIndex++);

        return calculateMotionProfileOutput(aCurrentPosition, aCurrentVelocity, currentPoint.mPosition, currentPoint.mVelocity);
    }

    private double calculateMotionProfileOutput(double aCurrentPosition, double aCurrentVelocity, double aGoalPosition, double aGoalVelocity)
    {
        double error = aGoalPosition - aCurrentPosition;

        double pTerm = error * mPidConstants[mCurrentPidProfile].mP;
        double dTerm = 0; // mPidConstants.mD * ((error - last_error_) /
                          // segment.dt - segment.vel);
        double vTerm = mPidConstants[mCurrentPidProfile].mF * aGoalVelocity;
        double output = pTerm + dTerm + vTerm;

//        DecimalFormat df = new DecimalFormat("#.##");
//        System.out.println(output + "  (" + "P: " + df.format(aCurrentPosition) + ", " + "V: " + df.format(aCurrentVelocity) + " - " + "GP: "
//                + aGoalPosition + ", GV: " + aGoalVelocity + ") " + "P: " + pTerm + ", V: " + vTerm);

        return output; // NOPMD
    }

    public double getLastClosedLoopError()
    {
        double multiplier = 1.0;

        switch (mControlType)
        {
        case MotionMagic:
        case MotionProfile:
        case Position:
            multiplier = getPositionUnitConversion();
            break;
        case Speed:
            multiplier = getVelocityUnitConversion();
            break;
        default:
            sLOGGER.log(Level.WARN, "I don't think get closed loop error should be called with " + mControlType);
            break;

        }
        return mLastError * multiplier;
    }

    public void addMotionProfilePoint(MotionProfilePoint aPoint)
    {
        if (aPoint.mIndex == 0)
        {
            mMotionProfilePoints.clear();
            mMotionProfileProcessedCounter = 0;
            mMotionProfileCurrentPointIndex = 0;
        }
        else
        {
            mMotionProfilePoints.add(aPoint);
            mMotionProfileProcessedCounter++;
        }
    }

    public int getMotionProfileSize()
    {
        return mMotionProfilePoints.size();
    }

    public MotionProfilePoint getMotionProfilePoint()
    {
        if (mMotionProfileProcessedCounter <= mMotionProfilePoints.size() && mMotionProfileProcessedCounter != 0)
        {
            return mMotionProfilePoints.get(mMotionProfileProcessedCounter - 1);
        }
        return null;
    }

    public void addFollower(CtreTalonSrxSpeedControllerSim aWrapper)
    {
        mFollowers.add(aWrapper);
    }

    public void setLimitSwitchOverride(boolean aOverrideFwdLimitSwitch, boolean aOverrideRevLimitSwitch)
    {
        if (aOverrideFwdLimitSwitch && !mLoggedCantOverrideFwdLimitSwitch)
        {
            mLoggedCantOverrideFwdLimitSwitch = true;
            sLOGGER.log(Level.WARN, "Cannot override forward limit switches");
        }

        if (aOverrideRevLimitSwitch && !mLoggedCantOverrideRevLimitSwitch)
        {
            mLoggedCantOverrideRevLimitSwitch = true;
            sLOGGER.log(Level.WARN, "Cannot override reverse limit switch");
        }
    }

    public void setCurrentProfile(byte aProfileSelect)
    {
        mCurrentPidProfile = aProfileSelect;
    }

    public void setCanFeedbackDevice(byte aFeedbackDevice)
    {
        FeedbackDevice newDevice = null;
        switch (aFeedbackDevice)
        {
        // Default feedback sensor, handle with care
        case 0:
            newDevice = FeedbackDevice.Encoder;
            break;
        case 2:
            newDevice = FeedbackDevice.Analog;
            break;
        // The Absolute and Relative encoders behave the same
        case 6:
        case 7:
            newDevice = FeedbackDevice.Encoder;
            break;
        default:
            sLOGGER.log(Level.WARN, "Unsupported feedback device " + aFeedbackDevice);
            break;
        }

        if (newDevice != mFeedbackDevice)
        {
            if (mFeedbackDevice == null)
            {
                mFeedbackDevice = newDevice;
                registerFeedbackSensor();
                sLOGGER.log(Level.DEBUG, "Setting feedback device to " + newDevice);
            }
            else
            {
                sLOGGER.log(Level.ERROR, "The simulator does not like you changing the feedback device attached to talon " + mCanHandle + " from "
                        + mFeedbackDevice + " to " + newDevice);
            }
            // if (mFeedbackDevice != null && mFeedbackDevice !=
            // FeedbackDevice.QuadEncoder)
            // {
            // sLOGGER.log(Level.ERROR, "The simulator does not like you
            // changing the feedback device attached to talon " + mCanHandle + "
            // from "
            // + mFeedbackDevice + " to " + newDevice);
            // }
            // else if (newDevice != FeedbackDevice.QuadEncoder)
            // {
            // if(mFeedbackDevice == FeedbackDevice.QuadEncoder)
            // {
            // System.out.println("UH OHHHHHH");
            // }
            //
            // mFeedbackDevice = newDevice;
            // registerFeedbackSensor();
            // sLOGGER.log(Level.DEBUG, "Setting feedback device to " +
            // newDevice);
            // }
            // else if (newDevice == FeedbackDevice.QuadEncoder)
            // {
            // mFeedbackDevice = newDevice;
            // registerFeedbackSensor();
            // }
        }
    }

    private void registerFeedbackSensor()
    {
        switch (mFeedbackDevice)
        {
        case Encoder:
            if (!DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().contains(mHandle))
            {
                DataAccessorFactory.getInstance().getEncoderAccessor().createSimulator(mHandle, CtreEncoder.class.getName());
                DataAccessorFactory.getInstance().getEncoderAccessor().connectSpeedController(getHandle(), getHandle());
                sLOGGER.log(Level.WARN, "CTRE Encoder on port " + mCanHandle + " was not registerd before starting the robot");
            }
            break;
        case Analog:
            if (!DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList().contains(mCanHandle))
            {
                DataAccessorFactory.getInstance().getAnalogInAccessor().createSimulator(mCanHandle, CtreAnalogIn.class.getName());
                sLOGGER.log(Level.WARN, "CTRE Analog on port " + mCanHandle + " was not registerd before starting the robot");
            }
            break;
        default:
            sLOGGER.log(Level.ERROR, "Unsupported feedback device " + mFeedbackDevice);
            break;
        }
    }

    private double getPositionUnitConversion()
    {
        return 4096;
    }

    private double getVelocityUnitConversion()
    {
        return 600;
    }

    private double getMotionMagicAccelerationUnitConversion()
    {
        return 600;
    }

    private double getMotionMagicVelocityUnitConversion()
    {
        return 600;
    }

    public int getBinnedPosition()
    {
        return (int) (getPosition() * this.getPositionUnitConversion());
    }

    public int getBinnedVelocity()
    {
        return (int) (getVelocity() * this.getVelocityUnitConversion());
    }

    public static class CtreEncoder extends BaseEncoderWrapper
    {

        public CtreEncoder(int aPort)
        {
            super("CAN Encoder (" + (aPort - sCTRE_OFFSET) + ")");
        }
    }

    public static class CtreAnalogIn extends ASensorWrapper implements IAnalogInWrapper
    {

        public CtreAnalogIn(int aPort)
        {
            super("CAN Analog (" + aPort + ")");
        }

        @Override
        public void setVoltage(double aVoltage)
        {
            // Nothing to do
        }

        @Override
        public double getVoltage()
        {
            return 0;
        }

    }
}

package com.snobot.simulator.simulator_components.ctre;

import java.util.LinkedList;
import java.util.List;

import com.snobot.simulator.module_wrapper.interfaces.IEncoderWrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.simulator_components.smart_sc.BaseCanSmartSpeedController;
import com.snobot.simulator.simulator_components.smart_sc.SmartScEncoder;
import com.snobot.simulator.simulator_components.smart_sc.SmartScAnalogIn;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

public class CtreTalonSrxSpeedControllerSim extends BaseCanSmartSpeedController
{
    private static final Logger sLOGGER = LogManager.getLogger(CtreTalonSrxSpeedControllerSim.class);

    private boolean mLoggedCantOverrideFwdLimitSwitch;
    private boolean mLoggedCantOverrideRevLimitSwitch;

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

    // Motion Profile
    protected List<MotionProfilePoint> mMotionProfilePoints;
    protected int mMotionProfileProcessedCounter;
    protected int mMotionProfileCurrentPointIndex;

    public CtreTalonSrxSpeedControllerSim(int aCanHandle)
    {
        super(aCanHandle, "CTRE", 2);

        mMotionProfilePoints = new LinkedList<>();
        mMotionProfileProcessedCounter = 0;
    }

    public void setMotionProfilingCommand(double aDemand)
    {
        mControlType = ControlType.MotionProfile;
        mControlGoal = aDemand;
    }

    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    @Override
    protected double calculateMotionProfileOutput(double aCurrentPosition, double aCurrentVelocity, int aModeType)
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

    @SuppressWarnings("PMD.UnusedFormalParameter")
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

    @Override
    protected void registerFeedbackSensor()
    {
        switch (mFeedbackDevice)
        {
        case Encoder:
            if (!DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().contains(mHandle))
            {
                IEncoderWrapper wrapper = DataAccessorFactory.getInstance().getEncoderAccessor().createSimulator(mHandle, SmartScEncoder.class.getName());
                wrapper.connectSpeedController(getHandle());
                sLOGGER.log(Level.WARN, "CTRE Encoder on port " + mCanHandle + " was not registerd before starting the robot");
            }
            SensorActuatorRegistry.get().getEncoders().get(getHandle()).setInitialized(true);
            break;
        case Analog:
            if (!DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList().contains(mHandle))
            {
                DataAccessorFactory.getInstance().getAnalogInAccessor().createSimulator(mHandle, SmartScAnalogIn.class.getName());
                sLOGGER.log(Level.WARN, "CTRE Analog on port " + mCanHandle + " was not registerd before starting the robot");
            }
            SensorActuatorRegistry.get().getAnalogIn().get(getHandle()).setInitialized(true);
            break;
        default:
            sLOGGER.log(Level.ERROR, "Unsupported feedback device " + mFeedbackDevice);
            break;
        }
    }

    @Override
    protected double getPositionUnitConversion()
    {
        return 4096;
    }

    protected double getVelocityUnitConversion()
    {
        return 600;
    }

    @Override
    protected double getMotionMagicAccelerationUnitConversion()
    {
        return 600;
    }

    @Override
    protected double getMotionMagicVelocityUnitConversion()
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
        case 8:
            newDevice = FeedbackDevice.Encoder;
            break;
        default:
            sLOGGER.log(Level.WARN, "Unsupported feedback device " + aFeedbackDevice);
            break;
        }

        setCanFeedbackDevice(newDevice);
    }
}

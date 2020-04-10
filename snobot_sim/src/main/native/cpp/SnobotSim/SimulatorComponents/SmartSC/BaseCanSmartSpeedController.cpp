#include "SnobotSim/SimulatorComponents/SmartSC/BaseCanSmartSpeedController.h"

#include <algorithm>
#include <cmath>

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "SnobotSim/SimulatorComponents/SmartSC/CanIdOffset.h"
#include "SnobotSim/SimulatorComponents/SmartSC/SmartSCAnalogIn.h"
#include "SnobotSim/SimulatorComponents/SmartSC/SmartSCEncoder.h"

BaseCanSmartSpeedController::BaseCanSmartSpeedController(int aSimHandle, const std::string& aBaseName, int aPidSlots) :
        BaseSpeedControllerWrapper(aBaseName + " SC " + std::to_string(aSimHandle - CAN_SC_OFFSET), aSimHandle),
        mCanHandle(aSimHandle - CAN_SC_OFFSET),
        mPidConstants(aPidSlots),
        mFeedbackDevice(FeedbackDevice::None),
        mControlType(ControlType::Raw)
{
}

void BaseCanSmartSpeedController::setInverted(bool aInverted)
{
    mInverted = aInverted;
}

void BaseCanSmartSpeedController::setPGain(int aSlot, double aP)
{
    if (aSlot >= mPidConstants.size())
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "PID slot too big");
        return;
    }
    mPidConstants[aSlot].mP = aP;
}

void BaseCanSmartSpeedController::setIGain(int aSlot, double aI)
{
    if (aSlot >= mPidConstants.size())
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "PID slot too big");
        return;
    }
    mPidConstants[aSlot].mI = aI;
}

void BaseCanSmartSpeedController::setDGain(int aSlot, double aD)
{
    if (aSlot >= mPidConstants.size())
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "PID slot too big");
        return;
    }
    mPidConstants[aSlot].mD = aD;
}

void BaseCanSmartSpeedController::setFGain(int aSlot, double aF)
{
    if (aSlot >= mPidConstants.size())
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "PID slot too big");
        return;
    }
    mPidConstants[aSlot].mF = aF;
}

void BaseCanSmartSpeedController::setIZone(int aSlot, double aIzone)
{
    if (aSlot >= mPidConstants.size())
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "PID slot too big");
        return;
    }
    mPidConstants[aSlot].mIZone = aIzone;
}

BaseCanSmartSpeedController::PIDFConstants BaseCanSmartSpeedController::getPidConstants(int aSlot)
{
    if (aSlot >= mPidConstants.size())
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "PID slot too big");
    }
    return mPidConstants[aSlot];
}

void BaseCanSmartSpeedController::setPositionGoal(double aDemand)
{
    if (mInverted)
    {
        aDemand = -aDemand;
    }

    double position = aDemand / getPositionUnitConversion();

    mControlType = ControlType::Position;
    mControlGoal = position;
}

void BaseCanSmartSpeedController::setSpeedGoal(double aSpeed)
{
    if (mInverted)
    {
        aSpeed = -aSpeed;
    }

    mControlType = ControlType::Speed;
    mControlGoal = aSpeed;
}

void BaseCanSmartSpeedController::setRawGoal(double aRawOutput)
{
    mControlType = ControlType::Raw;
    SetVoltagePercentage(aRawOutput);
}

void BaseCanSmartSpeedController::setMotionMagicGoal(double aDemand)
{
    if (mInverted)
    {
        aDemand = -aDemand;
    }

    double goal = aDemand / getPositionUnitConversion();

    mControlType = ControlType::MotionMagic;
    mControlGoal = goal;
}

void BaseCanSmartSpeedController::Update(double aWaitTime)
{
    switch (mControlType)
    {
    case ControlType::Position:
    {
        double output = calculateFeedbackOutput(GetPosition(), mControlGoal);
        BaseSpeedControllerWrapper::SetVoltagePercentage(output);
        break;
    }
    case ControlType::Speed:
    {
        double output = calculateFeedbackOutput(GetVelocity(), mControlGoal);
        BaseSpeedControllerWrapper::SetVoltagePercentage(output);
        break;
    }
    case ControlType::MotionMagic:
    {
        double output = calculateMotionMagicOutput(GetPosition(), GetVelocity(), mControlGoal);
        BaseSpeedControllerWrapper::SetVoltagePercentage(output);
        break;
    }
    case ControlType::MotionProfile:
    {
        double output = calculateMotionProfileOutput(GetPosition(), GetVelocity(), static_cast<int>(mControlGoal));
        BaseSpeedControllerWrapper::SetVoltagePercentage(output);
        break;
    }
    // Just use normal update
    case ControlType::Raw:
        break;
    default:
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Unsupported control type : " << static_cast<int>(mControlType));
        break;
    }
    BaseSpeedControllerWrapper::Update(aWaitTime);
}

void BaseCanSmartSpeedController::setMotionMagicMaxAcceleration(int aAccel)
{
    mMotionMagicMaxAcceleration = aAccel / getMotionMagicAccelerationUnitConversion();
}

void BaseCanSmartSpeedController::setMotionMagicMaxVelocity(int aVel)
{
    mMotionMagicMaxVelocity = aVel / getMotionMagicVelocityUnitConversion();
}

double BaseCanSmartSpeedController::calculateMotionMagicOutput(double aCurrentPosition, double aCurrentVelocity, double aControlGoal)
{
    double error = aControlGoal - aCurrentPosition;
    double dErr = error - mLastError;

    double velocity = std::copysign(mMotionMagicMaxVelocity, error);
    if (std::abs(error) < std::abs(velocity))
    {
        velocity = error;
    }

    PIDFConstants& constants = mPidConstants[mCurrentPidProfile];
    double pComp = mPidConstants[mCurrentPidProfile].mP * error;
    double iComp = mPidConstants[mCurrentPidProfile].mI * mSumError;
    double dComp = mPidConstants[mCurrentPidProfile].mD * dErr;
    double fComp = mPidConstants[mCurrentPidProfile].mF * velocity;

    double output = pComp + iComp + dComp + fComp;

    output = std::min(output, 1.0);
    output = std::max(output, -1.0);

    mLastError = error;

    // if (sLOGGER.isDebugEnabled())
    // {
    //     DecimalFormat df = new DecimalFormat("#.##");
    //     sLOGGER.log(Level.DEBUG,
    //             "Motion Magic... " + "Goal: " + aControlGoal + ", " + "CurPos: " + df.format(aCurrentPosition) + ", " + "CurVel: "
    //                     + df.format(aCurrentVelocity) + ", " + "err: " + df.format(error) + ", " + "maxa: "
    //                     + df.format(mMotionMagicMaxAcceleration) + ", " + "maxv: " + df.format(mMotionMagicMaxVelocity) + " -- Output: "
    //                     + output);
    // }

    return output;
}

void BaseCanSmartSpeedController::SetVoltagePercentage(double aVoltagePercentage)
{
    // followers will have their own inverted flag
    for (auto& follower : mFollowers)
    {
        follower->SetVoltagePercentage(aVoltagePercentage);
    }

    if (mInverted)
    {
        aVoltagePercentage = -aVoltagePercentage;
    }
    BaseSpeedControllerWrapper::SetVoltagePercentage(aVoltagePercentage);
}

double BaseCanSmartSpeedController::calculateFeedbackOutput(double aCurrent, double aGoal)
{
    double error = aGoal - aCurrent;
    double dErr = error - mLastError;

    mSumError += error;

    PIDFConstants& constants = mPidConstants[mCurrentPidProfile];
    if (error > constants.mIZone)
    {
        mSumError = 0;
    }

    double pComp = constants.mP * error;
    double iComp = constants.mI * mSumError;
    double dComp = constants.mD * dErr;
    double fComp = constants.mF * aGoal;

    double output = pComp + iComp + dComp + fComp;

    output = std::min(output, 1.0);
    output = std::max(output, -1.0);

    mLastError = error;

    // sLOGGER.log(Level.DEBUG, "Updating CAN PID: Error: " + error + ", Output: " + output + " (Cur: " + aCurrent + ", Goal: " + aGoal + ") "
    //         + " (P: " + pComp + ", I: " + iComp + ", D: " + dComp + ", F: " + fComp + ")");

    return output;
}

void BaseCanSmartSpeedController::addFollower(std::shared_ptr<BaseCanSmartSpeedController> aWrapper)
{
    mFollowers.push_back(aWrapper);
}

void BaseCanSmartSpeedController::setCanFeedbackDevice(FeedbackDevice aNewDevice)
{
    if (aNewDevice != mFeedbackDevice)
    {
        if (mFeedbackDevice == FeedbackDevice::None)
        {
            mFeedbackDevice = aNewDevice;
            registerFeedbackSensor();
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_INFO, "Setting feedback device to " << static_cast<int>(aNewDevice));
        }
        else
        {
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "The simulator does not like you changing the feedback device attached to talon " << mCanHandle << " from " << static_cast<int>(mFeedbackDevice) << " to " << static_cast<int>(aNewDevice));
        }
    }
}
void BaseCanSmartSpeedController::registerFeedbackSensor()
{
    switch (mFeedbackDevice)
    {
    case FeedbackDevice::Encoder:
        if (!SensorActuatorRegistry::Get().GetIEncoderWrapper(mId, false))
        {
            FactoryContainer::Get().GetEncoderFactory()->Create(mId, SmartSCEncoder::TYPE);
            SensorActuatorRegistry::Get().GetIEncoderWrapper(mId, true)->SetSpeedController(SensorActuatorRegistry::Get().GetISpeedControllerWrapper(mId));
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Simulator on port " << mCanHandle << "(" << mId << ") was not registered before starting the robot");
        }
        SensorActuatorRegistry::Get().GetIEncoderWrapper(mId, true)->SetInitialized(true);
        break;
    case FeedbackDevice::Analog:
        if (!SensorActuatorRegistry::Get().GetIAnalogInWrapper(mId, false))
        {
            FactoryContainer::Get().GetAnalogInFactory()->Create(mId, SmartSCAnalogIn::TYPE);
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Simulator on port " << mCanHandle << "(" << mId << ") was not registered before starting the robot");
        }
        SensorActuatorRegistry::Get().GetIAnalogInWrapper(mId, true)->SetInitialized(true);
        break;
    default:
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Unsupported feedback device " << static_cast<int>(mFeedbackDevice));
        break;
    }
}

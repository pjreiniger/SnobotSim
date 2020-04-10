
#pragma once

#include <memory>
#include <string>
#include <vector>

#include "SnobotSim/ModuleWrapper/BaseSpeedControllerWrapper.h"

class BaseCanSmartSpeedController : public BaseSpeedControllerWrapper
{
public:
    enum class ControlType
    {
        Raw,
        Position,
        Speed,
        MotionMagic,
        MotionProfile
    };

    enum class FeedbackDevice
    {
        None,
        QuadEncoder,
        Encoder,
        Analog
    };

    BaseCanSmartSpeedController(int aCanHandel, const std::string& aBaseName, int aPidSlots);

    void Update(double aWaitTime) override;
    void SetVoltagePercentage(double aVoltagePercentage) override;

    void setInverted(bool aInverted);
    void setPGain(int aSlot, double aP);
    void setIGain(int aSlot, double aI);
    void setDGain(int aSlot, double aD);
    void setFGain(int aSlot, double aF);
    void setIZone(int aSlot, double aIzone);
    void setPositionGoal(double aDemand);
    void setSpeedGoal(double aSpeed);
    void setRawGoal(double aRawOutput);
    void setMotionMagicGoal(double aDemand);
    void setMotionMagicMaxAcceleration(int aAccel);
    void setMotionMagicMaxVelocity(int aVel);
    void addFollower(std::shared_ptr<BaseCanSmartSpeedController> aWrapper);

protected:
    struct PIDFConstants
    {
        double mP{ 0 };
        double mI{ 0 };
        double mD{ 0 };
        double mF{ 0 };
        double mIZone{ 0 };
    };

    PIDFConstants getPidConstants(int aSlot);
    double calculateMotionMagicOutput(double aCurrentPosition, double aCurrentVelocity, double aControlGoal);
    double calculateFeedbackOutput(double aCurrent, double aGoal);
    void setCanFeedbackDevice(FeedbackDevice aNewDevice);

    virtual void registerFeedbackSensor();
    virtual double getPositionUnitConversion() = 0;
    virtual double getMotionMagicAccelerationUnitConversion() = 0;
    virtual double getMotionMagicVelocityUnitConversion() = 0;
    virtual double calculateMotionProfileOutput(double aCurrentPosition, double aCurrentVelocity, int aModeType) = 0;

    const int mCanHandle;
    ControlType mControlType{ ControlType::Raw };
    bool mInverted{ false };

    // Feedback control
    std::vector<PIDFConstants> mPidConstants;
    int mCurrentPidProfile{ 0 };
    FeedbackDevice mFeedbackDevice{ FeedbackDevice::None };
    double mControlGoal{ 0 };
    double mSumError{ 0 };
    double mLastError{ 0 };

    // Motion Magic
    double mMotionMagicMaxAcceleration{ 0 };
    double mMotionMagicMaxVelocity{ 0 };

    std::vector<std::shared_ptr<BaseCanSmartSpeedController>> mFollowers;
};

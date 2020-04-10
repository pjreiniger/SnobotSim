
#pragma once

#include <string>
#include <vector>

#include "SnobotSim/SimulatorComponents/SmartSC/BaseCanSmartSpeedController.h"

class CtreTalonSRXSpeedControllerSim : public BaseCanSmartSpeedController
{
public:
    static const std::string TYPE;
    std::string GetType() override
    {
        return TYPE;
    }

    struct MotionProfilePoint
    {
        const int mIndex;
        const double mPosition;
        const double mVelocity;

        MotionProfilePoint(int aIndex, double aPosition, double aVelocity) :
                mIndex(aIndex),
                mPosition(aPosition),
                mVelocity(aVelocity)
        {
        }
    };

    explicit CtreTalonSRXSpeedControllerSim(int aCanHandle);

    void setMotionProfilingCommand(double aDemand);
    double getLastClosedLoopError();
    void addMotionProfilePoint(MotionProfilePoint aPoint);

    int getMotionProfileSize();
    MotionProfilePoint getMotionProfilePoint();
    void setLimitSwitchOverride(bool aOverrideFwdLimitSwitch, bool aOverrideRevLimitSwitch);

    void setCurrentProfile(char aProfileSelect);

    int getBinnedPosition();
    int getBinnedVelocity();
    void setCanFeedbackDevice(char aFeedbackDevice);

protected:
    double calculateMotionProfileOutput(double aCurrentPosition, double aCurrentVelocity, int aModeType) override;
    double calculateMotionProfileOutput(double aCurrentPosition, double aCurrentVelocity, double aGoalPosition, double aGoalVelocity);

    double getPositionUnitConversion() override
    {
        return 4096;
    }

    double getVelocityUnitConversion()
    {
        return 600;
    }

    double getMotionMagicAccelerationUnitConversion() override
    {
        return 600;
    }

    double getMotionMagicVelocityUnitConversion() override
    {
        return 600;
    }

    std::vector<MotionProfilePoint> mMotionProfilePoints;
    int mMotionProfileProcessedCounter{ 0 };
    int mMotionProfileCurrentPointIndex{ 0 };
};

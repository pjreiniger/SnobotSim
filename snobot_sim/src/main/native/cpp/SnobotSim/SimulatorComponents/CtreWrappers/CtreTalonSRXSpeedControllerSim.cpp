
#include "SnobotSim/SimulatorComponents/CtreWrappers/CtreTalonSRXSpeedControllerSim.h"

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "SnobotSim/SensorActuatorRegistry.h"

const std::string CtreTalonSRXSpeedControllerSim::TYPE = "com.snobot.simulator.simulator_components.ctre.CtreTalonSrxSpeedControllerSim";

CtreTalonSRXSpeedControllerSim::CtreTalonSRXSpeedControllerSim(int aCanHandle) :
        BaseCanSmartSpeedController(aCanHandle, "CTRE", 2)
{
}

void CtreTalonSRXSpeedControllerSim::setMotionProfilingCommand(double aDemand)
{
    mControlType = ControlType::MotionProfile;
    mControlGoal = aDemand;
}
double CtreTalonSRXSpeedControllerSim::getLastClosedLoopError()
{
    double multiplier = 1.0;

    switch (mControlType)
    {
    case ControlType::MotionMagic:
    case ControlType::MotionProfile:
    case ControlType::Position:
        multiplier = getPositionUnitConversion();
        break;
    case ControlType::Speed:
        multiplier = getVelocityUnitConversion();
        break;
    default:
        // sLOGGER.log(Level.WARN, "I don't think get closed loop error should be called with " + mControlType);
        break;
    }
    return mLastError * multiplier;
}
void CtreTalonSRXSpeedControllerSim::addMotionProfilePoint(MotionProfilePoint aPoint)
{
    // if (aPoint.mIndex == 0)
    // {
    //     mMotionProfilePoints.clear();
    //     mMotionProfileProcessedCounter = 0;
    //     mMotionProfileCurrentPointIndex = 0;
    // }
    // else
    // {
    //     mMotionProfilePoints.add(aPoint);
    //     mMotionProfileProcessedCounter++;
    // }
}

int CtreTalonSRXSpeedControllerSim::getMotionProfileSize()
{
    return mMotionProfilePoints.size();
}

// MotionProfilePoint CtreTalonSRXSpeedControllerSim::getMotionProfilePoint()
// {
//     if (mMotionProfileProcessedCounter <= mMotionProfilePoints.size() && mMotionProfileProcessedCounter != 0)
//     {
//         return mMotionProfilePoints.get(mMotionProfileProcessedCounter - 1);
//     }
//     return null;
// }

void CtreTalonSRXSpeedControllerSim::setLimitSwitchOverride(bool aOverrideFwdLimitSwitch, bool aOverrideRevLimitSwitch)
{
    // if (aOverrideFwdLimitSwitch && !mLoggedCantOverrideFwdLimitSwitch)
    // {
    //     mLoggedCantOverrideFwdLimitSwitch = true;
    //     sLOGGER.log(Level.WARN, "Cannot override forward limit switches");
    // }

    // if (aOverrideRevLimitSwitch && !mLoggedCantOverrideRevLimitSwitch)
    // {
    //     mLoggedCantOverrideRevLimitSwitch = true;
    //     sLOGGER.log(Level.WARN, "Cannot override reverse limit switch");
    // }
}

void CtreTalonSRXSpeedControllerSim::setCurrentProfile(char aProfileSelect)
{
    mCurrentPidProfile = aProfileSelect;
}

int CtreTalonSRXSpeedControllerSim::getBinnedPosition()
{
    return static_cast<int>(GetPosition() * getPositionUnitConversion());
}
int CtreTalonSRXSpeedControllerSim::getBinnedVelocity()
{
    return static_cast<int>(GetVelocity() * getVelocityUnitConversion());
}
void CtreTalonSRXSpeedControllerSim::setCanFeedbackDevice(char aFeedbackDevice)
{
    FeedbackDevice newDevice = FeedbackDevice::None;
    switch (aFeedbackDevice)
    {
    // Default feedback sensor, handle with care
    case 0:
        newDevice = FeedbackDevice::Encoder;
        break;
    case 2:
        newDevice = FeedbackDevice::Analog;
        break;
    // The Absolute and Relative encoders behave the same
    case 8:
        newDevice = FeedbackDevice::Encoder;
        break;
    default:
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unsupported feedback device " << static_cast<int>(aFeedbackDevice));
        break;
    }

    BaseCanSmartSpeedController::setCanFeedbackDevice(newDevice);
}

double CtreTalonSRXSpeedControllerSim::calculateMotionProfileOutput(double aCurrentPosition, double aCurrentVelocity, int aModeType)
{
    return 0;
}

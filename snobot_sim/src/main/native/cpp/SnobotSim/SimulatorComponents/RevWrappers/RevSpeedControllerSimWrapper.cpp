

#include "SnobotSim/SimulatorComponents/RevWrappers/RevSpeedControllerSimWrapper.h"

#include "SnobotSim/Logging/SnobotLogger.h"

const std::string RevpeedControllerSim::TYPE = "com.snobot.simulator.simulator_components.ctre.RevpeedControllerSim";

RevpeedControllerSim::RevpeedControllerSim(int aCanHandle) :
        BaseCanSmartSpeedController(aCanHandle, "Rev", 2)
{
}

double RevpeedControllerSim::calculateMotionProfileOutput(double aCurrentPosition, double aCurrentVelocity, int aModeType)
{
    return 0;
}

void RevpeedControllerSim::setCanFeedbackDevice(int aFeedbackDevice)
{
    FeedbackDevice newDevice = FeedbackDevice::None;
    if (aFeedbackDevice == 1 || aFeedbackDevice == 2)
    {
        newDevice = FeedbackDevice::Encoder;
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unsupported feedback device " << static_cast<int>(aFeedbackDevice));
    }

    BaseCanSmartSpeedController::setCanFeedbackDevice(newDevice);
}

#include "SnobotSim/SimulatorComponents/SmartSC/SmartSCLimitSwitch.h"

#include "SnobotSim/SimulatorComponents/SmartSC/CanIdOffset.h"

const std::string SmartSCLimitSwitch::TYPE = "com.snobot.simulator.simulator_components.smart_sc.SmartScEncoder";

SmartSCLimitSwitch::SmartSCLimitSwitch(int aPort) :
        AModuleWrapper("CAN Limit Switch " + std::to_string(aPort - CAN_SC_OFFSET))
{
}

SmartSCLimitSwitch::~SmartSCLimitSwitch()
{
}

bool SmartSCLimitSwitch::Get()
{
    return mState;
}
void SmartSCLimitSwitch::Set(bool aState)
{
    mState = aState;
}

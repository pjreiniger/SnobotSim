#include "SnobotSim/SimulatorComponents/SmartSC/SmartSCAnalogIn.h"

#include "SnobotSim/SimulatorComponents/SmartSC/CanIdOffset.h"

const std::string SmartSCAnalogIn::TYPE = "com.snobot.simulator.simulator_components.smart_sc.SmartScAnalogIn";

SmartSCAnalogIn::SmartSCAnalogIn(int aPort) :
        AModuleWrapper("CAN Analog (" + std::to_string(aPort - CAN_SC_OFFSET) + ")")
{
}

SmartSCAnalogIn::~SmartSCAnalogIn()
{
}

void SmartSCAnalogIn::SetVoltage(double aVoltage)
{
    mVoltage = aVoltage;
}

double SmartSCAnalogIn::GetVoltage()
{
    return mVoltage;
}

#include "SnobotSim/SimulatorComponents/SmartSC/SmartSCEncoder.h"

#include "SnobotSim/SimulatorComponents/SmartSC/CanIdOffset.h"

const std::string SmartSCEncoder::TYPE = "com.snobot.simulator.simulator_components.smart_sc.SmartScEncoder";

SmartSCEncoder::SmartSCEncoder(int aPort) :
        BaseEncoderWrapper("CAN Encoder (" + std::to_string(aPort - CAN_SC_OFFSET) + ")")
{
}

SmartSCEncoder::~SmartSCEncoder()
{
}

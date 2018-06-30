/*
 * RelayFactory.cpp
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/Factories/RelayFactory.h"

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiRelayWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"

RelayFactory::RelayFactory()
{
    // TODO Auto-generated constructor stub
}

RelayFactory::~RelayFactory()
{
    // TODO Auto-generated destructor stub
}

bool RelayFactory::Create(int aHandle, const std::string& aType)
{
    bool success = true;

    if (aType == "WpiRelayWrapper")
    {
        SensorActuatorRegistry::Get().Register(aHandle,
                std::shared_ptr<IRelayWrapper>(new WpiRelayWrapper(aHandle)));
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::WARN, "Unknown type " << aType);
        success = false;
    }

    return success;
}

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
}

RelayFactory::~RelayFactory()
{
}

bool RelayFactory::Create(int aHandle, const std::string& aType)
{
    bool success = true;

    if (aType == "WpiRelayWrapper")
    {
        if (!SensorActuatorRegistry::Get().GetIRelayWrapper(aHandle, false))
        {
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Not set up before loading robot");

            SensorActuatorRegistry::Get().Register(aHandle,
                    std::shared_ptr<IRelayWrapper>(new WpiRelayWrapper(aHandle)));
        }
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown type " << aType);
        success = false;
    }

    return success;
}

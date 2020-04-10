/*
 * DigitalIoFactory.cpp
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/Factories/DigitalIoFactory.h"

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiDigitalIoWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"

DigitalIoFactory::DigitalIoFactory()
{
}

DigitalIoFactory::~DigitalIoFactory()
{
}

bool DigitalIoFactory::Create(int aHandle, const std::string& aType)
{
    bool success = true;

    if (aType == WpiDigitalIoWrapper::TYPE)
    {
        SensorActuatorRegistry::Get().Register(aHandle,
                std::shared_ptr<IDigitalIoWrapper>(new WpiDigitalIoWrapper(aHandle)));
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown type " << aType);
        success = false;
    }

    return success;
}

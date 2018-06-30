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
    // TODO Auto-generated constructor stub
}

DigitalIoFactory::~DigitalIoFactory()
{
    // TODO Auto-generated destructor stub
}

bool DigitalIoFactory::Create(int aHandle, const std::string& aType)
{
    bool success = true;

    if (aType == "WpiDigitalIoWrapper")
    {
        SensorActuatorRegistry::Get().Register(aHandle,
                std::shared_ptr<IDigitalIoWrapper>(new WpiDigitalIoWrapper(aHandle)));
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::WARN, "Unknown type " << aType);
        success = false;
    }

    return success;
}

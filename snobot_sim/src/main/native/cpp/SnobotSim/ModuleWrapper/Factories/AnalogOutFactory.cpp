/*
 * AnalogOutFactory.cpp
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/Factories/AnalogOutFactory.h"

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiAnalogOutWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"

AnalogOutFactory::AnalogOutFactory()
{
}

AnalogOutFactory::~AnalogOutFactory()
{
}

bool AnalogOutFactory::Create(int aHandle, const std::string& aType)
{
    bool success = true;

    if (aType == "WpiAnalogOutWrapper")
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Not set up before loading robot");
        if (!SensorActuatorRegistry::Get().GetIAnalogOutWrapper(aHandle, false))
        {
            SensorActuatorRegistry::Get().Register(aHandle,
                    std::shared_ptr<IAnalogOutWrapper>(new WpiAnalogOutWrapper(aHandle)));
        }
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown type " << aType);
        success = false;
    }

    return success;
}

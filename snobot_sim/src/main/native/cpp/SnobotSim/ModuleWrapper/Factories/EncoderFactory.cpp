/*
 * EncoderFactory.cpp
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/Factories/EncoderFactory.h"

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiEncoderWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"

EncoderFactory::EncoderFactory()
{
}

EncoderFactory::~EncoderFactory()
{
}

bool EncoderFactory::Create(int aHandle, const std::string& aType)
{
    bool success = true;

    if (aType == "WpiEncoderWrapper")
    {
        if (!SensorActuatorRegistry::Get().GetIEncoderWrapper(aHandle, false))
        {
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Not set up before loading robot");

            SensorActuatorRegistry::Get().Register(aHandle,
                    std::shared_ptr<IEncoderWrapper>(new WpiEncoderWrapper(aHandle, aHandle)));
        }
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown type " << aType);
        success = false;
    }

    return success;
}

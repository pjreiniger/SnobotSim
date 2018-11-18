/*
 * SpeedControllerFactory.cpp
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/Factories/SpeedControllerFactory.h"

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiSpeedControllerWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"

SpeedControllerFactory::SpeedControllerFactory()
{
}

SpeedControllerFactory::~SpeedControllerFactory()
{
}

bool SpeedControllerFactory::Create(int aHandle, const std::string& aType)
{
    bool success = true;

    if (aType == "WpiPwmWrapper")
    {
        if (!SensorActuatorRegistry::Get().GetISpeedControllerWrapper(aHandle, false))
        {
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Not set up before loading robot");

            SensorActuatorRegistry::Get().Register(aHandle,
                    std::shared_ptr<ISpeedControllerWrapper>(new WpiSpeedControllerWrapper(aHandle)));
        }
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown type " << aType);
        success = false;
    }

    return success;
}

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
    // TODO Auto-generated constructor stub
}

SpeedControllerFactory::~SpeedControllerFactory()
{
    // TODO Auto-generated destructor stub
}

bool SpeedControllerFactory::Create(int aHandle, const std::string& aType)
{
    bool success = true;

    if (aType == "WpiPwmWrapper")
    {
        SensorActuatorRegistry::Get().Register(aHandle,
                std::shared_ptr<ISpeedControllerWrapper>(new WpiSpeedControllerWrapper(aHandle)));
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::WARN, "Unknown type " << aType);
        success = false;
    }

    return success;
}

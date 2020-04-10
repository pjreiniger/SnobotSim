/*
 * GyroFactory.cpp
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/Factories/GyroFactory.h"

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiAnalogGyroWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"

GyroFactory::GyroFactory()
{
}

GyroFactory::~GyroFactory()
{
}

bool GyroFactory::Create(int aHandle, const std::string& aType)
{
    bool success = true;

    if (aType == WpiAnalogGyroWrapper::TYPE)
    {
        std::shared_ptr<IGyroWrapper> gyroWrapper(new WpiAnalogGyroWrapper(aHandle));
        SensorActuatorRegistry::Get().Register(aHandle, gyroWrapper);
    }
    else if (aType == "com.snobot.simulator.simulator_components.adx_family.ADXRS450GyroWrapper")
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_DEBUG, "This type is set up elsewhere, won't do anything here");
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown type " << aType);
        success = false;
    }

    return success;
}

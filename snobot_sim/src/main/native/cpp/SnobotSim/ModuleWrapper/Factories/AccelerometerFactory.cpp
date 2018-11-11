/*
 * AccelerometerFactory.cpp
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/Factories/AccelerometerFactory.h"

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/SensorActuatorRegistry.h"

AccelerometerFactory::AccelerometerFactory()
{
}

AccelerometerFactory::~AccelerometerFactory()
{
}

bool AccelerometerFactory::Create(int aHandle, const std::string& aType)
{
    bool success = true;

    if (aType == "WpiAnalogGyroWrapper")
    {
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown type " << aType);
        success = false;
    }

    return success;
}

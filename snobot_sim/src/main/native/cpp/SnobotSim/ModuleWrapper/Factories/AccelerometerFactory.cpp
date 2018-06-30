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
    // TODO Auto-generated constructor stub
}

AccelerometerFactory::~AccelerometerFactory()
{
    // TODO Auto-generated destructor stub
}

bool AccelerometerFactory::Create(int aHandle, const std::string& aType)
{
    bool success = true;

    if (aType == "WpiAnalogGyroWrapper")
    {
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::WARN, "Unknown type " << aType);
        success = false;
    }

    return success;
}

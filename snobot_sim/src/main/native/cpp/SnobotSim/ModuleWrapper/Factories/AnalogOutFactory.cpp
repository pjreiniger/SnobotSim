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
    // TODO Auto-generated constructor stub
}

AnalogOutFactory::~AnalogOutFactory()
{
    // TODO Auto-generated destructor stub
}

bool AnalogOutFactory::Create(int aHandle, const std::string& aType)
{
    bool success = true;

    if (aType == "WpiAnalogOutWrapper")
    {
        SensorActuatorRegistry::Get().Register(aHandle,
                std::shared_ptr<IAnalogOutWrapper>(new WpiAnalogOutWrapper(aHandle)));
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::WARN, "Unknown type " << aType);
        success = false;
    }

    return success;
}

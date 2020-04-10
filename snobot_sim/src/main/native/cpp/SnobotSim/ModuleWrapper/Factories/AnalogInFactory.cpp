/*
 * AnalogInFactory.cpp
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/Factories/AnalogInFactory.h"

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiAnalogInWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "SnobotSim/SimulatorComponents/SmartSC/SmartSCAnalogIn.h"

AnalogInFactory::AnalogInFactory()
{
}

AnalogInFactory::~AnalogInFactory()
{
}

bool AnalogInFactory::Create(int aHandle, const std::string& aType)
{
    bool success = true;

    if (aType == WpiAnalogInWrapper::TYPE)
    {
        SensorActuatorRegistry::Get().Register(aHandle,
                std::shared_ptr<IAnalogInWrapper>(new WpiAnalogInWrapper(aHandle)));
    }
    else if (aType == SmartSCAnalogIn::TYPE)
    {
        SensorActuatorRegistry::Get().Register(aHandle,
                std::shared_ptr<IAnalogInWrapper>(new SmartSCAnalogIn(aHandle)));
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown type " << aType);
        success = false;
    }

    return success;
}

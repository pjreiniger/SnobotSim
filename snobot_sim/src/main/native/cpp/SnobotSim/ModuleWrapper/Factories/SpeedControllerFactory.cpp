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
#include "SnobotSim/SimulatorComponents/CtreWrappers/CtreTalonSRXSpeedControllerSim.h"
#include "SnobotSim/SimulatorComponents/RevWrappers/RevSpeedControllerSimWrapper.h"

SpeedControllerFactory::SpeedControllerFactory()
{
}

SpeedControllerFactory::~SpeedControllerFactory()
{
}

bool SpeedControllerFactory::Create(int aHandle, const std::string& aType)
{
    bool success = true;

    if (aType == WpiSpeedControllerWrapper::TYPE)
    {
        SensorActuatorRegistry::Get().Register(aHandle,
                std::shared_ptr<ISpeedControllerWrapper>(new WpiSpeedControllerWrapper(aHandle)));
    }
    else if (aType == CtreTalonSRXSpeedControllerSim::TYPE)
    {
        SensorActuatorRegistry::Get().Register(aHandle,
                std::shared_ptr<ISpeedControllerWrapper>(new CtreTalonSRXSpeedControllerSim(aHandle)));
    }
    else if (aType == RevpeedControllerSim::TYPE)
    {
        SensorActuatorRegistry::Get().Register(aHandle,
                std::shared_ptr<ISpeedControllerWrapper>(new RevpeedControllerSim(aHandle)));
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown type " << aType);
        success = false;
    }

    return success;
}

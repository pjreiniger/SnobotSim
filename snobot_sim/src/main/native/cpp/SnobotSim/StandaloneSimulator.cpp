/*
 * StandaloneSimulator.cpp
 *
 *  Created on: Jul 18, 2018
 *      Author: PJ
 */

#include "SnobotSim/StandaloneSimulator.h"

#include <thread>

#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/HalCallbacks/CallbackSetup.h"
#include "SnobotSim/Logging/SnobotCoutLogger.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/RobotStateSingleton.h"
#include "hal/HAL.h"

void SnobotSim::InitializeStandaloneSim()
{
    static SnobotLogging::SnobotCoutLogger snobotLogger;
    snobotLogger.SetLogLevel(SnobotLogging::LOG_LEVEL_DEBUG);
    SnobotLogging::SetLogger(&snobotLogger);

    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_INFO, "Initializing the simulator");

    if (!HAL_Initialize(500, 0))
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Couldn't initialize the hal");
        return;
    }

    SnobotSim::InitializeSnobotCallbacks();
}

SnobotSim::AStandaloneSimulator::AStandaloneSimulator()
{
}

SnobotSim::AStandaloneSimulator::~AStandaloneSimulator()
{
}

void SnobotSim::AStandaloneSimulator::UpdateSimulatorComponentsThread()
{
    while (true)
    {
        HAL_WaitForDSDataTimeout(.02);
        UpdateSimulatorComponents();
    }
}

void SnobotSim::AStandaloneSimulator::UpdateSimulatorComponents()
{
    RobotStateSingleton::Get().UpdateLoop();

    std::map<int, std::shared_ptr<ISpeedControllerWrapper>>& speedControllers = SensorActuatorRegistry::Get().GetISpeedControllerWrapperMap();
    std::map<int, std::shared_ptr<ISpeedControllerWrapper>>::iterator iter;
    for (iter = speedControllers.begin(); iter != speedControllers.end(); ++iter)
    {
        iter->second->Update(.02);
    }
}

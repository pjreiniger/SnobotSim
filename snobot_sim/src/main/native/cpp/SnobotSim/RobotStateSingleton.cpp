/*
 * RobotStateSingleton.cpp
 *
 *  Created on: May 5, 2017
 *      Author: preiniger
 */

#include "SnobotSim/RobotStateSingleton.h"

#include <chrono>
#include <future>

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "mockdata/DriverStationData.h"

RobotStateSingleton RobotStateSingleton::sINSTANCE;

RobotStateSingleton::RobotStateSingleton()
{
}

RobotStateSingleton::~RobotStateSingleton()
{
    Reset();
}

RobotStateSingleton& RobotStateSingleton::Get()
{
    return sINSTANCE;
}

void RobotStateSingleton::Reset()
{
    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_INFO, "Resetting State Singleton");
}

void RobotStateSingleton::WaitForNextControlLoop(double aWaitTime)
{
    std::this_thread::sleep_for(std::chrono::milliseconds(static_cast<int>(aWaitTime * 1000)));
    HALSIM_NotifyDriverStationNewData();
}

void RobotStateSingleton::UpdateLoop()
{
    std::vector<std::shared_ptr<ISimulatorUpdater>>& comps = SensorActuatorRegistry::Get().GetSimulatorComponents();

    for (unsigned int i = 0; i < comps.size(); ++i)
    {
        comps[i]->Update();
    }
}

double RobotStateSingleton::GetMatchTime() const
{
    if (HALSIM_GetDriverStationEnabled())
    {
        std::chrono::duration<double> diff = std::chrono::system_clock::now() - mTimeEnabled;
        return diff.count();
    }
    return 0;
}

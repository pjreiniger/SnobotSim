/*
 * StartSimulatorMacro.h
 *
 *  Created on: Jul 18, 2018
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_STANDALONE_NATIVE_INCLUDE_STARTSIMULATORMACRO_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_STANDALONE_NATIVE_INCLUDE_STARTSIMULATORMACRO_H_

#include <iostream>

#include "SnobotSim/StandaloneSimulator.h"
#include "SnobotSim/Logging/SnobotLogger.h"

#define START_STANDALONE_SIMULATOR(_ClassName_, _SimulatorName_)                                                               \
    int main()                                                                                                                 \
    {                                                                                                                          \
        SnobotSim::InitializeStandaloneSim();                                                                                  \
        SNOBOT_LOG(SnobotLogging::INFO, "Starting SnobotSim with " << #_ClassName_ << " with simulator " << #_SimulatorName_); \
        static _ClassName_ robot;                                                                                              \
        static _SimulatorName_ simulator;                                                                                      \
        std::thread t(&_SimulatorName_::UpdateSimulatorComponentsThread, simulator);                                           \
        robot.StartCompetition();                                                                                              \
    }

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_STANDALONE_NATIVE_INCLUDE_STARTSIMULATORMACRO_H_

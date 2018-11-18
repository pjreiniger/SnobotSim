/*
 * StartSimulatorMacro.h
 *
 *  Created on: Jul 18, 2018
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_STARTSTANDALONESIMULATORMACRO_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_STARTSTANDALONESIMULATORMACRO_H_

#include <iostream>

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/StandaloneSimulator.h"

#define START_STANDALONE_SIMULATOR(_ClassName_, _SimulatorName_)                                                                         \
    int main()                                                                                                                           \
    {                                                                                                                                    \
        SnobotSim::InitializeStandaloneSim();                                                                                            \
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_INFO, "Starting SnobotSim with " << #_ClassName_ << " with simulator " << #_SimulatorName_); \
        static _ClassName_ robot;                                                                                                        \
        static _SimulatorName_ simulator;                                                                                                \
        std::thread t(&_SimulatorName_::UpdateSimulatorComponentsThread, simulator);                                                     \
        robot.StartCompetition();                                                                                                        \
    }

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_STARTSTANDALONESIMULATORMACRO_H_

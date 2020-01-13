/*
 * StartSimulatorMacro.h
 *
 *  Created on: Jul 18, 2018
 *      Author: PJ
 */

#pragma once

#include <iostream>

#include "SnobotSim/Config/SimulatorConfigReaderV1.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/StandaloneSimulator.h"

#define START_STANDALONE_SIMULATOR(_ClassName_, _SimulatorName_, _SimulatorConfigFile_)                                                                                                           \
    int main()                                                                                                                                                                                    \
    {                                                                                                                                                                                             \
        SnobotSim::InitializeStandaloneSim();                                                                                                                                                     \
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_INFO, "Starting SnobotSim with " << #_ClassName_ << " with simulator " << #_SimulatorName_ << ", and config file '" << _SimulatorConfigFile_ << "'"); \
        SimulatorConfigReaderV1 configReader;                                                                                                                                                     \
        configReader.LoadConfig(_SimulatorConfigFile_);                                                                                                                                           \
        static _ClassName_ robot;                                                                                                                                                                 \
        static _SimulatorName_ simulator;                                                                                                                                                         \
        std::thread t(&_SimulatorName_::UpdateSimulatorComponentsThread, simulator);                                                                                                              \
        robot.StartCompetition();                                                                                                                                                                 \
    }

/*
 * StandaloneSimulator.h
 *
 *  Created on: Jul 18, 2018
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_STANDALONESIMULATOR_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_STANDALONESIMULATOR_H_

namespace SnobotSim
{
class AStandaloneSimulator
{
public:
    AStandaloneSimulator();

    virtual ~AStandaloneSimulator();

    virtual void UpdateSimulatorComponents();

    void UpdateSimulatorComponentsThread();
};

void InitializeStandaloneSim();
} // namespace SnobotSim

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_STANDALONESIMULATOR_H_

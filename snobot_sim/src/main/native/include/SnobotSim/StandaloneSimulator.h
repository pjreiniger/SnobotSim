/*
 * StandaloneSimulator.h
 *
 *  Created on: Jul 18, 2018
 *      Author: PJ
 */

#pragma once

#include "SnobotSim/ExportHelper.h"

namespace SnobotSim
{
class EXPORT_ AStandaloneSimulator
{
public:
    AStandaloneSimulator();

    virtual ~AStandaloneSimulator();

    virtual void UpdateSimulatorComponents();

    void UpdateSimulatorComponentsThread();
};

void EXPORT_ InitializeStandaloneSim();
} // namespace SnobotSim

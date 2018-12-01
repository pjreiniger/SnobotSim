/*
 * SpiNavxWrapper.h
 *
 *  Created on: Nov 23, 2017
 *      Author: PJ
 */

#pragma once
#include "SnobotSim/ModuleWrapper/Interfaces/ISpiWrapper.h"
#include "SnobotSim/SimulatorComponents/NavxWrappers/BaseNavxWrapper.h"

class SpiNavxWrapper : public ISpiWrapper, public BaseNavxWrapper
{
public:
    explicit SpiNavxWrapper(int aPort);
    virtual ~SpiNavxWrapper();
};

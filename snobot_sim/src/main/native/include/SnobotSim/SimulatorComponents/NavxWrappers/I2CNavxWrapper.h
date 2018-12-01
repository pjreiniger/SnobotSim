/*
 * I2CNavxWrapper.h
 *
 *  Created on: Nov 23, 2017
 *      Author: PJ
 */

#pragma once
#include "SnobotSim/ModuleWrapper/Interfaces/II2CWrapper.h"
#include "SnobotSim/SimulatorComponents/NavxWrappers/BaseNavxWrapper.h"

class I2CNavxWrapper : public II2CWrapper, public BaseNavxWrapper
{
public:
    explicit I2CNavxWrapper(int aPort);
    virtual ~I2CNavxWrapper();
};

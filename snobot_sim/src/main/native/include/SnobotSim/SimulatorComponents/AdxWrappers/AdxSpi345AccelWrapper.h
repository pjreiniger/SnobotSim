/*
 * AdxSpi345AccelWrapper.h
 *
 *  Created on: Nov 23, 2017
 *      Author: PJ
 */

#pragma once
#include "SnobotSim/ModuleWrapper/Interfaces/ISpiWrapper.h"
#include "SnobotSim/SimulatorComponents/AdxWrappers/BaseAdxAccelWrapper.h"

class AdxSpi345AccelWrapper : public ISpiWrapper, public BaseAdxAccelWrapper
{
public:
    explicit AdxSpi345AccelWrapper(int aPort);
    virtual ~AdxSpi345AccelWrapper();
};

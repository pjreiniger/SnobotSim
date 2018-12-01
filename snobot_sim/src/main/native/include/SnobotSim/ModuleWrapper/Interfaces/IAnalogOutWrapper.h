/*
 * IAnalogInWrapper.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#pragma once

#include "SnobotSim/ModuleWrapper/Interfaces/ISensorWrapper.h"

class IAnalogOutWrapper : public virtual ISensorWrapper
{
public:
    virtual void SetVoltage(double aVoltage) = 0;
    virtual double GetVoltage() = 0;
};

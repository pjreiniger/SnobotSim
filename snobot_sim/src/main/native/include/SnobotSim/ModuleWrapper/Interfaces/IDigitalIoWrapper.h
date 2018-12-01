/*
 * IAnalogInWrapper.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#pragma once

#include "SnobotSim/ModuleWrapper/Interfaces/ISensorWrapper.h"

class IDigitalIoWrapper : public virtual ISensorWrapper
{
public:
    virtual bool Get() = 0;
    virtual void Set(bool aState) = 0;
};

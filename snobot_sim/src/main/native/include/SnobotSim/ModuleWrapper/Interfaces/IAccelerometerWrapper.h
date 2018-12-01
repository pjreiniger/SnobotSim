/*
 * AccelerometerWrapper.h
 *
 *  Created on: May 7, 2017
 *      Author: PJ
 */

#pragma once

#include "SnobotSim/ModuleWrapper/Interfaces/ISensorWrapper.h"

class IAccelerometerWrapper : public virtual ISensorWrapper
{
public:
    virtual ~IAccelerometerWrapper()
    {
    }

    virtual void SetAcceleration(double aAcceleration) = 0;

    virtual double GetAcceleration() = 0;
};

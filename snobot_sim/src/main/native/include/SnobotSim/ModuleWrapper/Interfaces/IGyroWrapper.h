/*
 * IGyroWrapper.hpp
 *
 *  Created on: May 5, 2017
 *      Author: PJ
 */
#pragma once

#include "SnobotSim/ModuleWrapper/Interfaces/ISensorWrapper.h"

class IGyroWrapper : public virtual ISensorWrapper
{
public:
    virtual void SetAngle(double aAngle) = 0;

    virtual double GetAngle() = 0;
};

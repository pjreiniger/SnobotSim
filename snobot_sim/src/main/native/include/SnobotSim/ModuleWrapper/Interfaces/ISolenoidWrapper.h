/*
 * ISolenoidWrapper.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#pragma once

#include "SnobotSim/ModuleWrapper/Interfaces/ISensorWrapper.h"

class ISolenoidWrapper : public virtual ISensorWrapper
{
public:
    virtual void SetState(bool aOn) = 0;

    virtual bool GetState() = 0;
};

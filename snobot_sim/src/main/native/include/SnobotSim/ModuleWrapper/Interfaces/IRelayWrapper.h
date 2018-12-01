/*
 * IRelayWrapper.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#pragma once

#include "SnobotSim/ModuleWrapper/Interfaces/ISensorWrapper.h"

class IRelayWrapper : public virtual ISensorWrapper
{
public:
    virtual bool GetRelayForwards() = 0;
    virtual bool GetRelayReverse() = 0;
};

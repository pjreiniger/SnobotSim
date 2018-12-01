/*
 * IEncoderWrapper.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#pragma once

#include <memory>

#include "SnobotSim/ModuleWrapper/Interfaces/ISensorWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISpeedControllerWrapper.h"

class IEncoderWrapper : public virtual ISensorWrapper
{
public:
    virtual void Reset() = 0;

    virtual double GetDistance() = 0;

    virtual double GetVelocity() = 0;

    virtual void SetSpeedController(const std::shared_ptr<ISpeedControllerWrapper>& aMotorWrapper) = 0;

    virtual const std::shared_ptr<ISpeedControllerWrapper>& GetSpeedController() = 0;

    virtual bool IsHookedUp() = 0;
};

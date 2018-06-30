/*
 * IEncoderWrapper.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IENCODERWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IENCODERWRAPPER_H_

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

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IENCODERWRAPPER_H_

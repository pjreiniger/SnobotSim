/*
 * AccelerometerWrapper.h
 *
 *  Created on: May 7, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IACCELEROMETERWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IACCELEROMETERWRAPPER_H_

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

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IACCELEROMETERWRAPPER_H_

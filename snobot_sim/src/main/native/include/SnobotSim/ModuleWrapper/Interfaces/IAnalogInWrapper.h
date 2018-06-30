/*
 * IAnalogInWrapper.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IANALOGINWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IANALOGINWRAPPER_H_

#include "SnobotSim/ModuleWrapper/Interfaces/ISensorWrapper.h"

class IAnalogInWrapper : public virtual ISensorWrapper
{
public:
    virtual void SetVoltage(double aVoltage) = 0;
    virtual double GetVoltage() = 0;
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IANALOGINWRAPPER_H_

/*
 * IAnalogInWrapper.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IDIGITALIOWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IDIGITALIOWRAPPER_H_

#include "SnobotSim/ModuleWrapper/Interfaces/ISensorWrapper.h"

class IDigitalIoWrapper : public virtual ISensorWrapper
{
public:
    virtual bool Get() = 0;
    virtual void Set(bool aState) = 0;
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IDIGITALIOWRAPPER_H_

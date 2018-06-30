/*
 * IRelayWrapper.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IRELAYWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IRELAYWRAPPER_H_

#include "SnobotSim/ModuleWrapper/Interfaces/ISensorWrapper.h"

class IRelayWrapper : public virtual ISensorWrapper
{
public:
    virtual bool GetRelayForwards() = 0;
    virtual bool GetRelayReverse() = 0;
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_IRELAYWRAPPER_H_

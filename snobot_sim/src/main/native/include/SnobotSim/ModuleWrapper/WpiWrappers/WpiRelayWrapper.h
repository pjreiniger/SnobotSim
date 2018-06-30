/*
 * RelayWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPIRELAYWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPIRELAYWRAPPER_H_

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IRelayWrapper.h"

class WpiRelayWrapper : public AModuleWrapper, public IRelayWrapper
{
public:
    explicit WpiRelayWrapper(int aPort);
    virtual ~WpiRelayWrapper();

    bool GetRelayForwards() override;
    bool GetRelayReverse() override;

protected:
    const int mHandle;
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPIRELAYWRAPPER_H_

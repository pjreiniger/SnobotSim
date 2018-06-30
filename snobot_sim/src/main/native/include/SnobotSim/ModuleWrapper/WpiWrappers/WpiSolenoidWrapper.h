/*
 * SolenoidWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPISOLENOIDWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPISOLENOIDWRAPPER_H_

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISolenoidWrapper.h"

class WpiSolenoidWrapper : public AModuleWrapper, public ISolenoidWrapper
{
public:
    explicit WpiSolenoidWrapper(int aPort);
    virtual ~WpiSolenoidWrapper();

    void SetState(bool aOn) override;

    bool GetState() override;

protected:
    const int mModule;
    const int mChannel;
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPISOLENOIDWRAPPER_H_

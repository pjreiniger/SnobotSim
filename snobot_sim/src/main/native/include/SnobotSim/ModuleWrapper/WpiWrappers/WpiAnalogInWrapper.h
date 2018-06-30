/*
 * AnalogSourceWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPIANALOGINWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPIANALOGINWRAPPER_H_

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAnalogInWrapper.h"

class WpiAnalogInWrapper : public AModuleWrapper, public IAnalogInWrapper
{
public:
    explicit WpiAnalogInWrapper(int aPort);
    virtual ~WpiAnalogInWrapper();

    void SetVoltage(double aVoltage) override;
    double GetVoltage() override;

protected:
    const int mHandle;
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPIANALOGINWRAPPER_H_

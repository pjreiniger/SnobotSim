/*
 * WpiSolenoidWrapper.cpp
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiSolenoidWrapper.h"

#include "SnobotSim/PortUnwrapper.h"
#include "mockdata/PCMData.h"

const std::string WpiSolenoidWrapper::TYPE = "com.snobot.simulator.module_wrapper.wpi.WpiSolenoidWrapper";

WpiSolenoidWrapper::WpiSolenoidWrapper(int aPort) :
        AModuleWrapper("Solenoid " + std::to_string(UnwrapPort(aPort))),
        mModule(aPort / 8),
        mChannel(aPort % 8)
{
}

WpiSolenoidWrapper::~WpiSolenoidWrapper()
{
}

void WpiSolenoidWrapper::SetState(bool aOn)
{
    HALSIM_SetPCMSolenoidOutput(mModule, mChannel, aOn);
}

bool WpiSolenoidWrapper::GetState()
{
    return HALSIM_GetPCMSolenoidOutput(mModule, mChannel);
}

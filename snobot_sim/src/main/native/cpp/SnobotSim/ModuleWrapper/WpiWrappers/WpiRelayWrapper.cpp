/*
 * RelayWrapper.cpp
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiRelayWrapper.h"

#include "SnobotSim/PortUnwrapper.h"
#include "mockdata/RelayData.h"

const std::string WpiRelayWrapper::TYPE = "com.snobot.simulator.module_wrapper.wpi.WpiRelayWrapper";

WpiRelayWrapper::WpiRelayWrapper(int aPort) :
        AModuleWrapper("Relay " + std::to_string(UnwrapPort(aPort))),
        mHandle(aPort)
{
}

WpiRelayWrapper::~WpiRelayWrapper()
{
}

bool WpiRelayWrapper::GetRelayForwards()
{
    return HALSIM_GetRelayForward(mHandle);
}

bool WpiRelayWrapper::GetRelayReverse()
{
    return HALSIM_GetRelayReverse(mHandle);
}

/*
 * RelayWrapper.cpp
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiRelayWrapper.h"

#include "MockData/RelayData.h"
#include "SnobotSim/PortUnwrapper.h"

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

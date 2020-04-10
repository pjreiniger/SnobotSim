/*
 * DigitalSourceWrapper.cpp
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiDigitalIoWrapper.h"

#include "SnobotSim/PortUnwrapper.h"
#include "mockdata/DIOData.h"

const std::string WpiDigitalIoWrapper::TYPE = "com.snobot.simulator.module_wrapper.wpi.WpiDigitalIoWrapper";

WpiDigitalIoWrapper::WpiDigitalIoWrapper(int aPort) :
        AModuleWrapper("Digital IO " + std::to_string(UnwrapPort(aPort))),
        mHandle(aPort)
{
}

WpiDigitalIoWrapper::~WpiDigitalIoWrapper()
{
}

bool WpiDigitalIoWrapper::Get()
{
    return HALSIM_GetDIOValue(mHandle);
}

void WpiDigitalIoWrapper::Set(bool aState)
{
    HALSIM_SetDIOValue(mHandle, aState);
}

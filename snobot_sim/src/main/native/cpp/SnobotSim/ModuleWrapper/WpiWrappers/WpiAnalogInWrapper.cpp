/*
 * AnalogSourceWrapper.cpp
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiAnalogInWrapper.h"

#include "SnobotSim/PortUnwrapper.h"
#include "mockdata/AnalogInData.h"

WpiAnalogInWrapper::WpiAnalogInWrapper(int aPort) :
        AModuleWrapper("Analog In " + std::to_string(UnwrapPort(aPort))),
        mHandle(aPort)
{
}

WpiAnalogInWrapper::~WpiAnalogInWrapper()
{
}

void WpiAnalogInWrapper::SetVoltage(double aVoltage)
{
    HALSIM_SetAnalogInVoltage(mHandle, aVoltage);
}

double WpiAnalogInWrapper::GetVoltage()
{
    return HALSIM_GetAnalogInVoltage(mHandle);
}

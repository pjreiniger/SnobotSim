/*
 * AnalogSourceWrapper.cpp
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiAnalogOutWrapper.h"

#include "MockData/AnalogOutData.h"
#include "SnobotSim/PortUnwrapper.h"

WpiAnalogOutWrapper::WpiAnalogOutWrapper(int aPort) :
        AModuleWrapper("Analog Out " + std::to_string(UnwrapPort(aPort))),
        mHandle(aPort)
{
}

WpiAnalogOutWrapper::~WpiAnalogOutWrapper()
{
}

void WpiAnalogOutWrapper::SetVoltage(double aVoltage)
{
    HALSIM_SetAnalogOutVoltage(mHandle, aVoltage);
}

double WpiAnalogOutWrapper::GetVoltage()
{
    return HALSIM_GetAnalogOutVoltage(mHandle);
}

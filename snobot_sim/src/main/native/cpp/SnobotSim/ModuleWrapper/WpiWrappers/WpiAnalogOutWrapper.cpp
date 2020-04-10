/*
 * AnalogSourceWrapper.cpp
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiAnalogOutWrapper.h"

#include "SnobotSim/PortUnwrapper.h"
#include "mockdata/AnalogOutData.h"

const std::string WpiAnalogOutWrapper::TYPE = "com.snobot.simulator.module_wrapper.wpi.WpiAnalogOutWrapper";

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

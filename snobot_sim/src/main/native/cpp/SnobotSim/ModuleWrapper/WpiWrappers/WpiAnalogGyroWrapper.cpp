/*
 * AnalogGyrWrapper.cpp
 *
 *  Created on: May 5, 2017
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiAnalogGyroWrapper.h"

#include "mockdata/AnalogGyroData.h"

const std::string WpiAnalogGyroWrapper::TYPE = "com.snobot.simulator.module_wrapper.wpi.WpiAnalogGyroWrapper";

WpiAnalogGyroWrapper::WpiAnalogGyroWrapper(int aPort) :
        AModuleWrapper("Analog Gyro"),
        mHandle(aPort)
{
}

WpiAnalogGyroWrapper::~WpiAnalogGyroWrapper()
{
}

void WpiAnalogGyroWrapper::SetAngle(double aAngle)
{
    HALSIM_SetAnalogGyroAngle(mHandle, aAngle);
}

double WpiAnalogGyroWrapper::GetAngle()
{
    return HALSIM_GetAnalogGyroAngle(mHandle);
}

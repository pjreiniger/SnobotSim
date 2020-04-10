/*
 * AdxGyroWrapper.cpp
 *
 *  Created on: Nov 23, 2017
 *      Author: PJ
 */

#include "SnobotSim/SimulatorComponents/AdxWrappers/AdxGyroWrapper.h"

const std::string AdxGyroWrapper::TYPE = "com.snobot.simulator.module_wrapper.wpi.WpiAnalogGyroWrapper";

AdxGyroWrapper::AdxGyroWrapper(const std::string& aBaseName, int aPort) :
        AModuleWrapper("Gyro"),
        mSimWrapper(aBaseName, "Angle")
{
}

AdxGyroWrapper::~AdxGyroWrapper()
{
}

void AdxGyroWrapper::SetAngle(double aAngle)
{
    mSimWrapper.set(aAngle);
}

double AdxGyroWrapper::GetAngle()
{
    return mSimWrapper.get();
}

/*
 * WpiSpeedControllerWrapper.cpp
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiSpeedControllerWrapper.h"

#include "SnobotSim/PortUnwrapper.h"

const std::string WpiSpeedControllerWrapper::TYPE = "com.snobot.simulator.module_wrapper.wpi.WpiPwmWrapper";

WpiSpeedControllerWrapper::WpiSpeedControllerWrapper(int aPort) :
        BaseSpeedControllerWrapper("Speed Controller " + std::to_string(UnwrapPort(aPort)), aPort)
{
}

WpiSpeedControllerWrapper::~WpiSpeedControllerWrapper()
{
}

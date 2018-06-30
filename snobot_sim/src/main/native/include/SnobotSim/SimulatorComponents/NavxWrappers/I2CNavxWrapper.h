/*
 * I2CNavxWrapper.h
 *
 *  Created on: Nov 23, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_NAVXWRAPPERS_I2CNAVXWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_NAVXWRAPPERS_I2CNAVXWRAPPER_H_

#include "SnobotSim/ModuleWrapper/Interfaces/II2CWrapper.h"
#include "SnobotSim/SimulatorComponents/NavxWrappers/BaseNavxWrapper.h"

class I2CNavxWrapper : public II2CWrapper, public BaseNavxWrapper
{
public:
    explicit I2CNavxWrapper(int aPort);
    virtual ~I2CNavxWrapper();
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_NAVXWRAPPERS_I2CNAVXWRAPPER_H_

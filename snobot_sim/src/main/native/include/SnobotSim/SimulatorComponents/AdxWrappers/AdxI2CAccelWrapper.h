/*
 * AdxI2CAccelWrapper.h
 *
 *  Created on: Nov 23, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_ADXWRAPPERS_ADXI2CACCELWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_ADXWRAPPERS_ADXI2CACCELWRAPPER_H_

#include <memory>

#include "SnobotSim/ModuleWrapper/Interfaces/II2CWrapper.h"
#include "SnobotSim/SimulatorComponents/AdxWrappers/BaseAdxAccelWrapper.h"

class AdxI2CAccelWrapper : public II2CWrapper, public BaseAdxAccelWrapper
{
public:
    explicit AdxI2CAccelWrapper(int aPort);
    virtual ~AdxI2CAccelWrapper();
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_ADXWRAPPERS_ADXI2CACCELWRAPPER_H_

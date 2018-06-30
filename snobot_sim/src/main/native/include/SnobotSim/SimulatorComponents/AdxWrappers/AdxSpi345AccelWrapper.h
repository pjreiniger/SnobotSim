/*
 * AdxSpi345AccelWrapper.h
 *
 *  Created on: Nov 23, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_ADXWRAPPERS_ADXSPI345ACCELWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_ADXWRAPPERS_ADXSPI345ACCELWRAPPER_H_

#include "SnobotSim/ModuleWrapper/Interfaces/ISpiWrapper.h"
#include "SnobotSim/SimulatorComponents/AdxWrappers/BaseAdxAccelWrapper.h"

class AdxSpi345AccelWrapper : public ISpiWrapper, public BaseAdxAccelWrapper
{
public:
    explicit AdxSpi345AccelWrapper(int aPort);
    virtual ~AdxSpi345AccelWrapper();
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_ADXWRAPPERS_ADXSPI345ACCELWRAPPER_H_

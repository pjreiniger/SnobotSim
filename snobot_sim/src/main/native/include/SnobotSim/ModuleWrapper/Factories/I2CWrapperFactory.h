/*
 * I2CWrapperFactory.h
 *
 *  Created on: Sep 11, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_FACTORIES_I2CWRAPPERFACTORY_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_FACTORIES_I2CWRAPPERFACTORY_H_

#include <map>
#include <memory>
#include <string>

#include "SnobotSim/ExportHelper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/II2CWrapper.h"

class EXPORT_ I2CWrapperFactory
{
public:
    static const std::string I2C_ACCELEROMETER_NAME;
    static const std::string NAVX;

    I2CWrapperFactory();
    virtual ~I2CWrapperFactory();

    std::shared_ptr<II2CWrapper> GetI2CWrapper(int aPort);

    void RegisterDefaultWrapperType(int aPort, const std::string& aWrapperType);
    void ResetDefaults();

protected:
    std::shared_ptr<II2CWrapper> CreateWrapper(int aPort, const std::string& aType);

    std::map<int, std::string> mDefaultsMap;
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_FACTORIES_I2CWRAPPERFACTORY_H_

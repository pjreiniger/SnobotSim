/*
 * SpiWrapperFactory.h
 *
 *  Created on: Sep 11, 2017
 *      Author: PJ
 */

#pragma once

#include <map>
#include <memory>
#include <string>

#include "SnobotSim/ExportHelper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISpiWrapper.h"

class EXPORT_ SpiWrapperFactory
{
public:
    static const std::string ADXRS450_GYRO_NAME;
    static const std::string ADXL345_ACCELEROMETER_NAME;
    static const std::string ADXL362_ACCELEROMETER_NAME;
    static const std::string NAVX;

    SpiWrapperFactory();
    virtual ~SpiWrapperFactory();

    std::shared_ptr<ISpiWrapper> GetSpiWrapper(int aPort);

    void RegisterDefaultWrapperType(int aPort, const std::string& aWrapperType);
    void ResetDefaults();

protected:
    std::shared_ptr<ISpiWrapper> CreateWrapper(int aPort, const std::string& aType);

    std::map<int, std::string> mDefaultsMap;
};

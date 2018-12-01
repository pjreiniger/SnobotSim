/*
 * I2CWrapperFactory.cpp
 *
 *  Created on: Sep 11, 2017
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/Factories/I2CWrapperFactory.h"

#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/SimulatorComponents/AdxWrappers/AdxI2CAccelWrapper.h"
#include "SnobotSim/SimulatorComponents/NavxWrappers/I2CNavxWrapper.h"

const std::string I2CWrapperFactory::I2C_ACCELEROMETER_NAME = "ADXL345";
const std::string I2CWrapperFactory::NAVX = "NavX";

I2CWrapperFactory::I2CWrapperFactory()
{
}

I2CWrapperFactory::~I2CWrapperFactory()
{
}

void I2CWrapperFactory::RegisterDefaultWrapperType(int aPort, const std::string& aWrapperType)
{
    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_DEBUG, "Setting default for port " << aPort << " to '" << aWrapperType);
    mDefaultsMap[aPort] = aWrapperType;
}

void I2CWrapperFactory::ResetDefaults()
{
    mDefaultsMap.clear();
}

std::shared_ptr<II2CWrapper> I2CWrapperFactory::GetI2CWrapper(int aPort)
{
    std::shared_ptr<II2CWrapper> i2cWrapper = GetSensorActuatorHelper::GetII2CWrapper(aPort);
    if (i2cWrapper)
    {
        // Already exists, and there is no auto-discovery so just return that, even if it is null
    }
    // This must be an "initialize" call, it will be registered outside of this
    else
    {
        std::map<int, std::string>::iterator iter = mDefaultsMap.find(aPort);
        if (iter != mDefaultsMap.end())
        {
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_DEBUG, "Using specified default '" << iter->second << "' on port " << aPort);
            i2cWrapper = CreateWrapper(aPort, iter->second);
        }
        else
        {
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "No default specified for " << aPort << " using null wrapper");
            i2cWrapper = std::shared_ptr<II2CWrapper>();
        }
    }

    return i2cWrapper;
}

std::shared_ptr<II2CWrapper> I2CWrapperFactory::CreateWrapper(int aPort, const std::string& aType)
{
    if (aType == NAVX)
    {
        return std::shared_ptr<II2CWrapper>(new I2CNavxWrapper(aPort));
    }

    if (aType == I2C_ACCELEROMETER_NAME)
    {
        return std::shared_ptr<II2CWrapper>(new AdxI2CAccelWrapper(aPort));
    }

    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Unknown simulator type '" << aType << "', defaulting to null");
    return std::shared_ptr<II2CWrapper>();
}

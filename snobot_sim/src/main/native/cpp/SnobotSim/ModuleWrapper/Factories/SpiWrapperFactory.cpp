/*
 * SpiWrapperFactory.cpp
 *
 *  Created on: Sep 11, 2017
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/Factories/SpiWrapperFactory.h"

#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/SimulatorComponents/AdxWrappers/AdxGyroWrapper.h"
#include "SnobotSim/SimulatorComponents/AdxWrappers/AdxSpi345AccelWrapper.h"
#include "SnobotSim/SimulatorComponents/AdxWrappers/AdxSpi362AccelWrapper.h"
#include "SnobotSim/SimulatorComponents/NavxWrappers/SpiNavxWrapper.h"

const std::string SpiWrapperFactory::ADXRS450_GYRO_NAME = "ADXRS450";
const std::string SpiWrapperFactory::ADXL345_ACCELEROMETER_NAME = "ADXL345";
const std::string SpiWrapperFactory::ADXL362_ACCELEROMETER_NAME = "ADXL362";
const std::string SpiWrapperFactory::NAVX = "NavX";

SpiWrapperFactory::SpiWrapperFactory()
{
}

SpiWrapperFactory::~SpiWrapperFactory()
{
}

void SpiWrapperFactory::RegisterDefaultWrapperType(int aPort, const std::string& aWrapperType)
{
    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_DEBUG, "Setting default for port " << aPort << " to '" << aWrapperType);
    mDefaultsMap[aPort] = aWrapperType;
}

void SpiWrapperFactory::ResetDefaults()
{
    mDefaultsMap.clear();
}

std::shared_ptr<ISpiWrapper> SpiWrapperFactory::GetSpiWrapper(int aPort)
{
    std::shared_ptr<ISpiWrapper> spiWrapper = GetSensorActuatorHelper::GetISpiWrapper(aPort);
    if (spiWrapper)
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
            spiWrapper = CreateWrapper(aPort, iter->second);
        }
        else
        {
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "No default specified for " << aPort << ", using null wrapper");
            spiWrapper = std::shared_ptr<ISpiWrapper>();
        }
    }

    return spiWrapper;
}

std::shared_ptr<ISpiWrapper> SpiWrapperFactory::CreateWrapper(int aPort, const std::string& aType)
{
    if (aType == NAVX)
    {
        return std::shared_ptr<ISpiWrapper>(new SpiNavxWrapper(aPort));
    }

    if (aType == ADXRS450_GYRO_NAME)
    {
        std::shared_ptr<AdxGyroWrapper> spiGyro(new AdxGyroWrapper(aPort));
        SensorActuatorRegistry::Get().Register(aPort + 100, std::shared_ptr<IGyroWrapper>(spiGyro));

        return spiGyro;
    }
    else if (aType == ADXL345_ACCELEROMETER_NAME)
    {
        return std::shared_ptr<ISpiWrapper>(new AdxSpi345AccelWrapper(aPort));
    }
    else if (aType == ADXL362_ACCELEROMETER_NAME)
    {
        return std::shared_ptr<ISpiWrapper>(new AdxSpi362AccelWrapper(aPort));
    }

    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Unknown simulator type '" << aType << "', defaulting to null");
    return std::shared_ptr<ISpiWrapper>();
}

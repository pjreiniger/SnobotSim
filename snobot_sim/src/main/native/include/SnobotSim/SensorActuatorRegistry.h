/*
 * SensorActuatorRegistry.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SENSORACTUATORREGISTRY_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SENSORACTUATORREGISTRY_H_

#include <map>
#include <memory>
#include <string>
#include <vector>

#include "SnobotSim/ExportHelper.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAccelerometerWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAnalogInWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAnalogOutWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IDigitalIoWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IEncoderWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IGyroWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/II2CWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IRelayWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISolenoidWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISpeedControllerWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISpiWrapper.h"
#include "SnobotSim/SimulatorComponents/ISimulatorUpdater.h"
#include "SnobotSim/StackHelper/StackTraceHelper.h"

class CompressorWrapper;

#define ACTUATOR_GETTERS(ItemType)                                              \
    bool Register(int aPort, const std::shared_ptr<ItemType>& aActuator);       \
    std::shared_ptr<ItemType> Get##ItemType(int aPort, bool aLogError = true);  \
    const std::map<int, std::shared_ptr<ItemType>>& Get##ItemType##Map() const; \
    std::map<int, std::shared_ptr<ItemType>>& Get##ItemType##Map();

class EXPORT_ SensorActuatorRegistry
{
private:
    SensorActuatorRegistry();
    static SensorActuatorRegistry* sInstance;

public:
    virtual ~SensorActuatorRegistry();

    static SensorActuatorRegistry& Get();

    void AddSimulatorComponent(const std::shared_ptr<ISimulatorUpdater>& aSimulatorComponent);

    void Reset();

    std::vector<std::shared_ptr<ISimulatorUpdater>>& GetSimulatorComponents();

    std::shared_ptr<CompressorWrapper> GetCompressorWrapper();

    ACTUATOR_GETTERS(ISpeedControllerWrapper)
    ACTUATOR_GETTERS(IRelayWrapper)
    ACTUATOR_GETTERS(IAnalogInWrapper)
    ACTUATOR_GETTERS(IAnalogOutWrapper)
    ACTUATOR_GETTERS(IDigitalIoWrapper)
    ACTUATOR_GETTERS(ISolenoidWrapper)
    ACTUATOR_GETTERS(IEncoderWrapper)
    ACTUATOR_GETTERS(IAccelerometerWrapper)
    ACTUATOR_GETTERS(IGyroWrapper)
    ACTUATOR_GETTERS(ISpiWrapper)
    ACTUATOR_GETTERS(II2CWrapper)

protected:
    template <typename ItemType>
    bool RegisterItem(int aPort, std::shared_ptr<ItemType> aItem,
            std::map<int, std::shared_ptr<ItemType>>& aMap, const std::string& aType,
            bool aOverwriteOnConflict)
    {
        if (aMap.find(aPort) != aMap.end())
        {
            if (aOverwriteOnConflict)
            {
                SNOBOT_LOG(SnobotLogging::LOG_LEVEL_INFO, "Overwriting registration of " << aType << " on port " << aPort);
            }
            else
            {
                SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Type " << aType << " already has registered item on port " << aPort << ".");
                return false;
            }
        }

        aMap[aPort] = aItem;

        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_DEBUG, "Registered " << aType << " on port " << aPort << ".  The map has " << aMap.size() << " elements.")

        return true;
    }

    template <typename ItemType>
    std::shared_ptr<ItemType> GetItem(int aPort,
            const std::map<int, std::shared_ptr<ItemType>>& aMap, const std::string& aType, bool logError)
    {
        typename std::map<int, std::shared_ptr<ItemType>>::const_iterator iter = aMap.find(aPort);
        if (iter == aMap.end())
        {
            if (logError)
            {
                SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Unregistered " << aType << " on port " << aPort << ".  Map has " << aMap.size() << " elements.")
                StackTraceHelper::PrintStackTracker();
            }
            return std::shared_ptr<ItemType>();
        }

        return iter->second;
    }

    std::map<int, std::shared_ptr<ISpeedControllerWrapper>> mISpeedControllerWrapperMap;
    std::map<int, std::shared_ptr<IRelayWrapper>> mIRelayWrapperMap;
    std::map<int, std::shared_ptr<IDigitalIoWrapper>> mIDigitalIoWrapperMap;
    std::map<int, std::shared_ptr<IAnalogInWrapper>> mIAnalogInWrapperMap;
    std::map<int, std::shared_ptr<IAnalogOutWrapper>> mIAnalogOutWrapperMap;
    std::map<int, std::shared_ptr<ISolenoidWrapper>> mISolenoidWrapperMap;
    std::map<int, std::shared_ptr<IEncoderWrapper>> mIEncoderWrapperMap;
    std::map<int, std::shared_ptr<IGyroWrapper>> mIGyroWrapperMap;
    std::map<int, std::shared_ptr<IAccelerometerWrapper>> mIAccelerometerWrapperMap;
    std::map<int, std::shared_ptr<ISpiWrapper>> mISpiWrapperMap;
    std::map<int, std::shared_ptr<II2CWrapper>> mII2CWrapperMap;

    std::shared_ptr<CompressorWrapper> mCompressor;

    std::vector<std::shared_ptr<ISimulatorUpdater>> mSimulatorComponents;
};

#undef ACTUATOR_GETTERS

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SENSORACTUATORREGISTRY_H_

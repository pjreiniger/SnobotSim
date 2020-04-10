/*
 * SensorActuatorRegistry.cpp
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#include "SnobotSim/SensorActuatorRegistry.h"

#include "SnobotSim/SimulatorComponents/CompressorWrapper.h"
#include "hal/handles/HandlesInternal.h"

SensorActuatorRegistry* SensorActuatorRegistry::sInstance = new SensorActuatorRegistry();

SensorActuatorRegistry& SensorActuatorRegistry::Get()
{
    // std::cout << "SAR: " << sInstance << std::endl;
    return *sInstance;
}

SensorActuatorRegistry::SensorActuatorRegistry() :
        mCompressor(new CompressorWrapper)
{
}

SensorActuatorRegistry::~SensorActuatorRegistry()
{
    Reset();
}

void SensorActuatorRegistry::Reset()
{
    hal::HandleBase::ResetGlobalHandles();

    mISpeedControllerWrapperMap.clear();
    mIRelayWrapperMap.clear();
    mIDigitalIoWrapperMap.clear();
    mIAnalogInWrapperMap.clear();
    mIAnalogOutWrapperMap.clear();
    mISolenoidWrapperMap.clear();
    mIEncoderWrapperMap.clear();
    mIGyroWrapperMap.clear();
    mIAccelerometerWrapperMap.clear();
    mISpiWrapperMap.clear();
    mII2CWrapperMap.clear();

    mSimulatorComponents.clear();
}

std::shared_ptr<CompressorWrapper> SensorActuatorRegistry::GetCompressorWrapper()
{
    return mCompressor;
}

void SensorActuatorRegistry::AddSimulatorComponent(const std::shared_ptr<ISimulatorUpdater>& aSimulatorComponent)
{
    mSimulatorComponents.push_back(aSimulatorComponent);
}

std::vector<std::shared_ptr<ISimulatorUpdater>>& SensorActuatorRegistry::GetSimulatorComponents()
{
    return mSimulatorComponents;
}

#define ACTUATOR_GETTERS(ItemType, aOverwriteOnConflict)                                               \
    bool SensorActuatorRegistry::Register(int aPort, const std::shared_ptr<ItemType>& aActuator)       \
    {                                                                                                  \
        return RegisterItem(aPort, aActuator, m##ItemType##Map, #ItemType, aOverwriteOnConflict);      \
    }                                                                                                  \
    std::shared_ptr<ItemType> SensorActuatorRegistry::Get##ItemType(int aPort, bool aLogError)         \
    {                                                                                                  \
        return GetItem(aPort, m##ItemType##Map, #ItemType, aLogError);                                 \
    }                                                                                                  \
    const std::map<int, std::shared_ptr<ItemType>>& SensorActuatorRegistry::Get##ItemType##Map() const \
    {                                                                                                  \
        return m##ItemType##Map;                                                                       \
    }                                                                                                  \
    std::map<int, std::shared_ptr<ItemType>>& SensorActuatorRegistry::Get##ItemType##Map()             \
    {                                                                                                  \
        return m##ItemType##Map;                                                                       \
    }

ACTUATOR_GETTERS(ISpeedControllerWrapper, false)
ACTUATOR_GETTERS(IRelayWrapper, false)
ACTUATOR_GETTERS(IDigitalIoWrapper, false)
ACTUATOR_GETTERS(IAnalogInWrapper, false)
ACTUATOR_GETTERS(IAnalogOutWrapper, false)
ACTUATOR_GETTERS(ISolenoidWrapper, false)
ACTUATOR_GETTERS(IEncoderWrapper, false)
ACTUATOR_GETTERS(IAccelerometerWrapper, false)
ACTUATOR_GETTERS(IGyroWrapper, false)
ACTUATOR_GETTERS(ISpiWrapper, true)
ACTUATOR_GETTERS(II2CWrapper, true)


#include "SnobotSim/HalCallbacks/AnalogIOCallbacks.h"

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiAnalogInWrapper.h"
#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiAnalogOutWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "hal/Ports.h"
#include "mockdata/AnalogInData.h"
#include "mockdata/AnalogOutData.h"

void AnalogInCallback(const char* name, void* param, const struct HAL_Value* value)
{
    std::string nameStr = name;
    int port = *reinterpret_cast<int*>(param);

    if (nameStr == "Initialized")
    {
        if (!SensorActuatorRegistry::Get().GetIAnalogInWrapper(port, false))
        {
            FactoryContainer::Get().GetAnalogInFactory()->Create(port, "WpiAnalogInWrapper");
        }
        SensorActuatorRegistry::Get().GetIAnalogInWrapper(port)->SetInitialized(true);
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown name " << nameStr);
    }
}

void AnalogOutCallback(const char* name, void* param, const struct HAL_Value* value)
{
    std::string nameStr = name;
    int port = *reinterpret_cast<int*>(param);

    if (nameStr == "Initialized")
    {
        if (!SensorActuatorRegistry::Get().GetIAnalogOutWrapper(port, false))
        {
            FactoryContainer::Get().GetAnalogOutFactory()->Create(port, "WpiAnalogOutWrapper");
        }
        SensorActuatorRegistry::Get().GetIAnalogOutWrapper(port)->SetInitialized(true);
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown name " << nameStr);
    }
}

int gAnalogInArrayIndices[26];
int gAnalogOutArrayIndices[26];

void SnobotSim::InitializeAnalogIOCallbacks()
{
    for (int i = 0; i < HAL_GetNumAnalogInputs(); ++i)
    {
        gAnalogInArrayIndices[i] = i;
        HALSIM_RegisterAnalogInInitializedCallback(i, &AnalogInCallback, &gAnalogInArrayIndices[i], false);
    }

    for (int i = 0; i < HAL_GetNumAnalogOutputs(); ++i)
    {
        gAnalogOutArrayIndices[i] = i;
        HALSIM_RegisterAnalogOutInitializedCallback(i, &AnalogOutCallback, &gAnalogOutArrayIndices[i], false);
    }
}

void SnobotSim::ResetAnalogIOCallbacks()
{
    for (int i = 0; i < HAL_GetNumAnalogInputs(); ++i)
    {
        HALSIM_ResetAnalogInData(i);
    }
    for (int i = 0; i < HAL_GetNumAnalogOutputs(); ++i)
    {
        HALSIM_ResetAnalogOutData(i);
    }
}

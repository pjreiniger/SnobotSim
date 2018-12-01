

#include "SnobotSim/HalCallbacks/RelayCallbacks.h"

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiRelayWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "hal/Ports.h"
#include "mockdata/RelayData.h"

void RelayCallback(const char* name, void* param, const struct HAL_Value* value)
{
    std::string nameStr = name;
    int port = *reinterpret_cast<int*>(param);

    if (nameStr == "InitializedForward")
    {
        if (!SensorActuatorRegistry::Get().GetIRelayWrapper(port, false))
        {
            FactoryContainer::Get().GetRelayFactory()->Create(port, "com.snobot.simulator.module_wrapper.wpi.WpiRelayWrapper");
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Simulator on port " << port << " was not registered before starting the robot");
        }
        SensorActuatorRegistry::Get().GetIRelayWrapper(port)->SetInitialized(true);
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown name " << nameStr);
    }
}

int gRelayArrayIndices[20];

void SnobotSim::InitializeRelayCallbacks()
{
    for (int i = 0; i < HAL_GetNumRelayHeaders(); ++i)
    {
        gRelayArrayIndices[i] = i;
        HALSIM_RegisterRelayInitializedForwardCallback(i, &RelayCallback, &gRelayArrayIndices[i], false);
        HALSIM_RegisterRelayInitializedReverseCallback(i, &RelayCallback, &gRelayArrayIndices[i], false);
        HALSIM_RegisterRelayForwardCallback(i, &RelayCallback, &gRelayArrayIndices[i], false);
        HALSIM_RegisterRelayReverseCallback(i, &RelayCallback, &gRelayArrayIndices[i], false);
    }
}

void SnobotSim::ResetRelayCallbacks()
{
    for (int i = 0; i < HAL_GetNumRelayHeaders(); ++i)
    {
        HALSIM_ResetRelayData(i);
    }
}

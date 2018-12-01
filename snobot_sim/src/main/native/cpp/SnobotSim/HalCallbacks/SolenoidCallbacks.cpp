

#include "SnobotSim/HalCallbacks/SolenoidCallbacks.h"

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiSolenoidWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "hal/Ports.h"
#include "mockdata/PCMData.h"

void SolenoidCallback(const char* name, void* param, const struct HAL_Value* value)
{
    std::string nameStr = name;
    int port = *reinterpret_cast<int*>(param);

    if (nameStr == "SolenoidInitialized")
    {
        if (!SensorActuatorRegistry::Get().GetISolenoidWrapper(port, false))
        {
            FactoryContainer::Get().GetSolenoidFactory()->Create(port, "com.snobot.simulator.module_wrapper.wpi.WpiSolenoidWrapper");
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Simulator on port " << port << " was not registered before starting the robot");
        }
        SensorActuatorRegistry::Get().GetISolenoidWrapper(port)->SetInitialized(true);
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown name " << nameStr);
    }
}

int gSolenoidArrayIndices[200];

void SnobotSim::InitializeSolenoidCallbacks()
{
    for (int module = 0; module < 2; ++module)
    {
        for (int channel = 0; channel < HAL_GetNumSolenoidChannels(); ++channel)
        {
            int fullChannel = module * HAL_GetNumSolenoidChannels() + channel;
            gSolenoidArrayIndices[fullChannel] = fullChannel;
            HALSIM_RegisterPCMSolenoidInitializedCallback(module, channel, &SolenoidCallback, &gSolenoidArrayIndices[fullChannel], false);
            HALSIM_RegisterPCMSolenoidOutputCallback(module, channel, &SolenoidCallback, &gSolenoidArrayIndices[fullChannel], false);
        }
    }
}

void SnobotSim::ResetSolenoidCallbacks()
{
    for (int module = 0; module < 2; ++module)
    {
        HALSIM_ResetPCMData(module);
    }
}

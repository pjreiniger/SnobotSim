

#include "SnobotSim/HalCallbacks/PwmCallbacks.h"

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiSpeedControllerWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "hal/Ports.h"
#include "hal/simulation/PWMData.h"

void PwmCallback(const char* name, void* param, const struct HAL_Value* value)
{
    std::string nameStr = name;
    int port = *reinterpret_cast<int*>(param);

    if (nameStr == "Initialized")
    {
        if (!SensorActuatorRegistry::Get().GetISpeedControllerWrapper(port, false))
        {
            FactoryContainer::Get().GetSpeedControllerFactory()->Create(port, "com.snobot.simulator.module_wrapper.wpi.WpiPwmWrapper");
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Simulator on port " << port << " was not registered before starting the robot");
        }
        SensorActuatorRegistry::Get().GetISpeedControllerWrapper(port)->SetInitialized(true);
    }
    else if (nameStr == "Speed")
    {
        double speed = value->data.v_double;
        SensorActuatorRegistry::Get().GetISpeedControllerWrapper(port)->SetVoltagePercentage(speed);
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown name " << nameStr);
    }
}

int gPwmArrayIndices[26];

void SnobotSim::InitializePwmCallbacks()
{
    for (int i = 0; i < HAL_GetNumPWMChannels(); ++i)
    {
        gPwmArrayIndices[i] = i;
        HALSIM_RegisterPWMInitializedCallback(i, &PwmCallback, &gPwmArrayIndices[i], false);
        HALSIM_RegisterPWMSpeedCallback(i, &PwmCallback, &gPwmArrayIndices[i], false);
        HALSIM_RegisterPWMRawValueCallback(i, &PwmCallback, &gPwmArrayIndices[i], false);
        HALSIM_RegisterPWMPositionCallback(i, &PwmCallback, &gPwmArrayIndices[i], false);
    }
}

void SnobotSim::ResetPwmCallbacks()
{
    for (int i = 0; i < HAL_GetNumPWMChannels(); ++i)
    {
        HALSIM_ResetPWMData(i);
    }
}

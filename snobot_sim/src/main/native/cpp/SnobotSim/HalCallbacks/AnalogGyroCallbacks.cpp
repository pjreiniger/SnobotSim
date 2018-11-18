/*
 * AnalogGyroCallbacks.cpp
 *
 *  Created on: Sep 10, 2017
 *      Author: PJ
 */

#include "SnobotSim/HalCallbacks/AnalogGyroCallbacks.h"

#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiAnalogGyroWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "hal/Ports.h"
#include "mockdata/AnalogGyroData.h"

void AnalogGyroCallback(const char* name, void* param, const struct HAL_Value* value)
{
    std::string nameStr = name;
    int port = *reinterpret_cast<int*>(param);

    if (nameStr == "Initialized")
    {
        if (!SensorActuatorRegistry::Get().GetIGyroWrapper(port, false))
        {
            FactoryContainer::Get().GetGyroFactory()->Create(port, "WpiAnalogGyroWrapper");
        }

        std::shared_ptr<IAnalogInWrapper> analogWrapper = SensorActuatorRegistry::Get().GetIAnalogInWrapper(port);
        analogWrapper->SetWantsHidden(true);

        SensorActuatorRegistry::Get().GetIGyroWrapper(port)->SetInitialized(true);
    }
    else if (nameStr == "Angle")
    {
        double angle = value->data.v_double;
        SensorActuatorRegistry::Get().GetIGyroWrapper(port)->SetAngle(angle);
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown name " << nameStr);
    }
}

int gAnalogGyroArrayIndices[26];

void SnobotSim::InitializeAnalogGyroCallbacks()
{
    for (int i = 0; i < HAL_GetNumAccumulators(); ++i)
    {
        gAnalogGyroArrayIndices[i] = i;
        HALSIM_RegisterAnalogGyroInitializedCallback(i, &AnalogGyroCallback, &gAnalogGyroArrayIndices[i], false);
        HALSIM_RegisterAnalogGyroAngleCallback(i, &AnalogGyroCallback, &gAnalogGyroArrayIndices[i], false);
        HALSIM_RegisterAnalogGyroRateCallback(i, &AnalogGyroCallback, &gAnalogGyroArrayIndices[i], false);
    }
}

void SnobotSim::ResetAnalogGyroCallbacks()
{
    for (int i = 0; i < HAL_GetNumAccumulators(); ++i)
    {
        HALSIM_ResetAnalogGyroData(i);
    }
}

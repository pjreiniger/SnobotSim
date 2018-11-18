/*
 * SpiCallbacks.cpp
 *
 *  Created on: Sep 11, 2017
 *      Author: preiniger
 */

#include "SnobotSim/HalCallbacks/SpiCallbacks.h"

#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISpiWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "hal/Ports.h"
#include "mockdata/SPIData.h"

void SpiCallback(const char* name, void* param, const struct HAL_Value* value)
{
    std::string nameStr = name;
    int port = *reinterpret_cast<int*>(param);

    if ("Initialized" == nameStr)
    {
        std::shared_ptr<ISpiWrapper> spiWrapper = FactoryContainer::Get().GetSpiWrapperFactory()->GetSpiWrapper(port);
        SensorActuatorRegistry::Get().Register(port, spiWrapper);
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Unknown name " << nameStr);
    }
}

int gSpiInArrayIndices[5];

void SnobotSim::InitializeSpiCallbacks()
{
    for (int i = 0; i < 5; ++i)
    {
        gSpiInArrayIndices[i] = i;
        HALSIM_RegisterSPIInitializedCallback(i, &SpiCallback, &gSpiInArrayIndices[i], false);
    }
}

void SnobotSim::ResetSpiCallbacks()
{
    for (int i = 0; i < 5; ++i)
    {
        HALSIM_ResetSPIData(i);
    }
}

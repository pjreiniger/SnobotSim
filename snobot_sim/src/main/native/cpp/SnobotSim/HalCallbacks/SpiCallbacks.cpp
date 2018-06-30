/*
 * SpiCallbacks.cpp
 *
 *  Created on: Sep 11, 2017
 *      Author: preiniger
 */

#include "SnobotSim/HalCallbacks/SpiCallbacks.h"

#include "MockData/SPIData.h"
#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/SpiWrapperFactory.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISpiWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"

void SpiCallback(const char* name, void* param, const struct HAL_Value* value)
{
    std::string nameStr = name;
    int port = *reinterpret_cast<int*>(param);

    if ("Initialized" == nameStr)
    {
        std::shared_ptr<ISpiWrapper> spiWrapper = SpiWrapperFactory::Get().GetSpiWrapper(port);
        SensorActuatorRegistry::Get().Register(port, spiWrapper);
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::WARN, "Unknown name " << nameStr);
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

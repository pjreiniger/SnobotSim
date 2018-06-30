/*
 * I2CCallbacks.cpp
 *
 *  Created on: Sep 11, 2017
 *      Author: preiniger
 */

#include "SnobotSim/HalCallbacks/I2CCallbacks.h"

#include "MockData/I2CData.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/I2CWrapperFactory.h"
#include "SnobotSim/ModuleWrapper/Interfaces/II2CWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"

void I2CCallback(const char* name, void* param, const struct HAL_Value* value)
{
    std::string nameStr = name;
    int port = *reinterpret_cast<int*>(param);

    if ("Initialized" == nameStr)
    {
        std::shared_ptr<II2CWrapper> i2cWrapper = I2CWrapperFactory::Get().GetI2CWrapper(port);
        SensorActuatorRegistry::Get().Register(port, i2cWrapper);
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::WARN, "Unknown name " << nameStr);
    }
}

int gI2CInArrayIndices[2];

void SnobotSim::InitializeI2CCallbacks()
{
    for (int i = 0; i < 2; ++i)
    {
        gI2CInArrayIndices[i] = i;
        HALSIM_RegisterI2CInitializedCallback(i, &I2CCallback, &gI2CInArrayIndices[i], false);
    }
}

void SnobotSim::ResetI2CCallbacks()
{
    for (int i = 0; i < 2; ++i)
    {
        HALSIM_ResetI2CData(i);
    }
}

/*
 * GetSensorActuatorHelper.cpp
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#include "SnobotSim/GetSensorActuatorHelper.h"

#include "SnobotSim/PortUnwrapper.h"

#define FIND_MODULE_FUNC(WrapperType)                                                                                                         \
                                                                                                                                              \
    std::shared_ptr<WrapperType>                                                                                                              \
            Get##WrapperType(int aHandle, bool aLogOnMissing)                                                                                 \
                                                                                                                                              \
    {                                                                                                                                         \
        std::shared_ptr<WrapperType> wrapper = SensorActuatorRegistry::Get().Get##WrapperType(aHandle, false);                                \
        if (!wrapper) /* NOLINT */                                                                                                            \
        {                                                                                                                                     \
            int packedPort = WrapPort(aHandle);                                                                                               \
            wrapper = SensorActuatorRegistry::Get().Get##WrapperType(packedPort, false);                                                      \
            if (!wrapper && aLogOnMissing)                                                                                                    \
            {                                                                                                                                 \
                SNOBOT_LOG(SnobotLogging::CRITICAL, "Could not find " << #WrapperType << " for handles " << aHandle << " or " << packedPort); \
            }                                                                                                                                 \
        }                                                                                                                                     \
        return wrapper;                                                                                                                       \
    }

namespace GetSensorActuatorHelper
{
FIND_MODULE_FUNC(ISpeedControllerWrapper)
FIND_MODULE_FUNC(IGyroWrapper)
FIND_MODULE_FUNC(IDigitalIoWrapper)
FIND_MODULE_FUNC(IAnalogInWrapper)
FIND_MODULE_FUNC(IAnalogOutWrapper)
FIND_MODULE_FUNC(IRelayWrapper)
FIND_MODULE_FUNC(ISolenoidWrapper)
FIND_MODULE_FUNC(IEncoderWrapper)
FIND_MODULE_FUNC(ISpiWrapper)
FIND_MODULE_FUNC(II2CWrapper)
} // namespace GetSensorActuatorHelper

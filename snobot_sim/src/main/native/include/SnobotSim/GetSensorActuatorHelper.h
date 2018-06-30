/*
 * GetSensorActuatorHelper.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_GETSENSORACTUATORHELPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_GETSENSORACTUATORHELPER_H_

#include <memory>

#include "SnobotSim/SensorActuatorRegistry.h"

namespace GetSensorActuatorHelper
{
EXPORT_ std::shared_ptr<ISpeedControllerWrapper> GetISpeedControllerWrapper(int aHandle, bool aLogOnMissing = true);
EXPORT_ std::shared_ptr<IGyroWrapper> GetIGyroWrapper(int aHandle, bool aLogOnMissing = true);
EXPORT_ std::shared_ptr<IDigitalIoWrapper> GetIDigitalIoWrapper(int aHandle, bool aLogOnMissing = true);
EXPORT_ std::shared_ptr<IAnalogInWrapper> GetIAnalogInWrapper(int aHandle, bool aLogOnMissing = true);
EXPORT_ std::shared_ptr<IAnalogOutWrapper> GetIAnalogOutWrapper(int aHandle, bool aLogOnMissing = true);
EXPORT_ std::shared_ptr<IRelayWrapper> GetIRelayWrapper(int aHandle, bool aLogOnMissing = true);
EXPORT_ std::shared_ptr<ISolenoidWrapper> GetISolenoidWrapper(int aHandle, bool aLogOnMissing = true);
EXPORT_ std::shared_ptr<IEncoderWrapper> GetIEncoderWrapper(int aHandle, bool aLogOnMissing = true);
EXPORT_ std::shared_ptr<ISpiWrapper> GetISpiWrapper(int aHandle, bool aLogOnMissing = false);
EXPORT_ std::shared_ptr<II2CWrapper> GetII2CWrapper(int aHandle, bool aLogOnMissing = false);
} // namespace GetSensorActuatorHelper

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_GETSENSORACTUATORHELPER_H_

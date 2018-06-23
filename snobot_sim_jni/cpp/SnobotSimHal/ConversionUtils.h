/*
 * ConversionUtils.h
 *
 *  Created on: Jun 21, 2017
 *      Author: preiniger
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_JNI_CPP_SNOBOTSIMHAL_CONVERSIONUTILS_H_
#define SNOBOTSIM_SNOBOT_SIM_JNI_CPP_SNOBOTSIMHAL_CONVERSIONUTILS_H_

#include <jni.h>

#include "SnobotSim/MotorSim/DcMotorModelConfig.h"
#include "support/jni_util.h"

namespace ConversionUtils
{
    DcMotorModelConfig ConvertDcMotorModelConfig(JNIEnv * env, jobject& aJavaModelConfig);

    jobject ConvertDcMotorModelConfig(JNIEnv *env, const DcMotorModelConfig& aConfig);
}  // namespace ConversionUtils

#endif  // SNOBOTSIM_SNOBOT_SIM_JNI_CPP_SNOBOTSIMHAL_CONVERSIONUTILS_H_

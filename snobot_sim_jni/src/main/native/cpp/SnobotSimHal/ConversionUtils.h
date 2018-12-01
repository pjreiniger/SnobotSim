/*
 * ConversionUtils.h
 *
 *  Created on: Jun 21, 2017
 *      Author: preiniger
 */


#pragma once
#include <jni.h>

#include "SnobotSim/MotorSim/DcMotorModelConfig.h"
#include "wpi/jni_util.h"

namespace ConversionUtils
{
    DcMotorModelConfig ConvertDcMotorModelConfig(JNIEnv * env, jobject& aJavaModelConfig);

    jobject ConvertDcMotorModelConfig(JNIEnv *env, const DcMotorModelConfig& aConfig);
}  // namespace ConversionUtils

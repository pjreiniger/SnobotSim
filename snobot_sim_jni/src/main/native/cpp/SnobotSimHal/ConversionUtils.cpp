/*
 * ConversionUtils.cpp
 *
 *  Created on: Jun 21, 2017
 *      Author: preiniger
 */

#include "ConversionUtils.h"

using namespace wpi::java;

namespace ConversionUtils
{

    DcMotorModelConfig ConvertDcMotorModelConfig(JNIEnv * env, jobject& aJavaModelConfig)
    {
        jobject configObj = env->GetObjectField(aJavaModelConfig, env->GetFieldID(env->GetObjectClass(aJavaModelConfig), "mConfig", "Lcom/snobot/simulator/motor_sim/DcMotorModelConfig;"));
        jobject factoryParamsObj = env->GetObjectField(configObj, env->GetFieldID(env->GetObjectClass(configObj), "mFactoryParams", "Lcom/snobot/simulator/motor_sim/DcMotorModelConfig$FactoryParams;"));
        jobject motorParamsObj = env->GetObjectField(configObj, env->GetFieldID(env->GetObjectClass(configObj), "mMotorParams", "Lcom/snobot/simulator/motor_sim/DcMotorModelConfig$MotorParams;"));

        jstring motorType = (jstring) env->GetObjectField(factoryParamsObj, env->GetFieldID(env->GetObjectClass(factoryParamsObj), "mMotorType", "Ljava/lang/String;"));

        DcMotorModelConfig::FactoryParams factoryParams;
        factoryParams.mMotorName              = env->GetStringUTFChars(motorType, NULL);
        factoryParams.mNumMotors              = env->GetIntField(factoryParamsObj, env->GetFieldID(env->GetObjectClass(factoryParamsObj), "mNumMotors", "I"));
        factoryParams.mGearReduction          = env->GetDoubleField(factoryParamsObj, env->GetFieldID(env->GetObjectClass(factoryParamsObj), "mGearReduction", "D"));
        factoryParams.mTransmissionEfficiency = env->GetDoubleField(factoryParamsObj, env->GetFieldID(env->GetObjectClass(factoryParamsObj), "mGearboxEfficiency", "D"));

        DcMotorModelConfig config(
                factoryParams,
                env->GetDoubleField(motorParamsObj, env->GetFieldID(env->GetObjectClass(motorParamsObj), "mNominalVoltage", "D")),
                env->GetDoubleField(motorParamsObj, env->GetFieldID(env->GetObjectClass(motorParamsObj), "mFreeSpeedRpm", "D")),
                env->GetDoubleField(motorParamsObj, env->GetFieldID(env->GetObjectClass(motorParamsObj), "mFreeCurrent", "D")),
                env->GetDoubleField(motorParamsObj, env->GetFieldID(env->GetObjectClass(motorParamsObj), "mStallTorque", "D")),
                env->GetDoubleField(motorParamsObj, env->GetFieldID(env->GetObjectClass(motorParamsObj), "mStallCurrent", "D")),
                env->GetDoubleField(motorParamsObj, env->GetFieldID(env->GetObjectClass(motorParamsObj), "mMortorInertia", "D")),

                env->GetBooleanField(factoryParamsObj, env->GetFieldID(env->GetObjectClass(factoryParamsObj), "mHasBrake", "Z")),
                env->GetBooleanField(factoryParamsObj, env->GetFieldID(env->GetObjectClass(factoryParamsObj), "mInverted", "Z")),

                env->GetDoubleField(motorParamsObj, env->GetFieldID(env->GetObjectClass(motorParamsObj), "mKT", "D")),
                env->GetDoubleField(motorParamsObj, env->GetFieldID(env->GetObjectClass(motorParamsObj), "mKV", "D")),
                env->GetDoubleField(motorParamsObj, env->GetFieldID(env->GetObjectClass(motorParamsObj), "mResistance", "D"))
        );

        return config;
    }

    jobject ConvertDcMotorModelConfig(
            JNIEnv *env,
            const DcMotorModelConfig& aConfig)
    {
        static JClass theClazz = JClass(env, "com/snobot/simulator/jni/LocalDcMotorModelConfig");
        static jmethodID constructor =
                env->GetMethodID(theClazz, "<init>", "(Ljava/lang/String;IDDDDDDDDZZDDD)V");

        jstring motorType = MakeJString(env, aConfig.mFactoryParams.mMotorName);

        return env->NewObject(theClazz, constructor,
                motorType,
                aConfig.mFactoryParams.mNumMotors,
                aConfig.mFactoryParams.mGearReduction,
                aConfig.mFactoryParams.mTransmissionEfficiency,
                aConfig.NOMINAL_VOLTAGE,
                aConfig.FREE_SPEED_RPM,
                aConfig.FREE_CURRENT,
                aConfig.STALL_TORQUE,
                aConfig.STALL_CURRENT,
                aConfig.mMotorInertia,

                aConfig.mInverted,
                aConfig.mHasBrake,

                aConfig.mKT,
                aConfig.mKV,
                aConfig.mResistance);
    }
}  // namespace ConversionUtils

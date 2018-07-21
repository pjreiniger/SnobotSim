
#include <jni.h>

#include <cassert>

#include "ConversionUtils.h"
#include "SnobotSim/MotorFactory/VexMotorFactory.h"
#include "com_snobot_simulator_jni_MotorConfigFactoryJni.h"
#include "support/jni_util.h"

extern "C"
{

/*
 * Class:     com_snobot_simulator_jni_MotorConfigFactoryJni
 * Method:    createMotor
 * Signature: (Ljava/lang/String;IDDZZ)Lcom/snobot/simulator/jni/LocalDcMotorModelConfig;
 */
JNIEXPORT jobject JNICALL Java_com_snobot_simulator_jni_MotorConfigFactoryJni_createMotor
  (JNIEnv * env, jclass, jstring aName, jint aNumMotors, jdouble aGearReduction, jdouble aTransmissionEfficiency)
{

    DcMotorModelConfig config = VexMotorFactory::MakeTransmission(
            VexMotorFactory::CreateMotor(env->GetStringUTFChars(aName, NULL)),
            aNumMotors, aGearReduction, aTransmissionEfficiency);

    return ConversionUtils::ConvertDcMotorModelConfig(env, config);
}

}  // extern "C"

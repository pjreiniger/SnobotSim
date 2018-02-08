
#include "com_snobot_simulator_jni_standard_components_EncoderCallbackJni.h"

#include "MockData/EncoderData.h"

#include "SnobotSimJava/Jni/RegisterJniUtilities.h"

int gEncoderArrayIndices[26];
SnobotSimJava::CallbackHelperContainer gEncoderCallbackContainer;

void EncoderCallback(const char* name, void* param, const struct HAL_Value* value)
{
    SnobotSimJava::CallJavaCallback(gEncoderCallbackContainer, name, param, value);
}

/*
 * Class:     com_snobot_simulator_jni_standard_components_EncoderCallbackJni
 * Method:    setEncoderDistance
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_standard_1components_EncoderCallbackJni_setEncoderDistance
  (JNIEnv *, jclass, jint aHandle, jdouble aDistance)
{
    HALSIM_SetEncoderCount(aHandle, (int) aDistance);
}

/*
 * Class:     com_snobot_simulator_jni_standard_components_EncoderCallbackJni
 * Method:    registerEncoderCallback
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_standard_1components_EncoderCallbackJni_registerEncoderCallback
  (JNIEnv * env, jclass clz, jstring aFunctionName)
{
    SnobotSimJava::SetGlobalEnvironment(env);
    std::string functionName = env->GetStringUTFChars(aFunctionName, NULL);
    SetCallbackContainerInfo(env, clz, functionName, gEncoderCallbackContainer);
}

/*
 * Class:     com_snobot_simulator_jni_standard_components_EncoderCallbackJni
 * Method:    reset
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_standard_1components_EncoderCallbackJni_reset
  (JNIEnv *, jclass)
{
    for(int i = 0; i < HAL_GetNumEncoders(); ++i)
    {
        HALSIM_ResetEncoderData(i);
    }

    for(int i = 0; i < HAL_GetNumEncoders(); ++i)
    {
        gEncoderArrayIndices[i] = i;
        HALSIM_RegisterEncoderAllCallbacks(i, &EncoderCallback, &gEncoderArrayIndices[i], false);
    }
}


/*
 * Class:     com_snobot_simulator_jni_standard_components_EncoderCallbackJni
 * Method:    clearResetFlag
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_standard_1components_EncoderCallbackJni_clearResetFlag
  (JNIEnv *, jclass, jint aPort)
{
    HALSIM_SetEncoderReset(aPort, false);
}


#include <jni.h>

#include <cassert>

#include "MockData/DriverStationData.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "com_snobot_simulator_jni_JoystickJni.h"

extern "C"
{
/*
 * Class:     com_snobot_simulator_jni_JoystickJni
 * Method:    setJoystickInformation
 * Signature: (I[F[S[Z)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_JoystickJni_setJoystickInformation
  (JNIEnv * env, jclass,
   jint aHandle, jfloatArray aAxes, jshortArray aPovs, jint aButtonCount, jint aButtonMask)
{
    HAL_JoystickAxes newAxes;
    HAL_JoystickPOVs newPov;
    HAL_JoystickButtons newButtons;

    newButtons.count = aButtonCount;
    newButtons.buttons = aButtonMask;

    float* axes = env->GetFloatArrayElements(aAxes, NULL);
    newAxes.count = env->GetArrayLength(aAxes);
    for (int i = 0; i < newAxes.count; ++i)
    {
        newAxes.axes[i] = axes[i];
    }
    env->ReleaseFloatArrayElements(aAxes, axes, 0);

    short* povs = env->GetShortArrayElements(aPovs, NULL);
    newPov.count = env->GetArrayLength(aPovs);
    for (int i = 0; i < newPov.count; ++i)
    {
        newPov.povs[i] = povs[i];
    }
    env->ReleaseShortArrayElements(aPovs, povs, 0);

    HALSIM_SetJoystickAxes(aHandle, &newAxes);
    HALSIM_SetJoystickPOVs(aHandle, &newPov);
    HALSIM_SetJoystickButtons(aHandle, &newButtons);
}
}  // extern "C"

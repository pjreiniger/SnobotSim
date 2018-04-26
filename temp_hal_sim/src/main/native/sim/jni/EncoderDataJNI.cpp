#include "edu_wpi_first_hal_sim_mockdata_EncoderDataJNI.h"
#include "MockData/EncoderData.h"
#include <jni.h>
#include "CallbackStore.h"

extern "C" {

JNIEXPORT jint JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_registerInitializedCallback
  (JNIEnv * env, jclass, jint index, jobject callback, jboolean initialNotify) {
  return sim::AllocateCallback(env, index, callback, initialNotify, &HALSIM_RegisterEncoderInitializedCallback);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_cancelInitializedCallback
  (JNIEnv * env, jclass, jint index, jint handle) {
  return sim::FreeCallback(env, handle, index, &HALSIM_CancelEncoderInitializedCallback);
}

JNIEXPORT jboolean JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_getInitialized
  (JNIEnv *, jclass, jint index) {
  return HALSIM_GetEncoderInitialized(index);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_setInitialized
  (JNIEnv *, jclass, jint index, jboolean value) {
  HALSIM_SetEncoderInitialized(index, value);
}

JNIEXPORT jint JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_registerCountCallback
  (JNIEnv * env, jclass, jint index, jobject callback, jboolean initialNotify) {
  return sim::AllocateCallback(env, index, callback, initialNotify, &HALSIM_RegisterEncoderCountCallback);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_cancelCountCallback
  (JNIEnv * env, jclass, jint index, jint handle) {
  return sim::FreeCallback(env, handle, index, &HALSIM_CancelEncoderCountCallback);
}

JNIEXPORT jint JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_getCount
  (JNIEnv *, jclass, jint index) {
  return HALSIM_GetEncoderCount(index);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_setCount
  (JNIEnv *, jclass, jint index, jint value) {
  HALSIM_SetEncoderCount(index, value);
}

JNIEXPORT jint JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_registerPeriodCallback
  (JNIEnv * env, jclass, jint index, jobject callback, jboolean initialNotify) {
  return sim::AllocateCallback(env, index, callback, initialNotify, &HALSIM_RegisterEncoderPeriodCallback);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_cancelPeriodCallback
  (JNIEnv * env, jclass, jint index, jint handle) {
  return sim::FreeCallback(env, handle, index, &HALSIM_CancelEncoderPeriodCallback);
}

JNIEXPORT jdouble JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_getPeriod
  (JNIEnv *, jclass, jint index) {
  return HALSIM_GetEncoderPeriod(index);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_setPeriod
  (JNIEnv *, jclass, jint index, jdouble value) {
  HALSIM_SetEncoderPeriod(index, value);
}

JNIEXPORT jint JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_registerResetCallback
  (JNIEnv * env, jclass, jint index, jobject callback, jboolean initialNotify) {
  return sim::AllocateCallback(env, index, callback, initialNotify, &HALSIM_RegisterEncoderResetCallback);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_cancelResetCallback
  (JNIEnv * env, jclass, jint index, jint handle) {
  return sim::FreeCallback(env, handle, index, &HALSIM_CancelEncoderResetCallback);
}

JNIEXPORT jboolean JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_getReset
  (JNIEnv *, jclass, jint index) {
  return HALSIM_GetEncoderReset(index);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_setReset
  (JNIEnv *, jclass, jint index, jboolean value) {
  HALSIM_SetEncoderReset(index, value);
}

JNIEXPORT jint JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_registerMaxPeriodCallback
  (JNIEnv * env, jclass, jint index, jobject callback, jboolean initialNotify) {
  return sim::AllocateCallback(env, index, callback, initialNotify, &HALSIM_RegisterEncoderMaxPeriodCallback);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_cancelMaxPeriodCallback
  (JNIEnv * env, jclass, jint index, jint handle) {
  return sim::FreeCallback(env, handle, index, &HALSIM_CancelEncoderMaxPeriodCallback);
}

JNIEXPORT jdouble JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_getMaxPeriod
  (JNIEnv *, jclass, jint index) {
  return HALSIM_GetEncoderMaxPeriod(index);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_setMaxPeriod
  (JNIEnv *, jclass, jint index, jdouble value) {
  HALSIM_SetEncoderMaxPeriod(index, value);
}

JNIEXPORT jint JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_registerDirectionCallback
  (JNIEnv * env, jclass, jint index, jobject callback, jboolean initialNotify) {
  return sim::AllocateCallback(env, index, callback, initialNotify, &HALSIM_RegisterEncoderDirectionCallback);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_cancelDirectionCallback
  (JNIEnv * env, jclass, jint index, jint handle) {
  return sim::FreeCallback(env, handle, index, &HALSIM_CancelEncoderDirectionCallback);
}

JNIEXPORT jboolean JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_getDirection
  (JNIEnv *, jclass, jint index) {
  return HALSIM_GetEncoderDirection(index);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_setDirection
  (JNIEnv *, jclass, jint index, jboolean value) {
  HALSIM_SetEncoderDirection(index, value);
}

JNIEXPORT jint JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_registerReverseDirectionCallback
  (JNIEnv * env, jclass, jint index, jobject callback, jboolean initialNotify) {
  return sim::AllocateCallback(env, index, callback, initialNotify, &HALSIM_RegisterEncoderReverseDirectionCallback);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_cancelReverseDirectionCallback
  (JNIEnv * env, jclass, jint index, jint handle) {
  return sim::FreeCallback(env, handle, index, &HALSIM_CancelEncoderReverseDirectionCallback);
}

JNIEXPORT jboolean JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_getReverseDirection
  (JNIEnv *, jclass, jint index) {
  return HALSIM_GetEncoderReverseDirection(index);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_setReverseDirection
  (JNIEnv *, jclass, jint index, jboolean value) {
  HALSIM_SetEncoderReverseDirection(index, value);
}

JNIEXPORT jint JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_registerSamplesToAverageCallback
  (JNIEnv * env, jclass, jint index, jobject callback, jboolean initialNotify) {
  return sim::AllocateCallback(env, index, callback, initialNotify, &HALSIM_RegisterEncoderSamplesToAverageCallback);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_cancelSamplesToAverageCallback
  (JNIEnv * env, jclass, jint index, jint handle) {
  return sim::FreeCallback(env, handle, index, &HALSIM_CancelEncoderSamplesToAverageCallback);
}

JNIEXPORT jint JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_getSamplesToAverage
  (JNIEnv *, jclass, jint index) {
  return HALSIM_GetEncoderSamplesToAverage(index);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_setSamplesToAverage
  (JNIEnv *, jclass, jint index, jint value) {
  HALSIM_SetEncoderSamplesToAverage(index, value);
}

int hackhack(int32_t index,
        HAL_NotifyCallback callback,
        void* param, HAL_Bool initialNotify)
{
    HALSIM_RegisterEncoderAllCallbacks(index, callback, param, initialNotify);
    return 0;
}

JNIEXPORT jint JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_registerAllCallbacks
  (JNIEnv * env, jclass, jint index, jobject callback, jboolean initialNotify) {
    return sim::AllocateCallback(env, index, callback, initialNotify, &hackhack);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_resetData
  (JNIEnv*, jclass, jint index) {
  HALSIM_ResetEncoderData(index);
}

}

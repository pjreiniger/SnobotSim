#include "edu_wpi_first_hal_sim_mockdata_SimulatorJNI.h"
#include "SimulatorJNI.h"
#include "HAL/cpp/Log.h"
#include "HAL/HAL.h"
#include "CallbackStore.h"
#include "BufferCallbackStore.h"
#include "ConstBufferCallbackStore.h"
#include "SpiReadAutoReceiveBufferCallbackStore.h"
#include "HAL/handles/HandlesInternal.h"
#include "MockData/MockHooks.h"
#include <iostream>

using namespace wpi::java;

static JavaVM* jvm = nullptr;
static JClass simValueCls;
static JClass notifyCallbackCls;
static JClass bufferCallbackCls;
static JClass constBufferCallbackCls;
static JClass spiReadAutoReceiveBufferCallbackCls;
static jmethodID notifyCallbackCallback;
static jmethodID bufferCallbackCallback;
static jmethodID constBufferCallbackCallback;
static jmethodID spiReadAutoReceiveBufferCallbackCallback;

namespace sim {
jint SimOnLoad(JNIEnv *env) {

  simValueCls = JClass(env, "edu/wpi/first/wpilibj/sim/SimValue");
  if (!simValueCls){    return JNI_ERR;
  }

  notifyCallbackCls = JClass(env, "edu/wpi/first/wpilibj/sim/NotifyCallback");
  if (!notifyCallbackCls){    return JNI_ERR;
  }

  notifyCallbackCallback = env->GetMethodID(notifyCallbackCls, "callbackNative", "(Ljava/lang/String;IJD)V");
  if (!notifyCallbackCallback){    return JNI_ERR;
  }

  bufferCallbackCls = JClass(env, "edu/wpi/first/wpilibj/sim/BufferCallback");
  if (!bufferCallbackCls){    return JNI_ERR;
  }

  bufferCallbackCallback = env->GetMethodID(bufferCallbackCls, "callback", "(Ljava/lang/String;[BI)V");
  if (!bufferCallbackCallback){    return JNI_ERR;
  }

  constBufferCallbackCls = JClass(env, "edu/wpi/first/wpilibj/sim/ConstBufferCallback");
  if (!constBufferCallbackCls){    return JNI_ERR;
  }

  constBufferCallbackCallback = env->GetMethodID(constBufferCallbackCls, "callback", "(Ljava/lang/String;[BI)V");
  if (!constBufferCallbackCallback){    return JNI_ERR;
  }


  spiReadAutoReceiveBufferCallbackCls = JClass(env, "edu/wpi/first/wpilibj/sim/SpiReadAutoReceiveBufferCallback");
  if (!spiReadAutoReceiveBufferCallbackCls){    return JNI_ERR;
  }

  spiReadAutoReceiveBufferCallbackCallback = env->GetMethodID(spiReadAutoReceiveBufferCallbackCls, "callback", "(Ljava/lang/String;[BI)I");
  if (!spiReadAutoReceiveBufferCallbackCallback){    return JNI_ERR;
  }

  InitializeStore();
  InitializeBufferStore();
  InitializeConstBufferStore();
  InitializeSpiBufferStore();

  return JNI_VERSION_1_6;
}

void SimOnUnload(JavaVM * vm, void* reserved) {
  JNIEnv *env;
  if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK)
    return;

  simValueCls.free(env);
  notifyCallbackCls.free(env);
  bufferCallbackCls.free(env);
  constBufferCallbackCls.free(env);
  spiReadAutoReceiveBufferCallbackCls.free(env);
  jvm = nullptr;
}

JavaVM* GetJVM() {
  return jvm;
}

jmethodID GetNotifyCallback() {
  return notifyCallbackCallback;
}

jmethodID GetBufferCallback() {
  return bufferCallbackCallback;
}

jmethodID GetConstBufferCallback() {
  return constBufferCallbackCallback;
}

jmethodID GetSpiReadAutoReceiveBufferCallback() {
  return spiReadAutoReceiveBufferCallbackCallback;
}
}

extern "C" {
  /*
 * Class:     edu_wpi_first_hal_sim_mockdata_SimulatorJNI
 * Method:    waitForProgramStart
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_SimulatorJNI_waitForProgramStart
  (JNIEnv *, jclass) {
    HALSIM_WaitForProgramStart();
  }

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_SimulatorJNI
 * Method:    setProgramStarted
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_SimulatorJNI_setProgramStarted
  (JNIEnv *, jclass) {
    HALSIM_SetProgramStarted();
  }

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_SimulatorJNI
 * Method:    restartTiming
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_SimulatorJNI_restartTiming
  (JNIEnv *, jclass) {
  HALSIM_RestartTiming();
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_SimulatorJNI
 * Method:    resetHandles
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_SimulatorJNI_resetHandles
  (JNIEnv *, jclass) {
    hal::HandleBase::ResetGlobalHandles();
  }
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_SimulatorJNI_initialize
  (JNIEnv * env, jclass)
{
    static bool sINITIALIZED = false;

    if(!sINITIALIZED)
    {
        std::cout << "Initializing the hal" << std::endl;
        sINITIALIZED = true;

        if(!HAL_Initialize(0, 0))
        {
            std::cout << "!!!UH OH COULDN'T INITIALIZE" << std::endl;
        }
        std::cout << "LOAINfdsf " << sim::SimOnLoad(env) << std::endl;
    }

    hal::HandleBase::ResetGlobalHandles();

}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    std::cout << "On load" << std::endl;
  jvm = vm;
  std::cout << "On load2" << std::endl;
  return JNI_VERSION_1_6;
}


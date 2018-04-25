#include "CallbackStore.h"

#include "support/jni_util.h"
#include <jni.h>
#include "HAL/handles/UnlimitedHandleResource.h"
#include "MockData/HAL_Value.h"
#include "MockData/NotifyListener.h"
#include "HAL/types.h"
#include "SimulatorJNI.h"

using namespace wpi::java;
using namespace sim;

static hal::UnlimitedHandleResource<SIM_JniHandle, CallbackStore, hal::HAL_HandleEnum::SimulationJni>* callbackHandles;

namespace sim {
void InitializeStore() {
  static hal::UnlimitedHandleResource<SIM_JniHandle, CallbackStore, hal::HAL_HandleEnum::SimulationJni> cb;
  callbackHandles = &cb;
}
}

void CallbackStore::create(JNIEnv* env, jobject obj) {
  m_call = JGlobal<jobject>(env, obj);
}

void CallbackStore::performCallback(const char* name, const HAL_Value* value) {
  JNIEnv* env;
  JavaVM* vm = sim::GetJVM();
  bool didAttachThread = false;
  int tryGetEnv = vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6);
  if (tryGetEnv == JNI_EDETACHED) {
    // Thread not attached
    didAttachThread = true;
    if (vm->AttachCurrentThread(reinterpret_cast<void**>(&env), nullptr) != 0) {
      // Failed to attach, log and return
      llvm::outs() << "Failed to attach\n";
      llvm::outs().flush();
      return;
    }
  } else if (tryGetEnv == JNI_EVERSION) {
    llvm::outs() << "Invalid JVM Version requested\n";
    llvm::outs().flush();
  }

  env->CallVoidMethod(m_call, sim::GetNotifyCallback(), MakeJString(env, name), (jint)value->type, (jlong)value->data.v_long, (jdouble)value->data.v_double);

  if (env->ExceptionCheck()) {
    env->ExceptionDescribe();
  }

  if (didAttachThread) {
    vm->DetachCurrentThread();
  }
}

void CallbackStore::free(JNIEnv* env) {
  m_call.free(env);
}

SIM_JniHandle sim::AllocateCallback(JNIEnv* env, jint index, jobject callback, jboolean initialNotify, RegisterCallbackFunc createCallback) {

  auto callbackStore = std::make_shared<CallbackStore>();

  auto handle = callbackHandles->Allocate(callbackStore);

  if (handle == HAL_kInvalidHandle) {
    return -1;
  }

  uintptr_t handleAsPtr = static_cast<uintptr_t>(handle);
  void* handleAsVoidPtr = reinterpret_cast<void*>(handleAsPtr);

  callbackStore->create(env, callback);

  auto callbackFunc = [](const char* name, void* param, const HAL_Value* value){
    uintptr_t handleTmp = reinterpret_cast<uintptr_t>(param);
    SIM_JniHandle handle =
        static_cast<SIM_JniHandle>(handleTmp);
    auto data = callbackHandles->Get(handle);
    if (!data) return;

    data->performCallback(name, value);
  };

  auto id = createCallback(index, callbackFunc, handleAsVoidPtr, initialNotify);

  callbackStore->setCallbackId(id);

  return handle;
}

void sim::FreeCallback(JNIEnv* env, SIM_JniHandle handle, jint index, FreeCallbackFunc freeCallback) {
  auto callback = callbackHandles->Free(handle);
  freeCallback(index, callback->getCallbackId());
  callback->free(env);
}

SIM_JniHandle sim::AllocateChannelCallback(JNIEnv* env, jint index, jint channel, jobject callback, jboolean initialNotify, RegisterChannelCallbackFunc createCallback) {

  auto callbackStore = std::make_shared<CallbackStore>();

  auto handle = callbackHandles->Allocate(callbackStore);

  if (handle == HAL_kInvalidHandle) {
    return -1;
  }

  uintptr_t handleAsPtr = static_cast<uintptr_t>(handle);
  void* handleAsVoidPtr = reinterpret_cast<void*>(handleAsPtr);

  callbackStore->create(env, callback);

  auto callbackFunc = [](const char* name, void* param, const HAL_Value* value){
    uintptr_t handleTmp = reinterpret_cast<uintptr_t>(param);
    SIM_JniHandle handle =
        static_cast<SIM_JniHandle>(handleTmp);
    auto data = callbackHandles->Get(handle);
    if (!data) return;

    data->performCallback(name, value);
  };

  auto id = createCallback(index, channel, callbackFunc, handleAsVoidPtr, initialNotify);

  callbackStore->setCallbackId(id);

  return handle;
}

void sim::FreeChannelCallback(JNIEnv* env, SIM_JniHandle handle, jint index, jint channel, FreeChannelCallbackFunc freeCallback) {
  auto callback = callbackHandles->Free(handle);
  freeCallback(index, channel, callback->getCallbackId());
  callback->free(env);
}

SIM_JniHandle sim::AllocateCallbackNoIndex(JNIEnv* env, jobject callback, jboolean initialNotify, RegisterCallbackNoIndexFunc createCallback) {

  auto callbackStore = std::make_shared<CallbackStore>();

  auto handle = callbackHandles->Allocate(callbackStore);

  if (handle == HAL_kInvalidHandle) {
    return -1;
  }

  uintptr_t handleAsPtr = static_cast<uintptr_t>(handle);
  void* handleAsVoidPtr = reinterpret_cast<void*>(handleAsPtr);

  callbackStore->create(env, callback);

  auto callbackFunc = [](const char* name, void* param, const HAL_Value* value){
    uintptr_t handleTmp = reinterpret_cast<uintptr_t>(param);
    SIM_JniHandle handle =
        static_cast<SIM_JniHandle>(handleTmp);
    auto data = callbackHandles->Get(handle);
    if (!data) return;

    data->performCallback(name, value);
  };

  auto id = createCallback(callbackFunc, handleAsVoidPtr, initialNotify);

  callbackStore->setCallbackId(id);

  return handle;
}

void sim::FreeCallbackNoIndex(JNIEnv* env, SIM_JniHandle handle, FreeCallbackNoIndexFunc freeCallback) {
  auto callback = callbackHandles->Free(handle);
  freeCallback(callback->getCallbackId());
  callback->free(env);
}

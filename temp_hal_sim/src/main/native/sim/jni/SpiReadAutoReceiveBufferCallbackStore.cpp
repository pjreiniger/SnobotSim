#include "SpiReadAutoReceiveBufferCallbackStore.h"

#include "support/jni_util.h"
#include <jni.h>
#include "HAL/handles/UnlimitedHandleResource.h"
#include "MockData/HAL_Value.h"
#include "MockData/NotifyListener.h"
#include "HAL/types.h"
#include "SimulatorJNI.h"

using namespace wpi::java;
using namespace sim;

static hal::UnlimitedHandleResource<SIM_JniHandle, SpiReadAutoReceiveBufferCallbackStore, hal::HAL_HandleEnum::SimulationJni>* callbackHandles;

namespace sim {
void InitializeSpiBufferStore() {
  static hal::UnlimitedHandleResource<SIM_JniHandle, SpiReadAutoReceiveBufferCallbackStore, hal::HAL_HandleEnum::SimulationJni> cb;
  callbackHandles = &cb;
}
}

void SpiReadAutoReceiveBufferCallbackStore::create(JNIEnv* env, jobject obj) {
  m_call = JGlobal<jobject>(env, obj);
}

int32_t SpiReadAutoReceiveBufferCallbackStore::performCallback(const char* name, unsigned char* buffer, int32_t numToRead) {
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
      return -1;
    }
  } else if (tryGetEnv == JNI_EVERSION) {
    llvm::outs() << "Invalid JVM Version requested\n";
    llvm::outs().flush();
  }

  auto toCallbackArr = MakeJByteArray(env, llvm::StringRef{reinterpret_cast<const char*>(buffer),
                        static_cast<size_t>(numToRead)});

  jint ret = env->CallIntMethod(m_call, sim::GetBufferCallback(), MakeJString(env, name), toCallbackArr, (jint)numToRead);

  jbyte* fromCallbackArr = reinterpret_cast<jbyte*>(env->GetPrimitiveArrayCritical(toCallbackArr, nullptr));

  for (int i = 0; i < ret; i++) {
    buffer[i] = fromCallbackArr[i];
  }

  env->ReleasePrimitiveArrayCritical(toCallbackArr, fromCallbackArr, JNI_ABORT);

  if (env->ExceptionCheck()) {
    env->ExceptionDescribe();
  }

  if (didAttachThread) {
    vm->DetachCurrentThread();
  }
  return ret;
}

void SpiReadAutoReceiveBufferCallbackStore::free(JNIEnv* env) {
  m_call.free(env);
}

SIM_JniHandle sim::AllocateSpiBufferCallback(JNIEnv* env, jint index, jobject callback, RegisterSpiBufferCallbackFunc createCallback) {

  auto callbackStore = std::make_shared<SpiReadAutoReceiveBufferCallbackStore>();

  auto handle = callbackHandles->Allocate(callbackStore);

  if (handle == HAL_kInvalidHandle) {
    return -1;
  }

  uintptr_t handleAsPtr = static_cast<uintptr_t>(handle);
  void* handleAsVoidPtr = reinterpret_cast<void*>(handleAsPtr);

  callbackStore->create(env, callback);

  auto callbackFunc = [](const char* name, void* param, unsigned char* buffer, int32_t numToRead, int32_t* outputCount){
    uintptr_t handleTmp = reinterpret_cast<uintptr_t>(param);
    SIM_JniHandle handle =
        static_cast<SIM_JniHandle>(handleTmp);
    auto data = callbackHandles->Get(handle);
    if (!data) return;

    *outputCount = data->performCallback(name, buffer, numToRead);
  };

  auto id = createCallback(index, callbackFunc, handleAsVoidPtr);

  callbackStore->setCallbackId(id);

  return handle;
}

void sim::FreeSpiBufferCallback(JNIEnv* env, SIM_JniHandle handle, jint index, FreeSpiBufferCallbackFunc freeCallback) {
  auto callback = callbackHandles->Free(handle);
  freeCallback(index, callback->getCallbackId());
  callback->free(env);
}

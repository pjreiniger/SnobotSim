#include "ConstBufferCallbackStore.h"

#include "support/jni_util.h"
#include <jni.h>
#include "HAL/handles/UnlimitedHandleResource.h"
#include "MockData/HAL_Value.h"
#include "MockData/NotifyListener.h"
#include "HAL/types.h"
#include "SimulatorJNI.h"

using namespace wpi::java;
using namespace sim;

static hal::UnlimitedHandleResource<SIM_JniHandle, ConstBufferCallbackStore, hal::HAL_HandleEnum::Vendor>* callbackHandles;

namespace sim {
void InitializeConstBufferStore() {
  static hal::UnlimitedHandleResource<SIM_JniHandle, ConstBufferCallbackStore, hal::HAL_HandleEnum::Vendor> cb;
  callbackHandles = &cb;
}
}

void ConstBufferCallbackStore::create(JNIEnv* env, jobject obj) {
  m_call = JGlobal<jobject>(env, obj);
}

void ConstBufferCallbackStore::performCallback(const char* name, const uint8_t* buffer, uint32_t length) {
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

  auto toCallbackArr = MakeJByteArray(env, llvm::StringRef{reinterpret_cast<const char*>(buffer),
                        static_cast<size_t>(length)});

  env->CallVoidMethod(m_call, sim::GetConstBufferCallback(), MakeJString(env, name), toCallbackArr, (jint)length);

  if (env->ExceptionCheck()) {
    env->ExceptionDescribe();
  }

  if (didAttachThread) {
    vm->DetachCurrentThread();
  }
}

void ConstBufferCallbackStore::free(JNIEnv* env) {
  m_call.free(env);
}

SIM_JniHandle sim::AllocateConstBufferCallback(JNIEnv* env, jint index, jobject callback, RegisterConstBufferCallbackFunc createCallback) {

  auto callbackStore = std::make_shared<ConstBufferCallbackStore>();

  auto handle = callbackHandles->Allocate(callbackStore);

  if (handle == HAL_kInvalidHandle) {
    return -1;
  }

  uintptr_t handleAsPtr = static_cast<uintptr_t>(handle);
  void* handleAsVoidPtr = reinterpret_cast<void*>(handleAsPtr);

  callbackStore->create(env, callback);

  auto callbackFunc = [](const char* name, void* param, const uint8_t* buffer, uint32_t length){
    uintptr_t handleTmp = reinterpret_cast<uintptr_t>(param);
    SIM_JniHandle handle =
        static_cast<SIM_JniHandle>(handleTmp);
    auto data = callbackHandles->Get(handle);
    if (!data) return;

    data->performCallback(name, buffer, length);
  };

  auto id = createCallback(index, callbackFunc, handleAsVoidPtr);

  callbackStore->setCallbackId(id);

  return handle;
}

void sim::FreeConstBufferCallback(JNIEnv* env, SIM_JniHandle handle, jint index, FreeConstBufferCallbackFunc freeCallback) {
  auto callback = callbackHandles->Free(handle);
  freeCallback(index, callback->getCallbackId());
  callback->free(env);
}
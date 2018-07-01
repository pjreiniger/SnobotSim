#include "SnobotSimUtilities/CallbackStore.h"

#include <iostream>

using namespace wpi::java;

namespace SnobotSim
{

static int handlePtrs[1000];

static std::vector<std::shared_ptr<CallbackStore>> callbackHandles;
static std::vector<std::shared_ptr<BufferCallbackStore>> bufferCallbackHandles;
static std::vector<std::shared_ptr<ConstBufferCallbackStore>> constBufferCallbackHandles;

static JavaVM* gJvm = NULL;

static JClass notifyCallbackCls;
static jmethodID notifyCallbackCallback;

static JClass notifyBufferCallbackCls;
static jmethodID notifyBufferCallbackCallback;

static JClass notifyConstBufferCallbackCls;
static jmethodID notifyConstBufferCallbackCallback;

void ResetCallbacks(JNIEnv* env)
{
    env->GetJavaVM(&gJvm);
    for (int i = 0; i < 1000; ++i)
    {
        handlePtrs[i] = i;
    }

    notifyCallbackCls = JClass(env, "edu/wpi/first/wpilibj/sim/NotifyCallback");
    notifyCallbackCallback = env->GetMethodID(notifyCallbackCls,
            "callbackNative", "(Ljava/lang/String;IJD)V");

    notifyBufferCallbackCls = JClass(env, "edu/wpi/first/wpilibj/sim/BufferCallback");
    notifyBufferCallbackCallback = env->GetMethodID(notifyBufferCallbackCls,
            "callback", "(Ljava/lang/String;Ljava/nio/ByteBuffer;)V");

    notifyConstBufferCallbackCls = JClass(env, "edu/wpi/first/wpilibj/sim/ConstBufferCallback");
    notifyConstBufferCallbackCallback = env->GetMethodID(notifyConstBufferCallbackCls,
            "callback", "(Ljava/lang/String;Ljava/nio/ByteBuffer;)V");

    callbackHandles.clear();
    bufferCallbackHandles.clear();
    constBufferCallbackHandles.clear();
}

void AllocateCallback(JNIEnv* env, jint index, jobject callback,
        jboolean initialNotify, RegisterCallbackFunc createCallback)
{
    auto callbackStore = std::make_shared<CallbackStore>();

    int* handleAsPtr = &handlePtrs[callbackHandles.size()];
    callbackHandles.push_back(callbackStore);

    void* handleAsVoidPtr = reinterpret_cast<void*>(handleAsPtr);

    callbackStore->create(env, callback);

    auto callbackFunc = [](const char* name, void* param, const HAL_Value* value) {
        int* handleTmp = reinterpret_cast<int*>(param);
        auto data = callbackHandles[*handleTmp];
        data->performCallback(name, value);
    };

    auto id = createCallback(index, callbackFunc, handleAsVoidPtr,
            initialNotify);

    callbackStore->setCallbackId(id);
}

void AllocateCallback(JNIEnv* env, jint index, jobject callback,
        jboolean initialNotify,
        RegisterAllCallbackFunc createCallback)
{
    auto callbackStore = std::make_shared<CallbackStore>();

    int* handleAsPtr = &handlePtrs[callbackHandles.size()];
    callbackHandles.push_back(callbackStore);

    void* handleAsVoidPtr = reinterpret_cast<void*>(handleAsPtr);

    callbackStore->create(env, callback);

    auto callbackFunc = [](const char* name, void* param, const HAL_Value* value) {
        int* handleTmp = reinterpret_cast<int*>(param);
        auto data = callbackHandles[*handleTmp];
        data->performCallback(name, value);
    };

    createCallback(index, callbackFunc, handleAsVoidPtr,
            initialNotify);
}

void AllocateChannelCallback(
        JNIEnv* env, jint index, jint channel, jobject callback,
        jboolean initialNotify, RegisterChannelCallbackFunc createCallback)
{
    auto callbackStore = std::make_shared<CallbackStore>();

    int* handleAsPtr = &handlePtrs[callbackHandles.size()];
    callbackHandles.push_back(callbackStore);

    void* handleAsVoidPtr = reinterpret_cast<void*>(handleAsPtr);

    callbackStore->create(env, callback);

    auto callbackFunc = [](const char* name, void* param,
                                const HAL_Value* value) {
        int* handleTmp = reinterpret_cast<int*>(param);
        auto data = callbackHandles[*handleTmp];

        data->performCallback(name, value);
    };

    auto id = createCallback(index, channel, callbackFunc, handleAsVoidPtr,
            initialNotify);

    callbackStore->setCallbackId(id);
}

void AllocateBufferCallback(
        JNIEnv* env, jint index, jobject callback,
        RegisterBufferCallbackFunc createCallback)
{
    auto callbackStore = std::make_shared<BufferCallbackStore>();

    int* handleAsPtr = &handlePtrs[bufferCallbackHandles.size()];
    bufferCallbackHandles.push_back(callbackStore);

    void* handleAsVoidPtr = reinterpret_cast<void*>(handleAsPtr);

    callbackStore->create(env, callback);

    auto callbackFunc = [](const char* name, void* param, uint8_t* buffer,
                                uint32_t length) {
        int* handleTmp = reinterpret_cast<int*>(param);
        auto data = bufferCallbackHandles[*handleTmp];
        data->performCallback(name, buffer, length);
    };

    auto id = createCallback(index, callbackFunc, handleAsVoidPtr);

    callbackStore->setCallbackId(id);
}

void AllocateConstBufferCallback(
        JNIEnv* env, jint index, jobject callback,
        RegisterConstBufferCallbackFunc createCallback)
{
    auto callbackStore = std::make_shared<ConstBufferCallbackStore>();

    int* handleAsPtr = &handlePtrs[constBufferCallbackHandles.size()];
    constBufferCallbackHandles.push_back(callbackStore);

    void* handleAsVoidPtr = reinterpret_cast<void*>(handleAsPtr);

    callbackStore->create(env, callback);

    auto callbackFunc = [](const char* name, void* param, const uint8_t* buffer,
                                uint32_t length) {
        int* handleTmp = reinterpret_cast<int*>(param);
        auto data = constBufferCallbackHandles[*handleTmp];
        data->performCallback(name, buffer, length);
    };

    auto id = createCallback(index, callbackFunc, handleAsVoidPtr);

    callbackStore->setCallbackId(id);
}

void CallbackStore::create(JNIEnv* env, jobject obj)
{
    m_call = JGlobal<jobject>(env, obj);
}

void CallbackStore::performCallback(const char* name, const HAL_Value* value)
{
    JavaVMAttachArgs args = { JNI_VERSION_1_2, 0, 0 };
    JNIEnv* env;
    gJvm->AttachCurrentThread(reinterpret_cast<void**>(&env), &args);

    env->CallVoidMethod(m_call, notifyCallbackCallback, MakeJString(env, name),
            (jint)value->type, (jlong)value->data.v_long,
            (jdouble)value->data.v_double);
}

void ConstBufferCallbackStore::create(JNIEnv* env, jobject obj)
{
    m_call = JGlobal<jobject>(env, obj);
}

void ConstBufferCallbackStore::performCallback(const char* name,
        const uint8_t* buffer,
        uint32_t length)
{
    JavaVMAttachArgs args = { JNI_VERSION_1_2, 0, 0 };
    JNIEnv* env;
    gJvm->AttachCurrentThread(reinterpret_cast<void**>(&env), &args);

    jobject data = env->NewDirectByteBuffer(const_cast<uint8_t*>(buffer), length);
    env->CallVoidMethod(m_call, notifyConstBufferCallbackCallback, MakeJString(env, name), data);
}

void BufferCallbackStore::create(JNIEnv* env, jobject obj)
{
    m_call = JGlobal<jobject>(env, obj);
}

void BufferCallbackStore::performCallback(const char* name, uint8_t* buffer,
        uint32_t length)
{
    JavaVMAttachArgs args = { JNI_VERSION_1_2, 0, 0 };
    JNIEnv* env;
    gJvm->AttachCurrentThread(reinterpret_cast<void**>(&env), &args);

    jobject data = env->NewDirectByteBuffer(buffer, length);
    env->CallVoidMethod(m_call, notifyBufferCallbackCallback, MakeJString(env, name), data);
}

} // namespace SnobotSim


#pragma once

#include <jni.h>
#include <support/jni_util.h>

#include "HAL/Types.h"
#include "HAL/handles/UnlimitedHandleResource.h"
#include "MockData/HAL_Value.h"
#include "MockData/NotifyListener.h"

typedef HAL_Handle SIM_JniHandle;

typedef int32_t (*RegisterCallbackFunc)(int32_t index,
        HAL_NotifyCallback callback,
        void* param, HAL_Bool initialNotify);

typedef int32_t (*RegisterChannelCallbackFunc)(int32_t index, int32_t channel,
        HAL_NotifyCallback callback,
        void* param,
        HAL_Bool initialNotify);

typedef int32_t (*RegisterConstBufferCallbackFunc)(
        int32_t index, HAL_ConstBufferCallback callback, void* param);

typedef int32_t (*RegisterBufferCallbackFunc)(int32_t index,
        HAL_BufferCallback callback,
        void* param);

namespace SnobotSim
{

void ResetCallbacks(JNIEnv* env);

template <typename T>
class JGlobal
{
public:
    JGlobal() = default;

    JGlobal(JNIEnv* env, T obj)
    {
        m_cls = static_cast<T>(env->NewGlobalRef(obj));
    }

    void free(JNIEnv* env)
    {
        if (m_cls)
            env->DeleteGlobalRef(m_cls);
        m_cls = nullptr;
    }

    explicit operator bool() const
    {
        return m_cls;
    }

    operator T() const
    {
        return m_cls;
    }

protected:
    T m_cls = nullptr;
};

class CallbackStore
{
public:
    void create(JNIEnv* env, jobject obj);
    void performCallback(const char* name, const HAL_Value* value);
    void free(JNIEnv* env);
    void setCallbackId(int32_t id)
    {
        callbackId = id;
    }
    int32_t getCallbackId()
    {
        return callbackId;
    }

private:
    JGlobal<jobject> m_call;
    int32_t callbackId;
};

class ConstBufferCallbackStore
{
public:
    void create(JNIEnv* env, jobject obj);
    void performCallback(const char* name, const uint8_t* buffer,
            uint32_t length);
    void free(JNIEnv* env);
    void setCallbackId(int32_t id)
    {
        callbackId = id;
    }
    int32_t getCallbackId()
    {
        return callbackId;
    }

private:
    JGlobal<jobject> m_call;
    int32_t callbackId;
};

class BufferCallbackStore
{
public:
    void create(JNIEnv* env, jobject obj);
    void performCallback(const char* name, uint8_t* buffer, uint32_t length);
    void free(JNIEnv* env);
    void setCallbackId(int32_t id)
    {
        callbackId = id;
    }
    int32_t getCallbackId()
    {
        return callbackId;
    }

private:
    JGlobal<jobject> m_call;
    int32_t callbackId;
};

void AllocateCallback(JNIEnv* env, jint index, jobject callback,
        jboolean initialNotify,
        RegisterCallbackFunc createCallback);

void AllocateChannelCallback(
        JNIEnv* env, jint index, jint channel, jobject callback,
        jboolean initialNotify, RegisterChannelCallbackFunc createCallback);

void AllocateConstBufferCallback(
        JNIEnv* env, jint index, jobject callback,
        RegisterConstBufferCallbackFunc createCallback);

void AllocateBufferCallback(JNIEnv* env, jint index, jobject callback,
        RegisterBufferCallbackFunc createCallback);

} // namespace SnobotSim

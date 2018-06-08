
#include <jni.h>
#include <wpi/jni_util.h>

#include <iostream>

#include "CtreSimMocks/MockHooks.h"
#include "com_snobot_simulator_ctre_CtreJni.h"

using namespace wpi::java;

namespace SnobotSimJava
{
JavaVM* sJvm = NULL;
static JClass sCtreBufferCallbackClazz;
static jmethodID sCtreBufferCallbackCallback;
static jobject sCtreMotorCallbackObject = NULL;
static jobject sCtrePigeonCallbackObject = NULL;
} // namespace SnobotSimJava

extern "C" {

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved)
{
    SnobotSimJava::sJvm = vm;

    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK)
    {
        return JNI_ERR;
    }

    SnobotSimJava::sCtreBufferCallbackClazz = JClass(env, "com/snobot/simulator/ctre/CtreCallback");
    if (!SnobotSimJava::sCtreBufferCallbackClazz)
    {
        std::cout << "UH OH" << std::endl;
        return JNI_ERR;
    }

    SnobotSimJava::sCtreBufferCallbackCallback = env->GetMethodID(SnobotSimJava::sCtreBufferCallbackClazz, "callback", "(Ljava/lang/String;ILjava/nio/ByteBuffer;I)V");
    if (!SnobotSimJava::sCtreBufferCallbackCallback)
    {
        std::cout << "UH NOES" << std::endl;
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM* vm, void* reserved)
{
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK)
    {
        return;
    }

    SnobotSimJava::sCtreBufferCallbackClazz.free(env);

    if (SnobotSimJava::sCtreMotorCallbackObject)
    {
        env->DeleteGlobalRef(SnobotSimJava::sCtreMotorCallbackObject);
    }
    SnobotSimJava::sCtreMotorCallbackObject = nullptr;

    if (SnobotSimJava::sCtrePigeonCallbackObject)
    {
        env->DeleteGlobalRef(SnobotSimJava::sCtrePigeonCallbackObject);
    }
    SnobotSimJava::sCtrePigeonCallbackObject = nullptr;

    SnobotSimJava::sJvm = NULL;
}

JNIEXPORT jint JNICALL Java_com_snobot_simulator_ctre_CtreJni_registerCanMotorCallback(JNIEnv* aEnv, jclass, jobject callback)
{
    SnobotSimJava::sCtreMotorCallbackObject = aEnv->NewGlobalRef(callback);
    auto callbackFunc = [](const char* name,
                                uint32_t messageId,
                                uint8_t* buffer,
                                int length) {

        JavaVMAttachArgs args = { JNI_VERSION_1_6, 0, 0 };
        JNIEnv* env;
        SnobotSimJava::sJvm->AttachCurrentThread(reinterpret_cast<void**>(&env), &args);

        jobject dataBuffer = env->NewDirectByteBuffer(const_cast<uint8_t*>(buffer), static_cast<uint32_t>(length));
        jstring nameString = MakeJString(env, name);

        if (SnobotSimJava::sCtreMotorCallbackObject)
        {
            env->CallVoidMethod(SnobotSimJava::sCtreMotorCallbackObject, SnobotSimJava::sCtreBufferCallbackCallback, nameString,
                    messageId, dataBuffer, length);
        }
    };
    SnobotSim::SetMotControllerCallback(callbackFunc);

    return 0;
}

JNIEXPORT void JNICALL Java_com_snobot_simulator_ctre_CtreJni_cancelCanMotorCallback(JNIEnv* env, jclass, jint)
{
    if (SnobotSimJava::sCtreMotorCallbackObject)
    {
        env->DeleteGlobalRef(SnobotSimJava::sCtreMotorCallbackObject);
    }
    SnobotSimJava::sCtreMotorCallbackObject = NULL;
}

JNIEXPORT jint JNICALL Java_com_snobot_simulator_ctre_CtreJni_registerCanPigeonImuCallback(JNIEnv* aEnv, jclass, jobject callback)
{
    SnobotSimJava::sCtrePigeonCallbackObject = aEnv->NewGlobalRef(callback);

    auto callbackFunc = [](const char* name,
                                uint32_t messageId,
                                uint8_t* buffer,
                                int length) {

        JavaVMAttachArgs args = { JNI_VERSION_1_6, 0, 0 };
        JNIEnv* env;
        SnobotSimJava::sJvm->AttachCurrentThread(reinterpret_cast<void**>(&env), &args);

        jobject dataBuffer = env->NewDirectByteBuffer(const_cast<uint8_t*>(buffer), static_cast<uint32_t>(length));
        jstring nameString = MakeJString(env, name);

        if (SnobotSimJava::sCtrePigeonCallbackObject)
        {
            env->CallVoidMethod(SnobotSimJava::sCtrePigeonCallbackObject, SnobotSimJava::sCtreBufferCallbackCallback, nameString,
                    messageId, dataBuffer, length);
        }
    };

    SnobotSim::SetPigeonCallback(callbackFunc);
    return 0;
}

JNIEXPORT void JNICALL Java_com_snobot_simulator_ctre_CtreJni_cancelCanPigeonImuCallback(JNIEnv* env, jclass, jint)
{
    if (SnobotSimJava::sCtrePigeonCallbackObject)
    {
        env->DeleteGlobalRef(SnobotSimJava::sCtrePigeonCallbackObject);
    }
    SnobotSimJava::sCtrePigeonCallbackObject = NULL;
}

} // extern "C"

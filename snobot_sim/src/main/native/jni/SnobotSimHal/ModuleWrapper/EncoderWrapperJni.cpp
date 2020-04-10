
#include <jni.h>

#include <cassert>
#include <sstream>

#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IEncoderWrapper.h"
#include "SnobotSim/PortUnwrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "com_snobot_simulator_jni_module_wrapper_EncoderWrapperJni.h"
#include "wpi/jni_util.h"

using namespace wpi::java;

extern "C" {
/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    isInitialized
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_isInitialized
  (JNIEnv*, jclass, jint aPortHandle)
{
    return SensorActuatorRegistry::Get().GetIEncoderWrapper(aPortHandle)->IsInitialized();
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    getHandle
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_getHandle
  (JNIEnv*, jclass, jint handleA, jint handleB)
{
    int basicHandle = (handleA << 8) + handleB;
    std::shared_ptr<IEncoderWrapper> wrapper = SensorActuatorRegistry::Get().GetIEncoderWrapper(basicHandle, false);
    if (wrapper)
    {
        return basicHandle;
    }

    int packedHandle = (WrapPort(handleA) << 8) + WrapPort(handleB);
    wrapper = SensorActuatorRegistry::Get().GetIEncoderWrapper(packedHandle, false);
    if (wrapper)
    {
        return packedHandle;
    }

    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL,
            "Could not find encoder with ports (" << handleA << ", " << handleB << "), "
                                                  << "tried " << basicHandle << " and " << packedHandle);

    return -1;
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    setName
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_setName
  (JNIEnv* env, jclass, jint aPortHandle, jstring aName)
{
    std::shared_ptr<IEncoderWrapper> wrapper = SensorActuatorRegistry::Get().GetIEncoderWrapper(aPortHandle);
    if (wrapper)
    {
        wrapper->SetName(env->GetStringUTFChars(aName, NULL));
    }
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    getName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_getName
  (JNIEnv* env, jclass, jint portHandle)
{
    jstring output = MakeJString(env, SensorActuatorRegistry::Get().GetIEncoderWrapper(portHandle)->GetName());

    return output;
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    getWantsHidden
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_getWantsHidden
  (JNIEnv*, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetIEncoderWrapper(portHandle)->WantsHidden();
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    createSimulator
 * Signature: (ILjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_createSimulator
  (JNIEnv* env, jclass, jint aHandle, jstring aType)
{
    return FactoryContainer::Get().GetEncoderFactory()->Create(aHandle, env->GetStringUTFChars(aType, NULL));
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    removeSimluator
 * Signature: (I)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_removeSimluator
  (JNIEnv*, jclass, jint)
{
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    connectSpeedController
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_connectSpeedController
  (JNIEnv*, jclass, jint aEncoderhandle, jint aScHandle)
{
    bool success = false;
    std::shared_ptr<IEncoderWrapper> encoder = GetSensorActuatorHelper::GetIEncoderWrapper(aEncoderhandle);

    if (encoder)
    {
        std::shared_ptr<ISpeedControllerWrapper> speedController;
        if (aScHandle != -1)
        {
            speedController = GetSensorActuatorHelper::GetISpeedControllerWrapper(aScHandle);
        }

        if (speedController)
        {
            encoder->SetSpeedController(speedController);
            success = true;
        }
    }

    return success;
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    isHookedUp
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_isHookedUp
  (JNIEnv*, jclass, jint portHandle)
{
    std::shared_ptr<IEncoderWrapper> encoder = GetSensorActuatorHelper::GetIEncoderWrapper(portHandle);
    if (encoder)
    {
        return encoder->IsHookedUp();
    }
    return false;
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    setPosition
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_setPosition
  (JNIEnv*, jclass, jint portHandle, jdouble aPosition)
{
    std::shared_ptr<IEncoderWrapper> encoder = GetSensorActuatorHelper::GetIEncoderWrapper(portHandle);
    if (encoder)
    {
        encoder->SetPosition(aPosition);
    }
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    setVelocity
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_setVelocity
  (JNIEnv*, jclass, jint portHandle, jdouble aVelocity)
{
    std::shared_ptr<IEncoderWrapper> encoder = GetSensorActuatorHelper::GetIEncoderWrapper(portHandle);
    if (encoder)
    {
        encoder->SetVelocity(aVelocity);
    }
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    getHookedUpId
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_getHookedUpId
  (JNIEnv*, jclass, jint portHandle)
{
    std::shared_ptr<IEncoderWrapper> encoder = GetSensorActuatorHelper::GetIEncoderWrapper(portHandle);
    int output = -1;

    if (encoder)
    {
        std::shared_ptr<ISpeedControllerWrapper> sc = encoder->GetSpeedController();
        if (sc)
        {
            output = sc->GetId();
        }
    }

    return output;
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    getDistance
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_getDistance
  (JNIEnv*, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetIEncoderWrapper(portHandle)->GetDistance();
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    getVelocity
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_getVelocity
  (JNIEnv*, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetIEncoderWrapper(portHandle)->GetVelocity();
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    reset
 * Signature: (I)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_reset
  (JNIEnv*, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetIEncoderWrapper(portHandle)->Reset();
}

/*
 * Class:     com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni
 * Method:    getPortList
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL
Java_com_snobot_simulator_jni_module_1wrapper_EncoderWrapperJni_getPortList
  (JNIEnv* env, jclass)
{
    const std::map<int, std::shared_ptr<IEncoderWrapper>>& encoders = SensorActuatorRegistry::Get().GetIEncoderWrapperMap();

    jintArray output = env->NewIntArray(encoders.size());

    jint values[30];

    std::map<int, std::shared_ptr<IEncoderWrapper>>::const_iterator iter = encoders.begin();

    int ctr = 0;
    for (; iter != encoders.end(); ++iter)
    {
        values[ctr++] = iter->first;
    }

    env->SetIntArrayRegion(output, 0, encoders.size(), values);

    return output;
}

} // extern "C"

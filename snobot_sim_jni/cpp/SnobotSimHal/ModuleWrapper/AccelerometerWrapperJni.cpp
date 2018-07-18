
#include <jni.h>

#include <cassert>

#include "SnobotSim/SensorActuatorRegistry.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAccelerometerWrapper.h"
#include "com_snobot_simulator_jni_module_wrapper_GyroWrapperJni.h"
#include "wpi/jni_util.h"

using namespace wpi::java;

extern "C"
{
/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AccelerometerWrapperJni
 * Method:    isInitialized
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AccelerometerWrapperJni_isInitialized
  (JNIEnv *, jclass, jint aPortHandle)
{
	return SensorActuatorRegistry::Get().GetIAccelerometerWrapper(aPortHandle)->IsInitialized();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AccelerometerWrapperJni
 * Method:    setName
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AccelerometerWrapperJni_setName
  (JNIEnv * env, jclass, jint aPortHandle, jstring aName)
{
    SensorActuatorRegistry::Get().GetIAccelerometerWrapper(aPortHandle)->SetName(env->GetStringUTFChars(aName, NULL));
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AccelerometerWrapperJni
 * Method:    getName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AccelerometerWrapperJni_getName
  (JNIEnv * env, jclass, jint aPortHandle)
{
    jstring output = MakeJString(env, SensorActuatorRegistry::Get().GetIAccelerometerWrapper(aPortHandle)->GetName());
    return output;
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AccelerometerWrapperJni
 * Method:    getWantsHidden
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AccelerometerWrapperJni_getWantsHidden
  (JNIEnv * env, jclass, jint aPortHandle)
{
    return SensorActuatorRegistry::Get().GetIAccelerometerWrapper(aPortHandle)->WantsHidden();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AccelerometerWrapperJni
 * Method:    createSimulator
 * Signature: (ILjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AccelerometerWrapperJni_createSimulator
  (JNIEnv * env, jclass, jint, jstring)
{
	return false;
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AccelerometerWrapperJni
 * Method:    removeSimluator
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AccelerometerWrapperJni_removeSimluator
  (JNIEnv *, jclass, jint)
{

}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AccelerometerWrapperJni
 * Method:    getAcceleration
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AccelerometerWrapperJni_getAcceleration
  (JNIEnv * env, jclass, jint aPortHandle)
{
    return SensorActuatorRegistry::Get().GetIAccelerometerWrapper(aPortHandle)->GetAcceleration();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AccelerometerWrapperJni
 * Method:    setAcceleration
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AccelerometerWrapperJni_setAcceleration
  (JNIEnv *, jclass, jint aPortHandle, jdouble aAccel)
{
    return SensorActuatorRegistry::Get().GetIAccelerometerWrapper(aPortHandle)->SetAcceleration(aAccel);
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AccelerometerWrapperJni
 * Method:    getPortList
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AccelerometerWrapperJni_getPortList
  (JNIEnv * env, jclass)
{
    const std::map<int, std::shared_ptr<IAccelerometerWrapper>>& accelerometerWrappers =
            SensorActuatorRegistry::Get().GetIAccelerometerWrapperMap();

    jintArray output = env->NewIntArray(accelerometerWrappers.size());

    jint values[30];

    std::map<int, std::shared_ptr<IAccelerometerWrapper>>::const_iterator iter =
            accelerometerWrappers.begin();

    int ctr = 0;
    for (; iter != accelerometerWrappers.end(); ++iter)
    {
        values[ctr++] = iter->first;
    }

    env->SetIntArrayRegion(output, 0, accelerometerWrappers.size(), values);

    return output;
}


}  // extern "C"

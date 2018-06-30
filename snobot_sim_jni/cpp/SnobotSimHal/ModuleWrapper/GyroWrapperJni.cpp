
#include <jni.h>

#include <cassert>

#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IGyroWrapper.h"
#include "SnobotSim/ModuleWrapper/Factories/GyroFactory.h"
#include "com_snobot_simulator_jni_module_wrapper_GyroWrapperJni.h"
#include "support/jni_util.h"

using namespace wpi::java;

extern "C"
{
/*
 * Class:     com_snobot_simulator_jni_module_wrapper_GyroWrapperJni
 * Method:    isInitialized
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_GyroWrapperJni_isInitialized
  (JNIEnv *, jclass, jint aPortHandle)
{
	return SensorActuatorRegistry::Get().GetIGyroWrapper(aPortHandle)->IsInitialized();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_GyroWrapperJni
 * Method:    register
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_GyroWrapperJni_register
  (JNIEnv * env, jclass, jint aHandle, jstring aName)
{
//    std::shared_ptr<GyroWrapper> gyroWrapper(new GyroWrapper(env->GetStringUTFChars(aName, NULL)));
//    SensorActuatorRegistry::Get().Register(aHandle, gyroWrapper);
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_GyroWrapperJni
 * Method:    setName
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_GyroWrapperJni_setName
  (JNIEnv * env, jclass, jint aPortHandle, jstring aName)
{
    std::shared_ptr<IGyroWrapper> wrapper = GetSensorActuatorHelper::GetIGyroWrapper(aPortHandle);
    if(wrapper)
    {
        wrapper->SetName(env->GetStringUTFChars(aName, NULL));
    }
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_GyroWrapperJni
 * Method:    getName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_snobot_simulator_jni_module_1wrapper_GyroWrapperJni_getName
  (JNIEnv * env, jclass, jint aPortHandle)
{
    jstring output = MakeJString(env, SensorActuatorRegistry::Get().GetIGyroWrapper(aPortHandle)->GetName());
    return output;
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_GyroWrapperJni
 * Method:    getWantsHidden
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_GyroWrapperJni_getWantsHidden
  (JNIEnv *, jclass, jint aPortHandle)
{
    return SensorActuatorRegistry::Get().GetIGyroWrapper(aPortHandle)->WantsHidden();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_GyroWrapperJni
 * Method:    createSimulator
 * Signature: (ILjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_GyroWrapperJni_createSimulator
  (JNIEnv * env, jclass, jint aHandle, jstring aType)
{
	static GyroFactory factory;

	return factory.Create(aHandle, env->GetStringUTFChars(aType, NULL));
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_GyroWrapperJni
 * Method:    removeSimluator
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_GyroWrapperJni_removeSimluator
  (JNIEnv *, jclass, jint)
{

}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_GyroWrapperJni
 * Method:    getVoltagePercentage
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_GyroWrapperJni_getAngle
  (JNIEnv *, jclass, jint aPortHandle)
{
    return SensorActuatorRegistry::Get().GetIGyroWrapper(aPortHandle)->GetAngle();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_GyroWrapperJni
 * Method:    setAngle
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_GyroWrapperJni_setAngle
  (JNIEnv *, jclass, jint aPortHandle, jdouble aAngle)
{
    SensorActuatorRegistry::Get().GetIGyroWrapper(aPortHandle)->SetAngle(aAngle);
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_GyroWrapperJni
 * Method:    reset
 * Signature: (I)D
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_GyroWrapperJni_reset
  (JNIEnv *, jclass, jint aPortHandle)
{
    SensorActuatorRegistry::Get().GetIGyroWrapper(aPortHandle)->SetAngle(0);
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_GyroWrapperJni
 * Method:    getPortList
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_snobot_simulator_jni_module_1wrapper_GyroWrapperJni_getPortList
  (JNIEnv * env, jclass)
{
    const std::map<int, std::shared_ptr<IGyroWrapper>>& gyroWrappers =
            SensorActuatorRegistry::Get().GetIGyroWrapperMap();

    jintArray output = env->NewIntArray(gyroWrappers.size());

    jint values[30];

    std::map<int, std::shared_ptr<IGyroWrapper>>::const_iterator iter =
            gyroWrappers.begin();

    int ctr = 0;
    for (; iter != gyroWrappers.end(); ++iter)
    {
        values[ctr++] = iter->first;
    }

    env->SetIntArrayRegion(output, 0, gyroWrappers.size(), values);

    return output;
}

}  // extern "C"

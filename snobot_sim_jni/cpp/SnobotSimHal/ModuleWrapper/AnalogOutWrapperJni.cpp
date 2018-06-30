
#include <jni.h>

#include <cassert>

#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAnalogOutWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "com_snobot_simulator_jni_module_wrapper_AnalogOutWrapperJni.h"
#include "SnobotSim/ModuleWrapper/Factories/AnalogOutFactory.h"
#include "support/jni_util.h"

using namespace wpi::java;

extern "C"
{

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogOutWrapperJni
 * Method:    isInitialized
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogOutWrapperJni_isInitialized
  (JNIEnv *, jclass, jint aPortHandle)
{
	return SensorActuatorRegistry::Get().GetIAnalogOutWrapper(aPortHandle)->IsInitialized();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogOutWrapperJni
 * Method:    setName
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogOutWrapperJni_setName
  (JNIEnv * env, jclass, jint aPortHandle, jstring aName)
{
    std::shared_ptr<IAnalogOutWrapper> wrapper = GetSensorActuatorHelper::GetIAnalogOutWrapper(aPortHandle);
    if(wrapper)
    {
        wrapper->SetName(env->GetStringUTFChars(aName, NULL));
    }
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogOutWrapperJni
 * Method:    getName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogOutWrapperJni_getName
  (JNIEnv * env, jclass, jint portHandle)
{
    return MakeJString(env,
            SensorActuatorRegistry::Get().GetIAnalogOutWrapper(portHandle)->GetName());
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogOutWrapperJni
 * Method:    getWantsHidden
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogOutWrapperJni_getWantsHidden
  (JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetIAnalogOutWrapper(portHandle)->WantsHidden();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogOutWrapperJni
 * Method:    createSimulator
 * Signature: (ILjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogOutWrapperJni_createSimulator
  (JNIEnv * env, jclass, jint aHandle, jstring aType)
{
	static AnalogOutFactory factory;

	return factory.Create(aHandle, env->GetStringUTFChars(aType, NULL));
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogOutWrapperJni
 * Method:    removeSimluator
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogOutWrapperJni_removeSimluator
  (JNIEnv *, jclass, jint)
{

}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogOutWrapperJni
 * Method:    getVoltage
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogOutWrapperJni_getVoltagee(
        JNIEnv * env, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetIAnalogOutWrapper(portHandle)->GetVoltage();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogOutWrapperJni
 * Method:    getPortList
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogOutWrapperJni_getPortList
  (JNIEnv * env, jclass)
{
    const std::map<int, std::shared_ptr<IAnalogOutWrapper>>& analogWrappers =
            SensorActuatorRegistry::Get().GetIAnalogOutWrapperMap();

    jintArray output = env->NewIntArray(analogWrappers.size());

    jint values[30];

    std::map<int, std::shared_ptr<IAnalogOutWrapper>>::const_iterator iter =
            analogWrappers.begin();

    int ctr = 0;
    for (; iter != analogWrappers.end(); ++iter)
    {
        values[ctr++] = iter->first;
    }

    env->SetIntArrayRegion(output, 0, analogWrappers.size(), values);

    return output;
}

}  // extern "C"

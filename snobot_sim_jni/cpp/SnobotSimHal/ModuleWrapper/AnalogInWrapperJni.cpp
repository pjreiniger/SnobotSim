
#include <jni.h>

#include <cassert>

#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAnalogInWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "com_snobot_simulator_jni_module_wrapper_AnalogInWrapperJni.h"
#include "SnobotSim/ModuleWrapper/Factories/AnalogInFactory.h"
#include "support/jni_util.h"

using namespace wpi::java;

extern "C"
{

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogInWrapperJni
 * Method:    isInitialized
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogInWrapperJni_isInitialized
  (JNIEnv *, jclass, jint  aPortHandle)
{
	return SensorActuatorRegistry::Get().GetIAnalogInWrapper(aPortHandle)->IsInitialized();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogInWrapperJni
 * Method:    setName
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogInWrapperJni_setName
  (JNIEnv * env, jclass, jint aPortHandle, jstring aName)
{
    std::shared_ptr<IAnalogInWrapper> wrapper = GetSensorActuatorHelper::GetIAnalogInWrapper(aPortHandle);
    if(wrapper)
    {
        wrapper->SetName(env->GetStringUTFChars(aName, NULL));
    }
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogInWrapperJni
 * Method:    getName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogInWrapperJni_getName
  (JNIEnv * env, jclass, jint portHandle)
{
    return MakeJString(env,
            SensorActuatorRegistry::Get().GetIAnalogInWrapper(portHandle)->GetName());
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogInWrapperJni
 * Method:    getWantsHidden
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogInWrapperJni_getWantsHidden
  (JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetIAnalogInWrapper(portHandle)->WantsHidden();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogInWrapperJni
 * Method:    createSimulator
 * Signature: (ILjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogInWrapperJni_createSimulator
  (JNIEnv * env, jclass, jint aHandle, jstring aType)
{
	static AnalogInFactory factory;

	return factory.Create(aHandle, env->GetStringUTFChars(aType, NULL));
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogInWrapperJni
 * Method:    removeSimluator
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogInWrapperJni_removeSimluator
  (JNIEnv *, jclass, jint)
{

}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogInWrapperJni
 * Method:    getVoltage
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogInWrapperJni_getVoltage(
        JNIEnv * env, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetIAnalogInWrapper(portHandle)->GetVoltage();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogInWrapperJni
 * Method:    getPortList
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogInWrapperJni_getPortList
  (JNIEnv * env, jclass)
{
    const std::map<int, std::shared_ptr<IAnalogInWrapper>>& analogWrappers =
            SensorActuatorRegistry::Get().GetIAnalogInWrapperMap();

    jintArray output = env->NewIntArray(analogWrappers.size());

    jint values[30];

    std::map<int, std::shared_ptr<IAnalogInWrapper>>::const_iterator iter =
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

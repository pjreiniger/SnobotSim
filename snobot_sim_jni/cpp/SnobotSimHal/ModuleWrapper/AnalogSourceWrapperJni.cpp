
#include <jni.h>

#include <cassert>

#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/ModuleWrapper/AnalogSourceWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "com_snobot_simulator_jni_module_wrapper_AnalogSourceWrapperJni.h"
#include "wpi/jni_util.h"

using namespace wpi::java;

extern "C"
{

/*
 * Class:     Java_com_snobot_simulator_jni_module_1wrapper_AnalogSourceWrapperJni_setName
 * Method:    setName
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogSourceWrapperJni_setName
  (JNIEnv * env, jclass, jint aPortHandle, jstring aName)
{
    std::shared_ptr<AnalogSourceWrapper> wrapper = GetSensorActuatorHelper::GetAnalogSourceWrapper(aPortHandle);
    if(wrapper)
    {
        wrapper->SetName(env->GetStringUTFChars(aName, NULL));
    }
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogSourceWrapperJni
 * Method:    getName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogSourceWrapperJni_getName
  (
        JNIEnv * env, jclass, jint portHandle)
{
    return MakeJString(env,
            SensorActuatorRegistry::Get().GetAnalogSourceWrapper(portHandle)->GetName());
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogSourceWrapperJni
 * Method:    getWantsHidden
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogSourceWrapperJni_getWantsHidden(JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetAnalogSourceWrapper(portHandle)->WantsHidden();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogSourceWrapperJni
 * Method:    getVoltage
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogSourceWrapperJni_getVoltage(
        JNIEnv * env, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetAnalogSourceWrapper(portHandle)->GetVoltage();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_AnalogSourceWrapperJni
 * Method:    getPortList
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_snobot_simulator_jni_module_1wrapper_AnalogSourceWrapperJni_getPortList
  (
        JNIEnv * env, jclass)
{
    const std::map<int, std::shared_ptr<AnalogSourceWrapper>>& analogWrappers =
            SensorActuatorRegistry::Get().GetAnalogSourceWrapperMap();

    jintArray output = env->NewIntArray(analogWrappers.size());

    jint values[30];

    std::map<int, std::shared_ptr<AnalogSourceWrapper>>::const_iterator iter =
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


#include <jni.h>

#include <cassert>

#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/ModuleWrapper/RelayWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "com_snobot_simulator_jni_module_wrapper_RelayWrapperJni.h"
#include "support/jni_util.h"

using namespace wpi::java;

extern "C"
{

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_RelayWrapperJni
 * Method:    setName
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_RelayWrapperJni_setName
  (JNIEnv * env, jclass, jint aPortHandle, jstring aName)
{
    std::shared_ptr<RelayWrapper> wrapper = GetSensorActuatorHelper::GetRelayWrapper(aPortHandle);
    if(wrapper)
    {
        wrapper->SetName(env->GetStringUTFChars(aName, NULL));
    }
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_RelayWrapperJni
 * Method:    getName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_snobot_simulator_jni_module_1wrapper_RelayWrapperJni_getName(
        JNIEnv * env, jclass, jint portHandle)
{
    return MakeJString(env,
            SensorActuatorRegistry::Get().GetRelayWrapper(portHandle)->GetName());
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_RelayWrapperJni
 * Method:    getWantsHidden
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_RelayWrapperJni_getWantsHidden(JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetRelayWrapper(portHandle)->WantsHidden();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_RelayWrapperJni
 * Method:    getFowardValue
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_RelayWrapperJni_getFowardValue(JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetRelayWrapper(portHandle)->GetRelayForwards();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_RelayWrapperJni
 * Method:    getReverseValue
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_RelayWrapperJni_getReverseValue(JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetRelayWrapper(portHandle)->GetRelayReverse();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_RelayWrapperJni
 * Method:    getPortList
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_snobot_simulator_jni_module_1wrapper_RelayWrapperJni_getPortList(
        JNIEnv * env, jclass)
{
    const std::map<int, std::shared_ptr<RelayWrapper>>& relays =
            SensorActuatorRegistry::Get().GetRelayWrapperMap();

    jintArray output = env->NewIntArray(relays.size());

    jint values[30];

    std::map<int, std::shared_ptr<RelayWrapper>>::const_iterator iter =
            relays.begin();

    int ctr = 0;
    for (; iter != relays.end(); ++iter)
    {
        values[ctr++] = iter->first;
    }

    env->SetIntArrayRegion(output, 0, relays.size(), values);

    return output;
}

}  // extern "C"

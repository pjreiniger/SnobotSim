
#include <jni.h>

#include <cassert>

#include "mockdata/DIOData.h"
#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IDigitalIoWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "com_snobot_simulator_jni_module_wrapper_DigitalSourceWrapperJni.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "wpi/jni_util.h"

using namespace wpi::java;

extern "C"
{

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_DigitalSourceWrapperJni
 * Method:    isInitialized
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_DigitalSourceWrapperJni_isInitialized
  (JNIEnv *, jclass, jint aPortHandle)
{
	return SensorActuatorRegistry::Get().GetIDigitalIoWrapper(aPortHandle)->IsInitialized();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_DigitalSourceWrapperJni
 * Method:    setName
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_DigitalSourceWrapperJni_setName
  (JNIEnv * env, jclass, jint aPortHandle, jstring aName)
{
    std::shared_ptr<IDigitalIoWrapper> wrapper = GetSensorActuatorHelper::GetIDigitalIoWrapper(aPortHandle);
    if(wrapper)
    {
        wrapper->SetName(env->GetStringUTFChars(aName, NULL));
    }
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_DigitalSourceWrapperJni
 * Method:    getName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_snobot_simulator_jni_module_1wrapper_DigitalSourceWrapperJni_getName(
        JNIEnv * env, jclass, jint portHandle)
{
    return MakeJString(env,
            SensorActuatorRegistry::Get().GetIDigitalIoWrapper(portHandle)->GetName());
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_DigitalSourceWrapperJni
 * Method:    getWantsHidden
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_DigitalSourceWrapperJni_getWantsHidden(JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetIDigitalIoWrapper(portHandle)->WantsHidden();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_DigitalSourceWrapperJni
 * Method:    createSimulator
 * Signature: (ILjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_DigitalSourceWrapperJni_createSimulator
  (JNIEnv * env, jclass, jint aHandle, jstring aType)
{
	return FactoryContainer::Get().GetDigitalIoFactory()->Create(aHandle, env->GetStringUTFChars(aType, NULL));
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_DigitalSourceWrapperJni
 * Method:    removeSimluator
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_DigitalSourceWrapperJni_removeSimluator
  (JNIEnv *, jclass, jint)
{

}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_DigitalSourceWrapperJni
 * Method:    getState
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_DigitalSourceWrapperJni_getState(
        JNIEnv * env, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetIDigitalIoWrapper(portHandle)->Get();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_DigitalSourceWrapperJni
 * Method:    setState
 * Signature: (IZ)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_DigitalSourceWrapperJni_setState
  (JNIEnv *, jclass, jint portHandle, jboolean value)
{
    HALSIM_SetDIOValue(portHandle, value);
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_DigitalSourceWrapperJni
 * Method:    getPortList
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_snobot_simulator_jni_module_1wrapper_DigitalSourceWrapperJni_getPortList(
        JNIEnv * env, jclass)
{
    const std::map<int, std::shared_ptr<IDigitalIoWrapper>>& digitalSources =
            SensorActuatorRegistry::Get().GetIDigitalIoWrapperMap();

    jintArray output = env->NewIntArray(digitalSources.size());

    jint values[30];

    std::map<int, std::shared_ptr<IDigitalIoWrapper>>::const_iterator iter =
            digitalSources.begin();

    int ctr = 0;
    for (; iter != digitalSources.end(); ++iter)
    {
        values[ctr++] = iter->first;
    }

    env->SetIntArrayRegion(output, 0, digitalSources.size(), values);

    return output;
}


}  // extern "C"

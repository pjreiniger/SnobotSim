
#include <jni.h>

#include <cassert>

#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISolenoidWrapper.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "com_snobot_simulator_jni_module_wrapper_SolenoidWrapperJni.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "wpi/jni_util.h"

using namespace wpi::java;

extern "C"
{
/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SolenoidWrapperJni
 * Method:    isInitialized
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SolenoidWrapperJni_isInitialized
  (JNIEnv *, jclass, jint aPortHandle)
{
	return SensorActuatorRegistry::Get().GetISolenoidWrapper(aPortHandle)->IsInitialized();
}
/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SolenoidWrapperJni
 * Method:    setName
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SolenoidWrapperJni_setName
  (JNIEnv * env, jclass, jint aPortHandle, jstring aName)
{
    std::shared_ptr<ISolenoidWrapper> wrapper = GetSensorActuatorHelper::GetISolenoidWrapper(aPortHandle);
    if(wrapper)
    {
        wrapper->SetName(env->GetStringUTFChars(aName, NULL));
    }
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SolenoidWrapperJni
 * Method:    getName
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SolenoidWrapperJni_getName(JNIEnv * env, jclass, jint portHandle)
{

    jstring output = MakeJString(env, SensorActuatorRegistry::Get().GetISolenoidWrapper(portHandle)->GetName());

    return output;
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SolenoidWrapperJni
 * Method:    getWantsHidden
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SolenoidWrapperJni_getWantsHidden(JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetISolenoidWrapper(portHandle)->WantsHidden();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SolenoidWrapperJni
 * Method:    createSimulator
 * Signature: (ILjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SolenoidWrapperJni_createSimulator
  (JNIEnv * env, jclass, jint aHandle, jstring aType)
{
	return FactoryContainer::Get().GetSolenoidFactory()->Create(aHandle, env->GetStringUTFChars(aType, NULL));
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SolenoidWrapperJni
 * Method:    removeSimluator
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SolenoidWrapperJni_removeSimluator
  (JNIEnv *, jclass, jint)
{

}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SolenoidWrapperJni
 * Method:    get
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SolenoidWrapperJni_get(JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetISolenoidWrapper(portHandle)->GetState();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SolenoidWrapperJni
 * Method:    getPortList
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SolenoidWrapperJni_getPortList(JNIEnv * env, jclass)
{
    const std::map<int, std::shared_ptr<ISolenoidWrapper>>& solenoids = SensorActuatorRegistry::Get().GetISolenoidWrapperMap();

    jintArray output = env->NewIntArray(solenoids.size());

    jint values[30];

    std::map<int, std::shared_ptr<ISolenoidWrapper>>::const_iterator iter = solenoids.begin();

    int ctr = 0;
    for (; iter != solenoids.end(); ++iter)
    {
        values[ctr++] = iter->first;
    }

    env->SetIntArrayRegion(output, 0, solenoids.size(), values);

    return output;
}


}  // extern "C"

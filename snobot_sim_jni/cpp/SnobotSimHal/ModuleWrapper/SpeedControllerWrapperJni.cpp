
#include <jni.h>

#include <cassert>

#include "../ConversionUtils.h"
#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/SpeedControllerWrapper.h"
#include "SnobotSim/MotorSim/GravityLoadDcMotorSim.h"
#include "SnobotSim/MotorSim/RotationalLoadDcMotorSim.h"
#include "SnobotSim/MotorSim/SimpleMotorSimulator.h"
#include "SnobotSim/MotorSim/StaticLoadDcMotorSim.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni.h"
#include "support/jni_util.h"

using namespace wpi::java;

extern "C"
{

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    setName
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_setName
  (JNIEnv * env, jclass, jint aPortHandle, jstring aName)
{
    std::shared_ptr<SpeedControllerWrapper> wrapper = GetSensorActuatorHelper::GetSpeedControllerWrapper(aPortHandle);
    if(wrapper)
    {
        wrapper->SetName(env->GetStringUTFChars(aName, NULL));
    }
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getName
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getName(JNIEnv * env, jclass, jint portHandle)
{

    jstring output = MakeJString(env, SensorActuatorRegistry::Get().GetSpeedControllerWrapper(portHandle)->GetName());

    return output;
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getWantsHidden
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getWantsHidden(JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetSpeedControllerWrapper(portHandle)->WantsHidden();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getVoltagePercentage
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getVoltagePercentage(JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetSpeedControllerWrapper(portHandle)->GetVoltagePercentage();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getPosition
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getPosition
  (JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetSpeedControllerWrapper(portHandle)->GetPosition();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getVelocity
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getVelocity
  (JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetSpeedControllerWrapper(portHandle)->GetVelocity();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getAcceleration
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getAcceleration
  (JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetSpeedControllerWrapper(portHandle)->GetAcceleration();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getCurrent
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getCurrent
  (JNIEnv *, jclass, jint portHandle)
{
    return SensorActuatorRegistry::Get().GetSpeedControllerWrapper(portHandle)->GetCurrent();
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    updateAllSpeedControllers
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_updateAllSpeedControllers(JNIEnv *, jclass, jdouble aUpdatePeriod)
{
    std::map<int, std::shared_ptr<SpeedControllerWrapper>>& speedControllers = SensorActuatorRegistry::Get().GetSpeedControllerWrapperMap();

    std::map<int, std::shared_ptr<SpeedControllerWrapper>>::iterator iter;

    for (iter = speedControllers.begin(); iter != speedControllers.end(); ++iter)
    {
        iter->second->Update(aUpdatePeriod);
    }
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getMotorSimTypeNative
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getMotorSimTypeNative
  (JNIEnv *, jclass, jint aPortHandle)
{
    const std::shared_ptr<IMotorSimulator>& motorSim =
            SensorActuatorRegistry::Get().GetSpeedControllerWrapper(aPortHandle)->GetMotorSimulator();
    std::string type = motorSim->GetSimulatorType();

    if(type == "Null")
    {
        return 0;
    }
    else if(type == "Simple")
    {
        return 1;
    }
    else if(type == "Static Load")
    {
        return 2;
    }
    else if(type == "Rotational Load")
    {
        return 3;
    }
    else if(type == "Gravity Load")
    {
        return 4;
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::CRITICAL, "Unknown motor sim type " << type);
    }

    return -1;
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getPortList
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getPortList(JNIEnv * env, jclass)
{
    const std::map<int, std::shared_ptr<SpeedControllerWrapper>>& speedControllers = SensorActuatorRegistry::Get().GetSpeedControllerWrapperMap();

    jintArray output = env->NewIntArray(speedControllers.size());

    jint values[30];

    std::map<int, std::shared_ptr<SpeedControllerWrapper>>::const_iterator iter = speedControllers.begin();

    int ctr = 0;
    for (; iter != speedControllers.end(); ++iter)
    {
        values[ctr++] = iter->first;
    }

    env->SetIntArrayRegion(output, 0, speedControllers.size(), values);

    return output;
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getMotorConfig
 * Signature: (I)Lcom/snobot/simulator/DcMotorModelConfig;
 */
JNIEXPORT jobject JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getMotorConfig
  (JNIEnv * env, jclass, jint aPortHandle)
{
    const std::shared_ptr<IMotorSimulator>& motorSim =
            SensorActuatorRegistry::Get().GetSpeedControllerWrapper(aPortHandle)->GetMotorSimulator();
    std::string type = motorSim->GetSimulatorType();

    jobject output = NULL;

    if(type == "Static Load")
    {
        const std::shared_ptr<StaticLoadDcMotorSim>& castMotorSim = std::dynamic_pointer_cast<StaticLoadDcMotorSim>(motorSim);
        output = ConversionUtils::ConvertDcMotorModelConfig(env, castMotorSim->GetMotorModel().GetModelConfig());
    }
    else if(type == "Null" || type == "Simple")
    {
        SNOBOT_LOG(SnobotLogging::WARN, "The type " << type << " does not have a DC Motor config...");
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::CRITICAL, "Unknown motor sim type " << type);
    }

    return output;
}


/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getMotorSimSimpleModelConfig
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getMotorSimSimpleModelConfig
  (JNIEnv *, jclass, jint aPortHandle)
{
    const std::shared_ptr<IMotorSimulator>& motorSim =
            SensorActuatorRegistry::Get().GetSpeedControllerWrapper(aPortHandle)->GetMotorSimulator();
    const std::shared_ptr<SimpleMotorSimulator>& castMotorSim = std::dynamic_pointer_cast<SimpleMotorSimulator>(motorSim);
    if(castMotorSim)
    {
        return castMotorSim->GetMaxSpeed();
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::DEBUG, "Could not cast motor sim to desired type ");
    }

    return 0;
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getMotorSimStaticModelConfig
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getMotorSimStaticModelConfig
  (JNIEnv *, jclass, jint aPortHandle)
{
    const std::shared_ptr<IMotorSimulator>& motorSim =
            SensorActuatorRegistry::Get().GetSpeedControllerWrapper(aPortHandle)->GetMotorSimulator();
    const std::shared_ptr<StaticLoadDcMotorSim>& castMotorSim = std::dynamic_pointer_cast<StaticLoadDcMotorSim>(motorSim);
    if(castMotorSim)
    {
        return castMotorSim->GetLoad();
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::DEBUG, "Could not cast motor sim to desired type ");
    }

    return 0;
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getMotorSimGravitationalModelConfig
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getMotorSimGravitationalModelConfig
  (JNIEnv *, jclass, jint aPortHandle)
{
    const std::shared_ptr<IMotorSimulator>& motorSim =
            SensorActuatorRegistry::Get().GetSpeedControllerWrapper(aPortHandle)->GetMotorSimulator();
    const std::shared_ptr<GravityLoadDcMotorSim>& castMotorSim = std::dynamic_pointer_cast<GravityLoadDcMotorSim>(motorSim);
    if(castMotorSim)
    {
        return castMotorSim->GetLoad();
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::DEBUG, "Could not cast motor sim to desired type ");
    }

    return 0;
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getMotorSimRotationalModelConfig_armCenterOfMass
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getMotorSimRotationalModelConfig_1armCenterOfMass
  (JNIEnv *, jclass, jint aPortHandle)
{
    const std::shared_ptr<IMotorSimulator>& motorSim =
            SensorActuatorRegistry::Get().GetSpeedControllerWrapper(aPortHandle)->GetMotorSimulator();
    const std::shared_ptr<RotationalLoadDcMotorSim>& castMotorSim = std::dynamic_pointer_cast<RotationalLoadDcMotorSim>(motorSim);
    if(castMotorSim)
    {
        return castMotorSim->GetArmCenterOfMass();
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::DEBUG, "Could not cast motor sim to desired type ");
    }

    return 0;
}

/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    getMotorSimRotationalModelConfig_armMass
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_getMotorSimRotationalModelConfig_1armMass
  (JNIEnv *, jclass, jint aPortHandle)
{
    const std::shared_ptr<IMotorSimulator>& motorSim =
            SensorActuatorRegistry::Get().GetSpeedControllerWrapper(aPortHandle)->GetMotorSimulator();
    const std::shared_ptr<RotationalLoadDcMotorSim>& castMotorSim = std::dynamic_pointer_cast<RotationalLoadDcMotorSim>(motorSim);
    if(castMotorSim)
    {
        return castMotorSim->GetArmMass();
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::DEBUG, "Could not cast motor sim to desired type ");
    }

    return 0;
}


/*
 * Class:     com_snobot_simulator_jni_module_wrapper_SpeedControllerWrapperJni
 * Method:    reset
 * Signature: (IDDD)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_module_1wrapper_SpeedControllerWrapperJni_reset
  (JNIEnv *, jclass, jint portHandle, jdouble aPosition, jdouble aVelocity, jdouble aCurrent)
{
    SNOBOT_LOG(SnobotLogging::WARN, "Resetting... " << aPosition << ", " << aVelocity << ", " << aCurrent)
    return SensorActuatorRegistry::Get().GetSpeedControllerWrapper(portHandle)->Reset(aPosition, aVelocity, aCurrent);
}


}  // extern "C"

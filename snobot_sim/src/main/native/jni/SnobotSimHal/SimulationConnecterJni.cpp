
#include <jni.h>

#include <cassert>
#include <memory>
#include <vector>

#include "ConversionUtils.h"
#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IGyroWrapper.h"
#include "SnobotSim/MotorSim/DcMotorModelConfig.h"
#include "SnobotSim/MotorSim/GravityLoadDcMotorSim.h"
#include "SnobotSim/MotorSim/RotationalLoadDcMotorSim.h"
#include "SnobotSim/MotorSim/SimpleMotorSimulator.h"
#include "SnobotSim/MotorSim/StaticLoadDcMotorSim.h"
#include "SnobotSim/RobotStateSingleton.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "SnobotSim/SimulatorComponents/ISimulatorUpdater.h"
#include "SnobotSim/SimulatorComponents/TankDriveSimulator.h"

using namespace GetSensorActuatorHelper;

extern "C" {
/*
 * Class:     com_snobot_simulator_jni_SimulationConnectorJni
 * Method:    updateLoop
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_jni_SimulationConnectorJni_updateLoop
  (JNIEnv*, jclass)
{
    RobotStateSingleton::Get().UpdateLoop();
}

/*
 * Class:     com_snobot_simulator_jni_SimulationConnectorJni_setSpeedControllerModel
 * Method:    1Simple
 * Signature: (ID)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_snobot_simulator_jni_SimulationConnectorJni_setSpeedControllerModel_1Simple
  (JNIEnv*, jclass, jint aHandle, jdouble aMaxSpeed)
{
    std::shared_ptr<ISpeedControllerWrapper> speedController = GetISpeedControllerWrapper(aHandle);
    if (speedController)
    {
        speedController->SetMotorSimulator(std::shared_ptr<IMotorSimulator>(new SimpleMotorSimulator(aMaxSpeed)));
        return true;
    }

    return false;
}

/*
 * Class:     com_snobot_simulator_jni_SimulationConnectorJni_setSpeedControllerModel
 * Method:    1Static
 * Signature: (ILjava/lang/Object;DD)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_snobot_simulator_jni_SimulationConnectorJni_setSpeedControllerModel_1Static
  (JNIEnv* env, jclass, jint aSpeedControllerHandle, jobject aConfig,
   jdouble aLoad, jdouble aConversionFactor)
{
    DcMotorModel motorModel(ConversionUtils::ConvertDcMotorModelConfig(env, aConfig));

    std::shared_ptr<ISpeedControllerWrapper> speedController = GetISpeedControllerWrapper(aSpeedControllerHandle);
    if (speedController)
    {
        speedController->SetMotorSimulator(std::shared_ptr<IMotorSimulator>(new StaticLoadDcMotorSim(motorModel, aLoad, aConversionFactor)));
        return true;
    }

    return false;
}

/*
 * Class:     com_snobot_simulator_jni_SimulationConnectorJni_setSpeedControllerModel
 * Method:    1Gravitational
 * Signature: (ILjava/lang/Object;D)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_snobot_simulator_jni_SimulationConnectorJni_setSpeedControllerModel_1Gravitational
  (JNIEnv* env, jclass, jint aSpeedControllerHandle, jobject aConfig,
   jdouble aLoad)
{
    DcMotorModel motorModel(ConversionUtils::ConvertDcMotorModelConfig(env, aConfig));

    std::shared_ptr<ISpeedControllerWrapper> speedController = GetISpeedControllerWrapper(aSpeedControllerHandle);
    if (speedController)
    {
        speedController->SetMotorSimulator(std::shared_ptr<IMotorSimulator>(new GravityLoadDcMotorSim(motorModel, aLoad)));
        return true;
    }

    return false;
}

/*
 * Class:     com_snobot_simulator_jni_SimulationConnectorJni_setSpeedControllerModel
 * Method:    1Rotational
 * Signature: (ILjava/lang/Object;DDDD)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_snobot_simulator_jni_SimulationConnectorJni_setSpeedControllerModel_1Rotational
  (JNIEnv* env, jclass, jint aSpeedControllerHandle, jobject aConfig,
   jdouble aArmCenterOfMass, jdouble aArmMass, jdouble aConstantAssistTorque,
   jdouble aOverCenterAssistTorque)
{
    DcMotorModel motorModel(ConversionUtils::ConvertDcMotorModelConfig(env, aConfig));

    std::shared_ptr<ISpeedControllerWrapper> speedController = GetISpeedControllerWrapper(aSpeedControllerHandle);
    if (speedController)
    {
        speedController->SetMotorSimulator(std::shared_ptr<IMotorSimulator>(new RotationalLoadDcMotorSim(motorModel, speedController,
                aArmCenterOfMass, aArmMass, aConstantAssistTorque, aOverCenterAssistTorque)));
        return true;
    }

    return false;
}

/*
 * Class:     com_snobot_simulator_jni_SimulationConnectorJni
 * Method:    connectTankDriveSimulator
 * Signature: (IIID)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_snobot_simulator_jni_SimulationConnectorJni_connectTankDriveSimulator
  (JNIEnv*, jclass, jint aLeftEncHandle, jint aRightEncHandle, jint aGyroHandle,
   jdouble aTurnKp)
{
    std::shared_ptr<ISpeedControllerWrapper> leftEncoder = GetISpeedControllerWrapper(aLeftEncHandle);
    std::shared_ptr<ISpeedControllerWrapper> rightEncoder = GetISpeedControllerWrapper(aRightEncHandle);
    std::shared_ptr<IGyroWrapper> gyro = GetIGyroWrapper(aGyroHandle);

    std::shared_ptr<TankDriveSimulator> simulator(new TankDriveSimulator(leftEncoder, rightEncoder, gyro, -aTurnKp));

    SensorActuatorRegistry::Get().AddSimulatorComponent(simulator);

    return leftEncoder && rightEncoder && gyro;
}

} // extern "C"

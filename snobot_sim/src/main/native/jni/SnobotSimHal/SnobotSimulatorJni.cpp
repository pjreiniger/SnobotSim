
#include <jni.h>

#include <cassert>

#include "SnobotSim/Config/SimulatorConfigReaderV1.h"
#include "SnobotSim/Config/SimulatorConfigWriterV1.h"
#include "SnobotSim/HalCallbacks/CallbackSetup.h"
#include "SnobotSim/Logging/SnobotCoutLogger.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "SnobotSim/RobotStateSingleton.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "SnobotSim/SnobotSimHalVersion.h"
#include "com_snobot_simulator_jni_SnobotSimulatorJni.h"
#include "hal/HAL.h"
#include "wpi/jni_util.h"

using namespace wpi::java;

static SnobotLogging::ISnobotLogger* sSnobotLogger = NULL;

extern "C" {

/*
 * Class:     com_snobot_simulator_jni_SnobotSimulatorJni
 * Method:    initializeSimulator
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_jni_SnobotSimulatorJni_initializeSimulator
  (JNIEnv*, jclass)
{
    if (!HAL_Initialize(0, 0))
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Couldn't initialize!!!");
    }

    SnobotSim::InitializeSnobotCallbacks();
}

/*
 * Class:     com_snobot_simulator_jni_SnobotSimulatorJni
 * Method:    reset
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_jni_SnobotSimulatorJni_reset
  (JNIEnv*, jclass)
{
    SensorActuatorRegistry::Get().Reset();
    SnobotSim::ResetSnobotCallbacks();

    FactoryContainer::Get().GetI2CWrapperFactory()->ResetDefaults();
    FactoryContainer::Get().GetSpiWrapperFactory()->ResetDefaults();
}

/*
 * Class:     com_snobot_simulator_jni_SnobotSimulatorJni
 * Method:    shutdown
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_jni_SnobotSimulatorJni_shutdown
  (JNIEnv*, jclass)
{
    RobotStateSingleton::Get().Reset();
    SensorActuatorRegistry::Get().Reset();

    if (sSnobotLogger)
    {
        delete sSnobotLogger;
        sSnobotLogger = NULL;
    }
    SnobotLogging::SetLogger(NULL);
}

/*
 * Class:     com_snobot_simulator_jni_SnobotSimulatorJni
 * Method:    getVersion
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL
Java_com_snobot_simulator_jni_SnobotSimulatorJni_getVersion
  (JNIEnv* env, jclass)
{
    jstring output = MakeJString(env, SnobotSim::GetSnobotSimVersion());

    return output;
}

/*
 * Class:     com_snobot_simulator_jni_SnobotSimulatorJni
 * Method:    initializeLogging
 * Signature: (I)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_jni_SnobotSimulatorJni_initializeLogging
  (JNIEnv*, jclass, jint aLogLevel)
{
    if (!sSnobotLogger)
    {
        sSnobotLogger = new SnobotLogging::SnobotCoutLogger();
    }

    SnobotLogging::LogLevel logLevel = (SnobotLogging::LogLevel)aLogLevel;
    sSnobotLogger->SetLogLevel(logLevel);

    SnobotLogging::SetLogger(sSnobotLogger);
}

/*
 * Class:     com_snobot_simulator_jni_SimulationConnectorJni
 * Method:    createI2CSimulator
 * Signature: (ILjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_snobot_simulator_jni_SimulationConnectorJni_createI2CSimulator
  (JNIEnv* env, jclass, jint aPort, jstring aType)
{
    FactoryContainer::Get().GetI2CWrapperFactory()->CreateWrapper(aPort, env->GetStringUTFChars(aType, NULL));
    return true;
}

/*
 * Class:     com_snobot_simulator_jni_SimulationConnectorJni
 * Method:    createSpiSimulator
 * Signature: (ILjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_snobot_simulator_jni_SimulationConnectorJni_createSpiSimulator
  (JNIEnv* env, jclass, jint aPort, jstring aType)
{
    FactoryContainer::Get().GetSpiWrapperFactory()->CreateWrapper(aPort, env->GetStringUTFChars(aType, NULL));
    return true;
}

/*
 * Class:     com_snobot_simulator_jni_SnobotSimulatorJni
 * Method:    loadConfigFile
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_snobot_simulator_jni_SnobotSimulatorJni_loadConfigFile
  (JNIEnv* env, jclass, jstring aFilename)
{
    if (aFilename == NULL)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Simulator file was null! Won't hook anything up");
        return true;
    }
    std::string filename = env->GetStringUTFChars(aFilename, NULL);
    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Loading config file '" << filename << "'");

    SimulatorConfigReaderV1 configReader;
    return configReader.LoadConfig(filename);
}

/*
 * Class:     com_snobot_simulator_jni_SnobotSimulatorJni
 * Method:    saveConfigFile
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_snobot_simulator_jni_SnobotSimulatorJni_saveConfigFile
  (JNIEnv* env, jclass, jstring aFilename)
{
    if (aFilename == NULL)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Simulator file was null!");
        return false;
    }
    std::string filename = env->GetStringUTFChars(aFilename, NULL);
    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Saving config file '" << filename << "'");

    SimulatorConfigWriterV1 configWriter;
    return configWriter.DumpConfig(filename);
}

/*
 * Class:     com_snobot_simulator_jni_SnobotSimulatorJni
 * Method:    getSimulatorComponentConfigsCount
 * Signature: ()I
 */
JNIEXPORT jint JNICALL
Java_com_snobot_simulator_jni_SnobotSimulatorJni_getSimulatorComponentConfigsCount
  (JNIEnv*, jclass)
{
    return SensorActuatorRegistry::Get().GetSimulatorComponents().size();
}

} // extern "C"

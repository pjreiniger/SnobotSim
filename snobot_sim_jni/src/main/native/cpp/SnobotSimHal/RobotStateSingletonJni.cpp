
#include <jni.h>

#include <cassert>

#include "mockdata/DriverStationData.h"
#include "mockdata/MockHooks.h"
#include "SnobotSim/RobotStateSingleton.h"
#include "com_snobot_simulator_jni_RobotStateSingletonJni.h"

extern "C"
{

/*
 * Class:     com_snobot_simulator_jni_RobotStateSingletonJni
 * Method:    setDisabled
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_RobotStateSingletonJni_setDisabled(JNIEnv *, jclass, jboolean aDisabled)
{
    HALSIM_SetDriverStationDsAttached(!aDisabled);
    HALSIM_SetDriverStationEnabled(!aDisabled);
}

/*
 * Class:     com_snobot_simulator_jni_RobotStateSingletonJni
 * Method:    setAutonomous
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_RobotStateSingletonJni_setAutonomous(JNIEnv *, jclass, jboolean aBool)
{
    HALSIM_SetDriverStationAutonomous(aBool);
}

/*
 * Class:     com_snobot_simulator_jni_RobotStateSingletonJni
 * Method:    setTest
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_RobotStateSingletonJni_setTest(JNIEnv *, jclass, jboolean aBool)
{
    HALSIM_SetDriverStationTest(aBool);
}

/*
 * Class:     com_snobot_simulator_jni_RobotStateSingletonJni
 * Method:    getMatchTime
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_RobotStateSingletonJni_getMatchTime(JNIEnv *, jclass)
{
    return RobotStateSingleton::Get().GetMatchTime();
}

/*
 * Class:     com_snobot_simulator_jni_RobotStateSingletonJni
 * Method:    waitForProgramToStart
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_RobotStateSingletonJni_waitForProgramToStart
  (JNIEnv *, jclass)
{
    HALSIM_WaitForProgramStart();
}

/*
 * Class:     com_snobot_simulator_jni_RobotStateSingletonJni
 * Method:    waitForNextUpdateLoop
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_snobot_simulator_jni_RobotStateSingletonJni_waitForNextUpdateLoop
  (JNIEnv *, jclass, jdouble aUpdatePeriod)
{
    RobotStateSingleton::Get().WaitForNextControlLoop(aUpdatePeriod);
}

/*
 * Class:     com_snobot_simulator_jni_RobotStateSingletonJni
 * Method:    getCycleTime
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_com_snobot_simulator_jni_RobotStateSingletonJni_getCycleTime
  (JNIEnv *, jclass)
{
    return .02;
}

}  // extern "C"

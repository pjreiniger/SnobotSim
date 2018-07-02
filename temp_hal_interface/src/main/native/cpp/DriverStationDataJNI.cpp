
#include <jni.h>

#include "MockData/DriverStationData.h"
#include "edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI.h"

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setEnabled
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setEnabled
  (JNIEnv*, jclass, jboolean value)
{
    HALSIM_SetDriverStationEnabled(value);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setDsAttached
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setDsAttached
  (JNIEnv*, jclass, jboolean value)
{
    HALSIM_SetDriverStationDsAttached(value);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setAutonomous
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setAutonomous
  (JNIEnv*, jclass, jboolean value)
{
    HALSIM_SetDriverStationAutonomous(value);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    notifyNewData
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_notifyNewData
  (JNIEnv*, jclass)
{
    HALSIM_NotifyDriverStationNewData();
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setJoystickAxes
 * Signature: (B[F)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setJoystickAxes
  (JNIEnv* env, jclass, jbyte aHandle, jfloatArray aAxes)
{
    HAL_JoystickAxes newAxes;
    float* axes = env->GetFloatArrayElements(aAxes, NULL);
    newAxes.count = env->GetArrayLength(aAxes);
    for (int i = 0; i < newAxes.count; ++i)
    {
        newAxes.axes[i] = axes[i];
    }
    env->ReleaseFloatArrayElements(aAxes, axes, 0);
    HALSIM_SetJoystickAxes(aHandle, &newAxes);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setJoystickPOVs
 * Signature: (B[S)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setJoystickPOVs
  (JNIEnv* env, jclass, jbyte joystickNum, jshortArray aPovs)
{
    HAL_JoystickPOVs newPov;
    int16_t* povs = env->GetShortArrayElements(aPovs, NULL);
    newPov.count = env->GetArrayLength(aPovs);
    for (int i = 0; i < newPov.count; ++i)
    {
        newPov.povs[i] = povs[i];
    }
    env->ReleaseShortArrayElements(aPovs, povs, 0);

    HALSIM_SetJoystickPOVs(joystickNum, &newPov);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setJoystickButtons
 * Signature: (BII)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setJoystickButtons
  (JNIEnv* env, jclass, jbyte joystickNum, jint buttons, jint count)
{
    if (count > 32)
    {
        count = 32;
    }
    HAL_JoystickButtons joystickButtons;
    joystickButtons.count = count;
    joystickButtons.buttons = buttons;
    HALSIM_SetJoystickButtons(joystickNum, &joystickButtons);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setMatchInfo
 * Signature: (Ljava/lang/Object;)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setMatchInfo
  (JNIEnv* env, jclass, jobject matchInfo)
{
    jstring eventName = (jstring)env->GetObjectField(matchInfo, env->GetFieldID(env->GetObjectClass(matchInfo), "eventName", "Ljava/lang/String;"));
    jstring gameSpecificMessage = (jstring)env->GetObjectField(matchInfo, env->GetFieldID(env->GetObjectClass(matchInfo), "gameSpecificMessage", "Ljava/lang/String;"));
    int matchNumber = env->GetIntField(matchInfo, env->GetFieldID(env->GetObjectClass(matchInfo), "matchNumber", "I"));
    int replayNumber = env->GetIntField(matchInfo, env->GetFieldID(env->GetObjectClass(matchInfo), "replayNumber", "I"));
    int matchType = env->GetIntField(matchInfo, env->GetFieldID(env->GetObjectClass(matchInfo), "matchType", "I"));

    HAL_MatchInfo halMatchInfo;
    halMatchInfo.eventName = const_cast<char*>(env->GetStringUTFChars(eventName, NULL));
    halMatchInfo.gameSpecificMessage = const_cast<char*>(env->GetStringUTFChars(gameSpecificMessage, NULL));
    halMatchInfo.matchType = (HAL_MatchType)matchType;
    halMatchInfo.matchNumber = matchNumber;
    halMatchInfo.replayNumber = replayNumber;

    HALSIM_SetMatchInfo(&halMatchInfo);
}

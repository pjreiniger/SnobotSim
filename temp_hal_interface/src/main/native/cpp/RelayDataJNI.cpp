
#include "MockData/RelayData.h"
#include "SnobotSimUtilities/CallbackStore.h"
#include "edu_wpi_first_hal_sim_mockdata_RelayDataJNI.h"

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_RelayDataJNI
 * Method:    resetData
 * Signature: (I)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_RelayDataJNI_resetData
  (JNIEnv*, jclass, jint index)
{
    HALSIM_ResetRelayData(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_RelayDataJNI
 * Method:    registerInitializedForwardCallback
 * Signature: (ILjava/lang/Object;Z)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_RelayDataJNI_registerInitializedForwardCallback
  (JNIEnv* env, jclass, jint index, jobject callback, jboolean initialNotify)
{
    SnobotSim::AllocateCallback(env, index, callback, initialNotify,
            &HALSIM_RegisterRelayInitializedForwardCallback);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_RelayDataJNI
 * Method:    getForward
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL
Java_edu_wpi_first_hal_sim_mockdata_RelayDataJNI_getForward
  (JNIEnv*, jclass, jint index)
{
    return HALSIM_GetRelayForward(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_RelayDataJNI
 * Method:    setForward
 * Signature: (IZ)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_RelayDataJNI_setForward
  (JNIEnv*, jclass, jint index, jboolean value)
{
    HALSIM_SetRelayForward(index, value);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_RelayDataJNI
 * Method:    getReverse
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL
Java_edu_wpi_first_hal_sim_mockdata_RelayDataJNI_getReverse
  (JNIEnv*, jclass, jint index)
{
    return HALSIM_GetRelayReverse(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_RelayDataJNI
 * Method:    setReverse
 * Signature: (IZ)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_RelayDataJNI_setReverse
  (JNIEnv*, jclass, jint index, jboolean value)
{
    HALSIM_SetRelayReverse(index, value);
}

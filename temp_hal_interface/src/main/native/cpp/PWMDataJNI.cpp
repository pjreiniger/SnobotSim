
#include "MockData/PWMData.h"
#include "SnobotSimUtilities/CallbackStore.h"
#include "edu_wpi_first_hal_sim_mockdata_PWMDataJNI.h"

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_PWMDataJNI
 * Method:    resetData
 * Signature: (I)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_PWMDataJNI_resetData
  (JNIEnv*, jclass, jint index)
{
    HALSIM_ResetPWMData(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_PWMDataJNI
 * Method:    registerInitializedCallback
 * Signature: (ILjava/lang/Object;Z)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_PWMDataJNI_registerInitializedCallback
  (JNIEnv* env, jclass, jint index, jobject callback, jboolean initialNotify)
{
    SnobotSim::AllocateCallback(env, index, callback, initialNotify,
            &HALSIM_RegisterPWMInitializedCallback);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_PWMDataJNI
 * Method:    registerSpeedCallback
 * Signature: (ILjava/lang/Object;Z)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_PWMDataJNI_registerSpeedCallback
  (JNIEnv* env, jclass, jint index, jobject callback, jboolean initialNotify)
{
    SnobotSim::AllocateCallback(env, index, callback, initialNotify,
            &HALSIM_RegisterPWMSpeedCallback);
}

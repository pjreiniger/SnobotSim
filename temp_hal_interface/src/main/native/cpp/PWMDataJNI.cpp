
#include "MockData/PWMData.h"
#include "edu_wpi_first_hal_sim_mockdata_PWMDataJNI.h"
#include "SnobotSimUtilities/CallbackStore.h"

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_PWMDataJNI
 * Method:    resetData
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_PWMDataJNI_resetData
(JNIEnv*, jclass, jint index)
{
	HALSIM_ResetPWMData(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_PWMDataJNI
 * Method:    registerInitializedCallback
 * Signature: (ILedu/wpi/first/wpilibj/sim/NotifyCallback;Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_PWMDataJNI_registerInitializedCallback
(JNIEnv* env, jclass, jint index, jobject callback, jboolean initialNotify)
{
	SnobotSim::AllocateCallback(env, index, callback, initialNotify,
	                               &HALSIM_RegisterPWMInitializedCallback);
}


/*
 * Class:     edu_wpi_first_hal_sim_mockdata_PWMDataJNI
 * Method:    registerSpeedCallback
 * Signature: (ILedu/wpi/first/wpilibj/sim/NotifyCallback;Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_PWMDataJNI_registerSpeedCallback
(JNIEnv* env, jclass, jint index, jobject callback, jboolean initialNotify)
{
	SnobotSim::AllocateCallback(env, index, callback, initialNotify,
	                               &HALSIM_RegisterPWMSpeedCallback);
}

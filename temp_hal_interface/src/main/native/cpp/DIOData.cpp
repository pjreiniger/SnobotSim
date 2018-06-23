
#include "edu_wpi_first_hal_sim_mockdata_DIODataJNI.h"
#include "MockData/DIOData.h"
#include "SnobotSimUtilities/CallbackStore.h"

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DIODataJNI
 * Method:    resetData
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DIODataJNI_resetData
(JNIEnv*, jclass, jint index)
{
	  HALSIM_ResetDIOData(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DIODataJNI
 * Method:    registerInitializedCallback
 * Signature: (ILedu/wpi/first/wpilibj/sim/NotifyCallback;Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DIODataJNI_registerInitializedCallback
(JNIEnv* env, jclass, jint index, jobject callback, jboolean initialNotify)
{
	SnobotSim::AllocateCallback(env, index, callback, initialNotify,
	                               &HALSIM_RegisterDIOInitializedCallback);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DIODataJNI
 * Method:    getValue
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_edu_wpi_first_hal_sim_mockdata_DIODataJNI_getValue
  (JNIEnv *, jclass, jint index)
{
	  return HALSIM_GetDIOValue(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DIODataJNI
 * Method:    setValue
 * Signature: (IZ)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DIODataJNI_setValue
  (JNIEnv *, jclass, jint index, jboolean value)
{
	  HALSIM_SetDIOValue(index, value);
}

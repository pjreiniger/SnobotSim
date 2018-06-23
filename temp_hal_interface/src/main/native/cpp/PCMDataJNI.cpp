
#include "MockData/PCMData.h"
#include "edu_wpi_first_hal_sim_mockdata_PCMDataJNI.h"
#include "SnobotSimUtilities/CallbackStore.h"

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_PCMDataJNI
 * Method:    registerAllSolenoidCallbacks
 * Signature: (IILedu/wpi/first/wpilibj/sim/NotifyCallback;Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_PCMDataJNI_registerAllSolenoidCallbacks
(JNIEnv* env, jclass, jint index, jint channel, jobject callback,
 jboolean initialNotify)
{
	SnobotSim::AllocateChannelCallback(
		env, index, channel, callback, initialNotify,
		[](int32_t index, int32_t channel, HAL_NotifyCallback cb, void* param,
		   HAL_Bool in) {
		  HALSIM_RegisterPCMAllSolenoidCallbacks(index, channel, cb, param, in);
		  return 0;
		});
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_PCMDataJNI
 * Method:    resetData
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_PCMDataJNI_resetData
(JNIEnv*, jclass, jint index)
{
HALSIM_ResetPCMData(index);
}


#include "MockData/I2CData.h"
#include "edu_wpi_first_hal_sim_mockdata_I2CDataJNI.h"
#include "SnobotSimUtilities/CallbackStore.h"

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_I2CDataJNI
 * Method:    resetData
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_I2CDataJNI_resetData
(JNIEnv*, jclass, jint index)
{
HALSIM_ResetI2CData(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_I2CDataJNI
 * Method:    registerInitializedCallback
 * Signature: (ILedu/wpi/first/wpilibj/sim/NotifyCallback;Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_I2CDataJNI_registerInitializedCallback
(JNIEnv* env, jclass, jint index, jobject callback, jboolean initialNotify)
{
	SnobotSim::AllocateCallback(env, index, callback, initialNotify,
                               &HALSIM_RegisterI2CInitializedCallback);
}



#include "SnobotSimUtilities/CallbackStore.h"
#include "MockData/AnalogGyroData.h"
#include "edu_wpi_first_hal_sim_mockdata_AnalogGyroDataJNI.h"


/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogGyroDataJNI
 * Method:    getAngle
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_edu_wpi_first_hal_sim_mockdata_AnalogGyroDataJNI_getAngle
(JNIEnv*, jclass, jint index)
{
return HALSIM_GetAnalogGyroAngle(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogGyroDataJNI
 * Method:    setAngle
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_AnalogGyroDataJNI_setAngle
(JNIEnv*, jclass, jint index, jdouble value)
{
HALSIM_SetAnalogGyroAngle(index, value);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogGyroDataJNI
 * Method:    resetData
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_AnalogGyroDataJNI_resetData
(JNIEnv*, jclass, jint index)
{
HALSIM_ResetAnalogGyroData(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogGyroDataJNI
 * Method:    registerInitializedCallback
 * Signature: (ILedu/wpi/first/wpilibj/sim/NotifyCallback;Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_AnalogGyroDataJNI_registerInitializedCallback
(JNIEnv* env, jclass, jint index, jobject callback, jboolean initialNotify)
{
	SnobotSim::AllocateCallback(env, index, callback, initialNotify,
                             &HALSIM_RegisterAnalogGyroInitializedCallback);
}

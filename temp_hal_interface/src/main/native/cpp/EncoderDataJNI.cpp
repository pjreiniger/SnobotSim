
#include "MockData/EncoderData.h"
#include "edu_wpi_first_hal_sim_mockdata_EncoderDataJNI.h"
#include "SnobotSimUtilities/CallbackStore.h"

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_EncoderDataJNI
 * Method:    resetData
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_resetData
  (JNIEnv *, jclass, jint index)
{
  HALSIM_ResetEncoderData(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_EncoderDataJNI
 * Method:    registerInitializedCallback
 * Signature: (ILedu/wpi/first/wpilibj/sim/NotifyCallback;Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_registerInitializedCallback
  (JNIEnv * env, jclass, jint index, jobject callback, jboolean initialNotify)
{
	SnobotSim::AllocateCallback(env, index, callback, initialNotify,
	                               &HALSIM_RegisterEncoderInitializedCallback);
}
/*
 * Class:     edu_wpi_first_hal_sim_mockdata_EncoderDataJNI
 * Method:    registerResetCallback
 * Signature: (ILedu/wpi/first/wpilibj/sim/NotifyCallback;Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_registerResetCallback
(JNIEnv * env, jclass, jint index, jobject callback, jboolean initialNotify)
{
	SnobotSim::AllocateCallback(env, index, callback, initialNotify,
	                               &HALSIM_RegisterEncoderResetCallback);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_EncoderDataJNI
 * Method:    getCount
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_getCount
(JNIEnv*, jclass, jint index)
{
return HALSIM_GetEncoderCount(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_EncoderDataJNI
 * Method:    setCount
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_setCount
(JNIEnv*, jclass, jint index, jint value)
{
HALSIM_SetEncoderCount(index, value);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_EncoderDataJNI
 * Method:    getPeriod
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_getPeriod
(JNIEnv*, jclass, jint index)
{
return HALSIM_GetEncoderPeriod(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_EncoderDataJNI
 * Method:    setPeriod
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_setPeriod
(JNIEnv*, jclass, jint index, jdouble value)
{
HALSIM_SetEncoderPeriod(index, value);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_EncoderDataJNI
 * Method:    setDistancePerPulse
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_setDistancePerPulse
  (JNIEnv *, jclass, jint index, jdouble value)
{
//	HALSIM_SetEncoderDistancePerPulse(index, value);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_EncoderDataJNI
 * Method:    getDistancePerPulse
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_getDistancePerPulse
  (JNIEnv *, jclass, jint index)
{
//	return HALSIM_GetEncoderDistancePerPulse(index);
	return 1;
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_EncoderDataJNI
 * Method:    getReset
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL
Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_getReset
  (JNIEnv*, jclass, jint index)
{
  return HALSIM_GetEncoderReset(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_EncoderDataJNI
 * Method:    setReset
 * Signature: (IZ)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_EncoderDataJNI_setReset
  (JNIEnv*, jclass, jint index, jboolean value)
{
  HALSIM_SetEncoderReset(index, value);
}


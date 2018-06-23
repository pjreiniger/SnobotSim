
#include "edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI.h"
#include "MockData/AnalogOutData.h"
#include "SnobotSimUtilities/CallbackStore.h"
#include <iostream>

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI
 * Method:    resetData
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI_resetData
(JNIEnv*, jclass, jint index)
{
	  HALSIM_ResetAnalogOutData(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI
 * Method:    registerInitializedCallback
 * Signature: (ILedu/wpi/first/wpilibj/sim/NotifyCallback;Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI_registerInitializedCallback
(JNIEnv* env, jclass, jint index, jobject callback, jboolean initialNotify)
{
	SnobotSim::AllocateCallback(env, index, callback, initialNotify,
	                               &HALSIM_RegisterAnalogOutInitializedCallback);
}


/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI
 * Method:    getVoltage
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL
Java_edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI_getVoltage
  (JNIEnv*, jclass, jint index)
{
  return HALSIM_GetAnalogOutVoltage(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI
 * Method:    setVoltage
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI_setVoltage
  (JNIEnv*, jclass, jint index, jdouble value)
{
  HALSIM_SetAnalogOutVoltage(index, value);
}

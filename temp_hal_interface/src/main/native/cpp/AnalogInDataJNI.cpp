
#include "MockData/AnalogInData.h"
#include "SnobotSimUtilities/CallbackStore.h"
#include "edu_wpi_first_hal_sim_mockdata_AnalogInDataJNI.h"

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogInDataJNI
 * Method:    resetData
 * Signature: (I)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_AnalogInDataJNI_resetData
  (JNIEnv*, jclass, jint index)
{
    HALSIM_ResetAnalogInData(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogInDataJNI
 * Method:    registerInitializedCallback
 * Signature: (ILjava/lang/Object;Z)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_AnalogInDataJNI_registerInitializedCallback
  (JNIEnv* env, jclass, jint index, jobject callback, jboolean initialNotify)
{
    SnobotSim::AllocateCallback(env, index, callback, initialNotify,
            &HALSIM_RegisterAnalogInInitializedCallback);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogInDataJNI
 * Method:    getVoltage
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL
Java_edu_wpi_first_hal_sim_mockdata_AnalogInDataJNI_getVoltage
  (JNIEnv*, jclass, jint index)
{
    return HALSIM_GetAnalogInVoltage(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogInDataJNI
 * Method:    setVoltage
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_AnalogInDataJNI_setVoltage
  (JNIEnv*, jclass, jint index, jdouble value)
{
    HALSIM_SetAnalogInVoltage(index, value);
}

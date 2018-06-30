
#include "MockData/I2CData.h"
#include "SnobotSimUtilities/CallbackStore.h"
#include "edu_wpi_first_hal_sim_mockdata_I2CDataJNI.h"

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_I2CDataJNI
 * Method:    resetData
 * Signature: (I)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_I2CDataJNI_resetData
  (JNIEnv*, jclass, jint index)
{
    HALSIM_ResetI2CData(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_I2CDataJNI
 * Method:    registerInitializedCallback
 * Signature: (ILjava/lang/Object;Z)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_I2CDataJNI_registerInitializedCallback
  (JNIEnv* env, jclass, jint index, jobject callback, jboolean initialNotify)
{
    SnobotSim::AllocateCallback(env, index, callback, initialNotify,
            &HALSIM_RegisterI2CInitializedCallback);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_I2CDataJNI
 * Method:    registerReadCallback
 * Signature: (ILjava/lang/Object;)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_I2CDataJNI_registerReadCallback
  (JNIEnv* env, jclass, jint index, jobject callback)
{
    SnobotSim::AllocateBufferCallback(env, index, callback,
            &HALSIM_RegisterI2CReadCallback);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_I2CDataJNI
 * Method:    registerWriteCallback
 * Signature: (ILjava/lang/Object;)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_I2CDataJNI_registerWriteCallback
  (JNIEnv* env, jclass, jint index, jobject callback)
{
    SnobotSim::AllocateConstBufferCallback(env, index, callback,
            &HALSIM_RegisterI2CWriteCallback);
}

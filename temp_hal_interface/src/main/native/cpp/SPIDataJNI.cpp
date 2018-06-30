
#include "MockData/SPIData.h"
#include "SnobotSimUtilities/CallbackStore.h"
#include "edu_wpi_first_hal_sim_mockdata_SPIDataJNI.h"

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_SPIDataJNI
 * Method:    resetData
 * Signature: (I)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_SPIDataJNI_resetData
  (JNIEnv*, jclass, jint index)
{
    HALSIM_ResetSPIData(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_SPIDataJNI
 * Method:    registerInitializedCallback
 * Signature: (ILjava/lang/Object;Z)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_SPIDataJNI_registerInitializedCallback
  (JNIEnv* env, jclass, jint index, jobject callback, jboolean initialNotify)
{
    SnobotSim::AllocateCallback(env, index, callback, initialNotify,
            &HALSIM_RegisterSPIInitializedCallback);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_SPIDataJNI
 * Method:    registerReadCallback
 * Signature: (ILjava/lang/Object;)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_SPIDataJNI_registerReadCallback
  (JNIEnv* env, jclass, jint index, jobject callback)
{
    SnobotSim::AllocateBufferCallback(env, index, callback,
            &HALSIM_RegisterSPIReadCallback);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_SPIDataJNI
 * Method:    registerWriteCallback
 * Signature: (ILjava/lang/Object;)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_SPIDataJNI_registerWriteCallback
  (JNIEnv* env, jclass, jint index, jobject callback)
{
    SnobotSim::AllocateConstBufferCallback(env, index, callback,
            &HALSIM_RegisterSPIWriteCallback);
}

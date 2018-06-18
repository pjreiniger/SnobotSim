#include <jni.h>

#include "ADXRS450_SpiGyroWrapperData.h"
#include "edu_wpi_first_hal_sim_mockdata_ADXRS450_GyroDataJNI.h"

extern "C" {

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXRS450_1GyroDataJNI
 * Method:    createGyro
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXRS450_1GyroDataJNI_createGyro
  (JNIEnv*, jclass, jint port)
{
    hal::ADXRS450_SpiGyroWrapper* output = new hal::ADXRS450_SpiGyroWrapper(port);
    return (jlong)(output);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXRS450_1GyroDataJNI
 * Method:    deleteGyro
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXRS450_1GyroDataJNI_deleteGyro
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    hal::ADXRS450_SpiGyroWrapper* sim = reinterpret_cast<hal::ADXRS450_SpiGyroWrapper*>(aPointerAddress);
    delete sim;
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXRS450_1GyroDataJNI
 * Method:    getAngle
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXRS450_1GyroDataJNI_getAngle
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    return ((hal::ADXRS450_SpiGyroWrapper*)aPointerAddress)->GetAngle();
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXRS450_1GyroDataJNI
 * Method:    setAngle
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXRS450_1GyroDataJNI_setAngle
  (JNIEnv*, jclass, jlong aPointerAddress, jdouble aAngle)
{
    ((hal::ADXRS450_SpiGyroWrapper*)aPointerAddress)->SetAngle(aAngle);
}

} // extern "C"

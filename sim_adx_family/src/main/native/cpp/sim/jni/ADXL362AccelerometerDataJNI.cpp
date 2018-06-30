#include <jni.h>

#include "ADXL362_SpiAccelerometerData.h"
#include "edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI.h"

extern "C" {

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI
 * Method:    createAccelerometer
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_createAccelerometer
  (JNIEnv*, jclass, jint port)
{
    hal::ADXL362_SpiAccelerometer* output = new hal::ADXL362_SpiAccelerometer(port);
    return (jlong)(output);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI
 * Method:    deleteAccelerometer
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_deleteAccelerometer
  (JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL362_SpiAccelerometer* accel = (hal::ADXL362_SpiAccelerometer*)pointerAddress;
    delete accel;
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI
 * Method:    getX
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_getX
  (JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL362_SpiAccelerometer* accel = (hal::ADXL362_SpiAccelerometer*)pointerAddress;
    return accel->GetX();
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI
 * Method:    getY
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_getY
  (JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL362_SpiAccelerometer* accel = (hal::ADXL362_SpiAccelerometer*)pointerAddress;
    return accel->GetY();
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI
 * Method:    getZ
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_getZ
  (JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL362_SpiAccelerometer* accel = (hal::ADXL362_SpiAccelerometer*)pointerAddress;
    return accel->GetZ();
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI
 * Method:    setX
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_setX
  (JNIEnv*, jclass, jlong pointerAddress, jdouble x)
{
    hal::ADXL362_SpiAccelerometer* accel = (hal::ADXL362_SpiAccelerometer*)pointerAddress;
    accel->SetX(x);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI
 * Method:    setY
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_setY
  (JNIEnv*, jclass, jlong pointerAddress, jdouble y)
{
    hal::ADXL362_SpiAccelerometer* accel = (hal::ADXL362_SpiAccelerometer*)pointerAddress;
    accel->SetY(y);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI
 * Method:    setZ
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_setZ
  (JNIEnv*, jclass, jlong pointerAddress, jdouble z)
{
    hal::ADXL362_SpiAccelerometer* accel = (hal::ADXL362_SpiAccelerometer*)pointerAddress;
    accel->SetZ(z);
}

} // extern "C"

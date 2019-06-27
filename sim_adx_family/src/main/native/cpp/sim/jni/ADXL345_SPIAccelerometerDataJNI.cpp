#include <jni.h>

#include "ADXL345_SpiAccelerometerData.h"
#include "edu_wpi_first_hal_sim_mockdata_ADXL345_SPIAccelerometerDataJNI.h"

extern "C" {

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI
 * Method:    createAccelerometer
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_createAccelerometer
  (JNIEnv*, jclass, jint port)
{
    hal::ADXL345_SpiAccelerometer* output = new hal::ADXL345_SpiAccelerometer(port);
    return (jlong)(output);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI
 * Method:    deleteAccelerometer
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_deleteAccelerometer
  (JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL345_SpiAccelerometer* accel = (hal::ADXL345_SpiAccelerometer*)pointerAddress;
    delete accel;
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI
 * Method:    getX
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_getX
  (JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL345_SpiAccelerometer* accel = (hal::ADXL345_SpiAccelerometer*)pointerAddress;
    return accel->GetX();
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI
 * Method:    getY
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_getY
  (JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL345_SpiAccelerometer* accel = (hal::ADXL345_SpiAccelerometer*)pointerAddress;
    return accel->GetY();
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI
 * Method:    getZ
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_getZ
  (JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL345_SpiAccelerometer* accel = (hal::ADXL345_SpiAccelerometer*)pointerAddress;
    return accel->GetZ();
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI
 * Method:    setX
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_setX
  (JNIEnv*, jclass, jlong pointerAddress, jdouble x)
{
    hal::ADXL345_SpiAccelerometer* accel = (hal::ADXL345_SpiAccelerometer*)pointerAddress;
    accel->SetX(x);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI
 * Method:    setY
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_setY
  (JNIEnv*, jclass, jlong pointerAddress, jdouble y)
{
    hal::ADXL345_SpiAccelerometer* accel = (hal::ADXL345_SpiAccelerometer*)pointerAddress;
    accel->SetY(y);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI
 * Method:    setZ
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_setZ
  (JNIEnv*, jclass, jlong pointerAddress, jdouble z)
{
    hal::ADXL345_SpiAccelerometer* accel = (hal::ADXL345_SpiAccelerometer*)pointerAddress;
    accel->SetZ(z);
}

} // extern "C"

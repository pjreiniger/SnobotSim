#include <jni.h>

#include "ADXL345_SpiAccelerometerData.h"
#include "edu_wpi_first_hal_sim_mockdata_ADXL345_SPIAccelerometerDataJNI.h"

extern "C" {

JNIEXPORT jlong JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_createAccelerometer(JNIEnv*, jclass, jint port)
{
    hal::ADXL345_SpiAccelerometer* output = new hal::ADXL345_SpiAccelerometer(port);
    return (jlong)(output);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_deleteAccelerometer(JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL345_SpiAccelerometer* accel = (hal::ADXL345_SpiAccelerometer*)pointerAddress;
    delete accel;
}

JNIEXPORT jdouble JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_getX(JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL345_SpiAccelerometer* accel = (hal::ADXL345_SpiAccelerometer*)pointerAddress;
    return accel->GetX();
}

JNIEXPORT jdouble JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_getY(JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL345_SpiAccelerometer* accel = (hal::ADXL345_SpiAccelerometer*)pointerAddress;
    return accel->GetY();
}

JNIEXPORT jdouble JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_getZ(JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL345_SpiAccelerometer* accel = (hal::ADXL345_SpiAccelerometer*)pointerAddress;
    return accel->GetZ();
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_setX(JNIEnv*, jclass, jlong pointerAddress, jdouble x)
{
    hal::ADXL345_SpiAccelerometer* accel = (hal::ADXL345_SpiAccelerometer*)pointerAddress;
    accel->SetX(x);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_setY(JNIEnv*, jclass, jlong pointerAddress, jdouble y)
{
    hal::ADXL345_SpiAccelerometer* accel = (hal::ADXL345_SpiAccelerometer*)pointerAddress;
    accel->SetY(y);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1SPIAccelerometerDataJNI_setZ(JNIEnv*, jclass, jlong pointerAddress, jdouble z)
{
    hal::ADXL345_SpiAccelerometer* accel = (hal::ADXL345_SpiAccelerometer*)pointerAddress;
    accel->SetZ(z);
}

} // extern "C"

#include <jni.h>

#include "ADXL362_SpiAccelerometerData.h"
#include "edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI.h"

extern "C" {

JNIEXPORT jlong JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_createAccelerometer(JNIEnv*, jclass, jint port)
{
    hal::ADXL362_SpiAccelerometer* output = new hal::ADXL362_SpiAccelerometer(port);
    return (jlong)(output);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_deleteAccelerometer(JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL362_SpiAccelerometer* accel = (hal::ADXL362_SpiAccelerometer*)pointerAddress;
    delete accel;
}

JNIEXPORT jdouble JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_getX(JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL362_SpiAccelerometer* accel = (hal::ADXL362_SpiAccelerometer*)pointerAddress;
    return accel->GetX();
}

JNIEXPORT jdouble JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_getY(JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL362_SpiAccelerometer* accel = (hal::ADXL362_SpiAccelerometer*)pointerAddress;
    return accel->GetY();
}

JNIEXPORT jdouble JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_getZ(JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL362_SpiAccelerometer* accel = (hal::ADXL362_SpiAccelerometer*)pointerAddress;
    return accel->GetZ();
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_setX(JNIEnv*, jclass, jlong pointerAddress, jdouble x)
{
    hal::ADXL362_SpiAccelerometer* accel = (hal::ADXL362_SpiAccelerometer*)pointerAddress;
    accel->SetX(x);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_setY(JNIEnv*, jclass, jlong pointerAddress, jdouble y)
{
    hal::ADXL362_SpiAccelerometer* accel = (hal::ADXL362_SpiAccelerometer*)pointerAddress;
    accel->SetY(y);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL362AccelerometerDataJNI_setZ(JNIEnv*, jclass, jlong pointerAddress, jdouble z)
{
    hal::ADXL362_SpiAccelerometer* accel = (hal::ADXL362_SpiAccelerometer*)pointerAddress;
    accel->SetZ(z);
}

} // extern "C"

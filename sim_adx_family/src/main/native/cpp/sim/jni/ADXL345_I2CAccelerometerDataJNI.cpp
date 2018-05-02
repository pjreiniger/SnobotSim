#include <jni.h>

#include "ADXL345_I2CAccelerometerData.h"
#include "edu_wpi_first_hal_sim_mockdata_ADXL345_I2CAccelerometerDataJNI.h"

extern "C" {

JNIEXPORT jlong JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1I2CAccelerometerDataJNI_createAccelerometer(JNIEnv*, jclass, jint port)
{
    hal::ADXL345_I2CData* output = new hal::ADXL345_I2CData(port);
    return (jlong)(output);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1I2CAccelerometerDataJNI_deleteAccelerometer(JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL345_I2CData* accel = (hal::ADXL345_I2CData*)pointerAddress;
    delete accel;
}

JNIEXPORT jdouble JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1I2CAccelerometerDataJNI_getX(JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL345_I2CData* accel = (hal::ADXL345_I2CData*)pointerAddress;
    return accel->GetX();
}

JNIEXPORT jdouble JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1I2CAccelerometerDataJNI_getY(JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL345_I2CData* accel = (hal::ADXL345_I2CData*)pointerAddress;
    return accel->GetY();
}

JNIEXPORT jdouble JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1I2CAccelerometerDataJNI_getZ(JNIEnv*, jclass, jlong pointerAddress)
{
    hal::ADXL345_I2CData* accel = (hal::ADXL345_I2CData*)pointerAddress;
    return accel->GetZ();
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1I2CAccelerometerDataJNI_setX(JNIEnv*, jclass, jlong pointerAddress, jdouble x)
{
    hal::ADXL345_I2CData* accel = (hal::ADXL345_I2CData*)pointerAddress;
    accel->SetX(x);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1I2CAccelerometerDataJNI_setY(JNIEnv*, jclass, jlong pointerAddress, jdouble y)
{
    hal::ADXL345_I2CData* accel = (hal::ADXL345_I2CData*)pointerAddress;
    accel->SetY(y);
}

JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_ADXL345_1I2CAccelerometerDataJNI_setZ(JNIEnv*, jclass, jlong pointerAddress, jdouble z)
{
    hal::ADXL345_I2CData* accel = (hal::ADXL345_I2CData*)pointerAddress;
    accel->SetZ(z);
}

} // extern "C"

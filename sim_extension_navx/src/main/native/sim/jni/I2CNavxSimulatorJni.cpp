

#include <jni.h>

#include "NavxSim/I2CNavxSimulator.h"
#include "com_snobot_simulator_navx_I2CNavxSimulatorJni.h"

/*
 * Class:     com_snobot_simulator_navx_I2CNavxSimulatorJni
 * Method:    createNavx
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL
Java_com_snobot_simulator_navx_I2CNavxSimulatorJni_createNavx
  (JNIEnv*, jclass, jint aPort)
{
    I2CNavxSimulator* output = new I2CNavxSimulator(aPort);
    return (jlong)(output);
}

/*
 * Class:     com_snobot_simulator_navx_I2CNavxSimulatorJni
 * Method:    deleteNavx
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_navx_I2CNavxSimulatorJni_deleteNavx
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    I2CNavxSimulator* simulator = reinterpret_cast<I2CNavxSimulator*>(aPointerAddress);
    delete simulator;
}

/*
 * Class:     com_snobot_simulator_navx_I2CNavxSimulatorJni
 * Method:    getXAccel
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_com_snobot_simulator_navx_I2CNavxSimulatorJni_getXAccel
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    return simulator->GetX();
}

/*
 * Class:     com_snobot_simulator_navx_I2CNavxSimulatorJni
 * Method:    getYAccel
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_com_snobot_simulator_navx_I2CNavxSimulatorJni_getYAccel
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    return simulator->GetY();
}

/*
 * Class:     com_snobot_simulator_navx_I2CNavxSimulatorJni
 * Method:    getZAccel
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_com_snobot_simulator_navx_I2CNavxSimulatorJni_getZAccel
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    return simulator->GetZ();
}

/*
 * Class:     com_snobot_simulator_navx_I2CNavxSimulatorJni
 * Method:    setXAccel
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_navx_I2CNavxSimulatorJni_setXAccel
  (JNIEnv*, jclass, jlong aPointerAddress, jdouble value)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    simulator->SetX(value);
}

/*
 * Class:     com_snobot_simulator_navx_I2CNavxSimulatorJni
 * Method:    setYAccel
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_navx_I2CNavxSimulatorJni_setYAccel
  (JNIEnv*, jclass, jlong aPointerAddress, jdouble value)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    simulator->SetY(value);
}

/*
 * Class:     com_snobot_simulator_navx_I2CNavxSimulatorJni
 * Method:    setZAccel
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_navx_I2CNavxSimulatorJni_setZAccel
  (JNIEnv*, jclass, jlong aPointerAddress, jdouble value)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    simulator->SetZ(value);
}

/*
 * Class:     com_snobot_simulator_navx_I2CNavxSimulatorJni
 * Method:    getYaw
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_com_snobot_simulator_navx_I2CNavxSimulatorJni_getYaw
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    return simulator->GetYaw();
}

/*
 * Class:     com_snobot_simulator_navx_I2CNavxSimulatorJni
 * Method:    getPitch
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_com_snobot_simulator_navx_I2CNavxSimulatorJni_getPitch
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    return simulator->GetPitch();
}

/*
 * Class:     com_snobot_simulator_navx_I2CNavxSimulatorJni
 * Method:    getRoll
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_com_snobot_simulator_navx_I2CNavxSimulatorJni_getRoll
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    return simulator->GetRoll();
}

/*
 * Class:     com_snobot_simulator_navx_I2CNavxSimulatorJni
 * Method:    setYaw
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_navx_I2CNavxSimulatorJni_setYaw
  (JNIEnv*, jclass, jlong aPointerAddress, jdouble value)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    simulator->SetYaw(value);
}

/*
 * Class:     com_snobot_simulator_navx_I2CNavxSimulatorJni
 * Method:    setPitch
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_navx_I2CNavxSimulatorJni_setPitch
  (JNIEnv*, jclass, jlong aPointerAddress, jdouble value)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    simulator->SetPitch(value);
}

/*
 * Class:     com_snobot_simulator_navx_I2CNavxSimulatorJni
 * Method:    setRoll
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_navx_I2CNavxSimulatorJni_setRoll
  (JNIEnv*, jclass, jlong aPointerAddress, jdouble value)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    simulator->SetRoll(value);
}

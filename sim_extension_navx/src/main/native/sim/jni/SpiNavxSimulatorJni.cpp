

#include <jni.h>

#include "NavxSim/SpiNavxSimulator.h"
#include "com_snobot_simulator_navx_SpiNavxSimulatorJni.h"

/*
 * Class:     com_snobot_simulator_navx_SpiNavxSimulatorJni
 * Method:    createNavx
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL
Java_com_snobot_simulator_navx_SpiNavxSimulatorJni_createNavx
  (JNIEnv*, jclass, jint aPort)
{
    SpiNavxSimulator* output = new SpiNavxSimulator(aPort);
    return (jlong)(output);
}

/*
 * Class:     com_snobot_simulator_navx_SpiNavxSimulatorJni
 * Method:    deleteNavx
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_navx_SpiNavxSimulatorJni_deleteNavx
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    SpiNavxSimulator* simulator = reinterpret_cast<SpiNavxSimulator*>(aPointerAddress);
    delete simulator;
}

/*
 * Class:     com_snobot_simulator_navx_SpiNavxSimulatorJni
 * Method:    getXAccel
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_com_snobot_simulator_navx_SpiNavxSimulatorJni_getXAccel
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    return simulator->GetX();
}

/*
 * Class:     com_snobot_simulator_navx_SpiNavxSimulatorJni
 * Method:    getYAccel
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_com_snobot_simulator_navx_SpiNavxSimulatorJni_getYAccel
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    return simulator->GetY();
}

/*
 * Class:     com_snobot_simulator_navx_SpiNavxSimulatorJni
 * Method:    getZAccel
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_com_snobot_simulator_navx_SpiNavxSimulatorJni_getZAccel
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    return simulator->GetZ();
}

/*
 * Class:     com_snobot_simulator_navx_SpiNavxSimulatorJni
 * Method:    setXAccel
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_navx_SpiNavxSimulatorJni_setXAccel
  (JNIEnv*, jclass, jlong aPointerAddress, jdouble value)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    simulator->SetX(value);
}

/*
 * Class:     com_snobot_simulator_navx_SpiNavxSimulatorJni
 * Method:    setYAccel
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_navx_SpiNavxSimulatorJni_setYAccel
  (JNIEnv*, jclass, jlong aPointerAddress, jdouble value)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    simulator->SetY(value);
}

/*
 * Class:     com_snobot_simulator_navx_SpiNavxSimulatorJni
 * Method:    setZAccel
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_navx_SpiNavxSimulatorJni_setZAccel
  (JNIEnv*, jclass, jlong aPointerAddress, jdouble value)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    simulator->SetZ(value);
}

/*
 * Class:     com_snobot_simulator_navx_SpiNavxSimulatorJni
 * Method:    getYaw
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_com_snobot_simulator_navx_SpiNavxSimulatorJni_getYaw
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    return simulator->GetYaw();
}

/*
 * Class:     com_snobot_simulator_navx_SpiNavxSimulatorJni
 * Method:    getPitch
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_com_snobot_simulator_navx_SpiNavxSimulatorJni_getPitch
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    return simulator->GetPitch();
}

/*
 * Class:     com_snobot_simulator_navx_SpiNavxSimulatorJni
 * Method:    getRoll
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL
Java_com_snobot_simulator_navx_SpiNavxSimulatorJni_getRoll
  (JNIEnv*, jclass, jlong aPointerAddress)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    return simulator->GetRoll();
}

/*
 * Class:     com_snobot_simulator_navx_SpiNavxSimulatorJni
 * Method:    setYaw
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_navx_SpiNavxSimulatorJni_setYaw
  (JNIEnv*, jclass, jlong aPointerAddress, jdouble value)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    simulator->SetYaw(value);
}

/*
 * Class:     com_snobot_simulator_navx_SpiNavxSimulatorJni
 * Method:    setPitch
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_navx_SpiNavxSimulatorJni_setPitch
  (JNIEnv*, jclass, jlong aPointerAddress, jdouble value)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    simulator->SetPitch(value);
}

/*
 * Class:     com_snobot_simulator_navx_SpiNavxSimulatorJni
 * Method:    setRoll
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL
Java_com_snobot_simulator_navx_SpiNavxSimulatorJni_setRoll
  (JNIEnv*, jclass, jlong aPointerAddress, jdouble value)
{
    NavxSimulator* simulator = reinterpret_cast<NavxSimulator*>(aPointerAddress);
    simulator->SetRoll(value);
}

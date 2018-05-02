
#include <jni.h>

#include <cassert>

#include "com_ctre_phoenix_MotorControl_CAN_MotControllerJNI.h"
#include "ctre/phoenix/CCI/MotController_CCI.h"

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    Create
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_Create(JNIEnv*, jclass, jint baseArbId)
{
    return (jlong)c_MotController_Create1(baseArbId);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetDeviceNumber
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetDeviceNumber(JNIEnv*, jclass, jlong handle)
{
    int deviceNumber = 0;
    c_MotController_GetDeviceNumber(&handle, &deviceNumber);
    return deviceNumber;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    SetDemand
 * Signature: (JIII)V
 */
JNIEXPORT void JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_SetDemand(JNIEnv*, jclass, jlong handle, jint mode, jint demand0, jint demand1)
{
    c_MotController_SetDemand(&handle, mode, demand0, demand1);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    Set_4
 * Signature: (JIDDI)V
 */
JNIEXPORT void JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_Set_14
  (JNIEnv *, jclass, jlong handle, jint aMode, jdouble aDemand0, jdouble aDemand1, jint demand1Type)
{
    c_MotController_Set_4(&handle, aMode, aDemand0, aDemand1, demand1Type);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    SetNeutralMode
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_SetNeutralMode(JNIEnv*, jclass, jlong handle, jint neutralMode)
{
    c_MotController_SetNeutralMode(&handle, neutralMode);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    SetSensorPhase
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_SetSensorPhase(JNIEnv*, jclass, jlong handle, jboolean phaseSensor)
{
    c_MotController_SetSensorPhase(&handle, phaseSensor);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    SetInverted
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_SetInverted(JNIEnv*, jclass, jlong handle, jboolean inverted)
{
    c_MotController_SetInverted(&handle, inverted);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigOpenLoopRamp
 * Signature: (JDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigOpenLoopRamp(JNIEnv*, jclass, jlong handle, jdouble secondsFromNeutralToFull, jint timeoutMs)
{
    return (jint)c_MotController_ConfigOpenLoopRamp(&handle, secondsFromNeutralToFull, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigClosedLoopRamp
 * Signature: (JDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigClosedLoopRamp(JNIEnv*, jclass, jlong handle, jdouble secondsFromNeutralToFull, jint timeoutMs)
{
    return (jint)c_MotController_ConfigClosedLoopRamp(&handle, secondsFromNeutralToFull, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigPeakOutputForward
 * Signature: (JDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigPeakOutputForward(JNIEnv*, jclass, jlong handle, jdouble percentOut, jint timeoutMs)
{
    return (jint)c_MotController_ConfigPeakOutputForward(&handle, percentOut, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigPeakOutputReverse
 * Signature: (JDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigPeakOutputReverse(JNIEnv*, jclass, jlong handle, jdouble percentOut, jint timeoutMs)
{
    return (jint)c_MotController_ConfigPeakOutputReverse(&handle, percentOut, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigNominalOutputForward
 * Signature: (JDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigNominalOutputForward(JNIEnv*, jclass, jlong handle, jdouble value, jint timeoutMs)
{
    return (jint)c_MotController_ConfigNominalOutputForward(&handle, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigNominalOutputReverse
 * Signature: (JDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigNominalOutputReverse(JNIEnv*, jclass, jlong handle, jdouble value, jint timeoutMs)
{
    return (jint)c_MotController_ConfigNominalOutputReverse(&handle, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigNeutralDeadband
 * Signature: (JDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigNeutralDeadband(JNIEnv*, jclass, jlong handle, jdouble value, jint timeoutMs)
{
    return (jint)c_MotController_ConfigNeutralDeadband(&handle, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigVoltageCompSaturation
 * Signature: (JDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigVoltageCompSaturation(JNIEnv*, jclass, jlong handle, jdouble value, jint timeoutMs)
{
    return (jint)c_MotController_ConfigVoltageCompSaturation(&handle, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigVoltageMeasurementFilter
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigVoltageMeasurementFilter(JNIEnv*, jclass, jlong handle, jint value, jint timeoutMs)
{
    return (jint)c_MotController_ConfigVoltageMeasurementFilter(&handle, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    EnableVoltageCompensation
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_EnableVoltageCompensation(JNIEnv*, jclass, jlong handle, jboolean value)
{
    c_MotController_EnableVoltageCompensation(&handle, value);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetBusVoltage
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetBusVoltage(JNIEnv*, jclass, jlong handle)
{
    double output = 0;
    c_MotController_GetBusVoltage(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetMotorOutputPercent
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetMotorOutputPercent(JNIEnv*, jclass, jlong handle)
{
    double percentage = 0;
    c_MotController_GetMotorOutputPercent(&handle, &percentage);
    return percentage;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetOutputCurrent
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetOutputCurrent(JNIEnv*, jclass, jlong handle)
{
    double output = 0;
    c_MotController_GetOutputCurrent(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetTemperature
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetTemperature(JNIEnv*, jclass, jlong handle)
{
    double output = 0;
    c_MotController_GetTemperature(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigRemoteFeedbackFilter
 * Signature: (JIIII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigRemoteFeedbackFilter(JNIEnv*, jclass, jlong handle, jint arbId, jint peripheralIdx, jint reserved, jint timeoutMs)
{
    return (jint)c_MotController_ConfigRemoteFeedbackFilter(&handle, arbId, peripheralIdx, reserved, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigSelectedFeedbackSensor
 * Signature: (JIII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigSelectedFeedbackSensor(JNIEnv*, jclass, jlong handle, jint feedbackDevice, jint pidIdx, jint timeoutMs)
{
    return (jint)c_MotController_ConfigSelectedFeedbackSensor(&handle, feedbackDevice, pidIdx, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigSelectedFeedbackCoefficient
 * Signature: (JDII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigSelectedFeedbackCoefficient
  (JNIEnv *, jclass, jlong aHandle, jdouble aCoofficient, jint aPidIdx, jint aTimeout)
{
    return (jint) c_MotController_ConfigSelectedFeedbackCoefficient(&aHandle, aCoofficient, aPidIdx, aTimeout);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetSelectedSensorPosition
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetSelectedSensorPosition(JNIEnv*, jclass, jlong handle, jint pidIdx)
{
    int output = 0;
    c_MotController_GetSelectedSensorPosition(&handle, &output, pidIdx);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetSelectedSensorVelocity
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetSelectedSensorVelocity(JNIEnv*, jclass, jlong handle, jint pidIdx)
{
    int output = 0;
    c_MotController_GetSelectedSensorVelocity(&handle, &output, pidIdx);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    SetSelectedSensorPosition
 * Signature: (JIII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_SetSelectedSensorPosition(JNIEnv*, jclass, jlong handle, jint value, jint pidIdx, jint timeoutMs)
{
    return (jint)c_MotController_SetSelectedSensorPosition(&handle, value, pidIdx, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    SetControlFramePeriod
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_SetControlFramePeriod(JNIEnv*, jclass, jlong handle, jint value, jint timeoutMs)
{
    return (jint)c_MotController_SetControlFramePeriod(&handle, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    SetStatusFramePeriod
 * Signature: (JIII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_SetStatusFramePeriod(JNIEnv*, jclass, jlong handle, jint frame, jint periodMs, jint timeoutMs)
{
    return (jint)c_MotController_SetStatusFramePeriod(&handle, frame, periodMs, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetStatusFramePeriod
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetStatusFramePeriod(JNIEnv*, jclass, jlong handle, jint frame, jint timeoutMs)
{
    int status = 0;
    c_MotController_GetStatusFramePeriod(&handle, frame, &status, timeoutMs);
    return status;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigVelocityMeasurementPeriod
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigVelocityMeasurementPeriod(JNIEnv*, jclass, jlong handle, jint value, jint timeoutMs)
{
    return (jint)c_MotController_ConfigVelocityMeasurementPeriod(&handle, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigVelocityMeasurementWindow
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigVelocityMeasurementWindow(JNIEnv*, jclass, jlong handle, jint value, jint timeoutMs)
{
    return (jint)c_MotController_ConfigVelocityMeasurementWindow(&handle, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigForwardLimitSwitchSource
 * Signature: (JIIII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigForwardLimitSwitchSource(JNIEnv*, jclass, jlong handle, jint type, jint normalOpenOrClose, jint deviceId, jint timeoutMs)
{
    return (jint)c_MotController_ConfigForwardLimitSwitchSource(&handle, type, normalOpenOrClose, deviceId, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigReverseLimitSwitchSource
 * Signature: (JIIII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigReverseLimitSwitchSource(JNIEnv*, jclass, jlong handle, jint type, jint normalOpenOrClose, jint deviceID, jint timeoutMs)
{
    return (jint)c_MotController_ConfigReverseLimitSwitchSource(&handle, type, normalOpenOrClose, deviceID, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    EnableLimitSwitches
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_OverrideLimitSwitchesEnable
  (JNIEnv *, jclass, jlong aHandle, jboolean aEnable)
{
    c_MotController_OverrideLimitSwitchesEnable(&aHandle, aEnable);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigForwardSoftLimit
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigForwardSoftLimitThreshold(JNIEnv*, jclass, jlong handle, jint value, jint timeoutMs)
{
    return (jint)c_MotController_ConfigForwardSoftLimitThreshold(&handle, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigReverseSoftLimit
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigReverseSoftLimitThreshold(JNIEnv*, jclass, jlong handle, jint value, jint timeoutMs)
{
    return (jint)c_MotController_ConfigReverseSoftLimitThreshold(&handle, value, timeoutMs);
}


JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigForwardSoftLimitEnable(JNIEnv *, jclass, jlong aHandle, jboolean aEnable, jint aTimeout)
{
    return (jint) c_MotController_ConfigForwardSoftLimitEnable(&aHandle, aEnable, aTimeout);
}


JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigReverseSoftLimitEnable(JNIEnv *, jclass, jlong aHandle, jboolean aEnable, jint aTimeout)
{
    return (jint) c_MotController_ConfigReverseSoftLimitEnable(&aHandle, aEnable, aTimeout);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    EnableSoftLimits
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_OverrideSoftLimitsEnable(JNIEnv*, jclass, jlong handle, jboolean value)
{
    c_MotController_OverrideSoftLimitsEnable(&handle, value);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    Config_kP
 * Signature: (JIDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_Config_1kP(JNIEnv*, jclass, jlong handle, jint slot, jdouble value, jint timeoutMs)
{
    return (jint)c_MotController_Config_kP(&handle, slot, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    Config_kI
 * Signature: (JIDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_Config_1kI(JNIEnv*, jclass, jlong handle, jint slot, jdouble value, jint timeoutMs)
{
    return (jint)c_MotController_Config_kI(&handle, slot, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    Config_kD
 * Signature: (JIDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_Config_1kD(JNIEnv*, jclass, jlong handle, jint slot, jdouble value, jint timeoutMs)
{
    return (jint)c_MotController_Config_kD(&handle, slot, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    Config_kF
 * Signature: (JIDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_Config_1kF(JNIEnv*, jclass, jlong handle, jint slot, jdouble value, jint timeoutMs)
{
    return (jint)c_MotController_Config_kF(&handle, slot, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    Config_IntegralZone
 * Signature: (JIDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_Config_1IntegralZone(JNIEnv*, jclass, jlong handle, jint slot, jdouble value, jint timeoutMs)
{
    return (jint)c_MotController_Config_IntegralZone(&handle, slot, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigAllowableClosedloopError
 * Signature: (JIII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigAllowableClosedloopError(JNIEnv*, jclass, jlong handle, jint slotIdx, jint allowableClosedLoopError, jint timeoutMs)
{
    return (jint)c_MotController_ConfigAllowableClosedloopError(&handle, slotIdx, allowableClosedLoopError, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigMaxIntegralAccumulator
 * Signature: (JIDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigMaxIntegralAccumulator(JNIEnv*, jclass, jlong handle, jint slotIdx, jdouble iaccum, jint timeoutMs)
{
    return (jint)c_MotController_ConfigMaxIntegralAccumulator(&handle, slotIdx, iaccum, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigClosedLoopPeakOutput
 * Signature: (JIDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigClosedLoopPeakOutput
  (JNIEnv *, jclass, jlong aHandle, jint aSlotIdx, jdouble aPercentOut, jint aTimeout)
{
    return (jint) c_MotController_ConfigClosedLoopPeakOutput(&aHandle, aSlotIdx, aPercentOut, aTimeout);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigClosedLoopPeriod
 * Signature: (JIII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigClosedLoopPeriod
  (JNIEnv *, jclass, jlong aHandle, jint aSlotIdx, jint aLoopTime, jint aTimeout)
{
    return (jint) c_MotController_ConfigClosedLoopPeriod(&aHandle, aSlotIdx, aLoopTime, aTimeout);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    SetIntegralAccumulator
 * Signature: (JDI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_SetIntegralAccumulator(JNIEnv*, jclass, jlong handle, jdouble value, jint pidIdx, jint timeoutMs)
{
    return (jint)c_MotController_SetIntegralAccumulator(&handle, value, pidIdx, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetClosedLoopError
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetClosedLoopError(JNIEnv*, jclass, jlong handle, jint slotIdx)
{
    int closedLoopError = 0;
    c_MotController_GetClosedLoopError(&handle, &closedLoopError, slotIdx);
    return closedLoopError;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetIntegralAccumulator
 * Signature: (JI)D
 */
JNIEXPORT jdouble JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetIntegralAccumulator(JNIEnv*, jclass, jlong handle, jint timeoutMs)
{
    double output = 0;
    c_MotController_GetIntegralAccumulator(&handle, &output, timeoutMs);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetErrorDerivative
 * Signature: (JI)F
 */
JNIEXPORT jdouble JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetErrorDerivative(JNIEnv*, jclass, jlong handle, jint slotIdx)
{
    double output = 0;
    c_MotController_GetErrorDerivative(&handle, &output, slotIdx);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    SelectProfileSlot
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_SelectProfileSlot(JNIEnv*, jclass, jlong handle, jint slotIdx, jint pidIdx)
{
    c_MotController_SelectProfileSlot(&handle, slotIdx, pidIdx);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetActiveTrajectoryPosition
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetActiveTrajectoryPosition(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetActiveTrajectoryPosition(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetActiveTrajectoryVelocity
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetActiveTrajectoryVelocity(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetActiveTrajectoryVelocity(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetActiveTrajectoryHeading
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetActiveTrajectoryHeading(JNIEnv*, jclass, jlong handle)
{
    double output = 0;
    c_MotController_GetActiveTrajectoryHeading(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigMotionCruiseVelocity
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigMotionCruiseVelocity(JNIEnv*, jclass, jlong handle, jint sensorUnitsPer100ms, jint timeoutMs)
{
    return (jint)c_MotController_ConfigMotionCruiseVelocity(&handle, sensorUnitsPer100ms, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigMotionAcceleration
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigMotionAcceleration(JNIEnv*, jclass, jlong handle, jint sensorUnitsPer100msPerSec, jint timeoutMs)
{
    return (jint)c_MotController_ConfigMotionAcceleration(&handle, sensorUnitsPer100msPerSec, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ClearMotionProfileTrajectories
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ClearMotionProfileTrajectories(JNIEnv*, jclass, jlong handle)
{
    return (jint)c_MotController_ClearMotionProfileTrajectories(&handle);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetMotionProfileTopLevelBufferCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetMotionProfileTopLevelBufferCount(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetMotionProfileTopLevelBufferCount(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    PushMotionProfileTrajectory
 * Signature: (JDDDIZZ)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_PushMotionProfileTrajectory(JNIEnv*, jclass, jlong handle, jdouble position, jdouble velocity, jdouble headingDeg, jint profileSlotSelect, jboolean isLastPoint, jboolean zeroPos)
{
    return (jint)c_MotController_PushMotionProfileTrajectory(&handle, position, velocity, headingDeg, profileSlotSelect, isLastPoint, zeroPos);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    PushMotionProfileTrajectory2
 * Signature: (JDDDIZZ)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_PushMotionProfileTrajectory2(JNIEnv*, jclass, jlong handle, jdouble position, jdouble velocity, jdouble headingDeg,
        jint profileSlotSelect0, jint profileSlotSelect1, jboolean isLastPoint, jboolean zeroPos, jint durationMs)
{
    return (jint) c_MotController_PushMotionProfileTrajectory_2(
            &handle, position, velocity, headingDeg,
            profileSlotSelect0, profileSlotSelect1, isLastPoint, zeroPos, durationMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    IsMotionProfileTopLevelBufferFull
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_IsMotionProfileTopLevelBufferFull(JNIEnv*, jclass, jlong handle)
{
    bool output = 0;
    c_MotController_IsMotionProfileTopLevelBufferFull(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ProcessMotionProfileBuffer
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ProcessMotionProfileBuffer(JNIEnv*, jclass, jlong handle)
{
    return (jint)c_MotController_ProcessMotionProfileBuffer(&handle);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetMotionProfileStatus
 * Signature: (J[I)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetMotionProfileStatus(JNIEnv* env, jclass, jlong handle, jintArray result)
{
    static const int kSize = 9;
    int output[kSize];

    bool hasUnderrun = false;
    bool isUnderrun = false;
    bool activePointValid = false;
    bool isLast = false;

    c_MotController_GetMotionProfileStatus(&handle,
            &output[0], &output[1], &output[2],
            &hasUnderrun, &isUnderrun, &activePointValid,
            &isLast, &output[7], &output[8]);

    output[3] = hasUnderrun;
    output[4] = isUnderrun;
    output[5] = activePointValid;
    output[6] = isLast;

    jint fill[kSize];
    for (int i = 0; i < kSize; ++i)
    {
        fill[i] = output[i];
    }

    env->SetIntArrayRegion(result, 0, kSize, fill);

    return 0;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetMotionProfileStatus2
 * Signature: (J[I)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetMotionProfileStatus2(JNIEnv* env, jclass, jlong handle, jintArray result)
{
    static const int kSize = 11;
    int output[kSize];

    bool hasUnderrun = false;
    bool isUnderrun = false;
    bool activePointValid = false;
    bool isLast = false;

    c_MotController_GetMotionProfileStatus_2(&handle,
            &output[0], &output[1], &output[2],
            &hasUnderrun, &isUnderrun, &activePointValid,
            &isLast, &output[7], &output[8],
            &output[9], &output[10]);

    output[3] = hasUnderrun;
    output[4] = isUnderrun;
    output[5] = activePointValid;
    output[6] = isLast;

    jint fill[kSize];
    for (int i = 0; i < kSize; ++i)
    {
        fill[i] = output[i];
    }

    env->SetIntArrayRegion(result, 0, kSize, fill);

    return 0;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ClearMotionProfileHasUnderrun
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ClearMotionProfileHasUnderrun(JNIEnv*, jclass, jlong handle, jint timeoutMs)
{
    return (jint)c_MotController_ClearMotionProfileHasUnderrun(&handle, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ChangeMotionControlFramePeriod
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ChangeMotionControlFramePeriod(JNIEnv*, jclass, jlong handle, jint timeoutMs)
{
    return (jint)c_MotController_ChangeMotionControlFramePeriod(&handle, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigMotionProfileTrajectoryPeriod
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigMotionProfileTrajectoryPeriod(JNIEnv*, jclass, jlong handle, jint durationMs, jint timeoutMs)
{
    return (jint) c_MotController_ConfigMotionProfileTrajectoryPeriod(&handle, durationMs, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetLastError
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetLastError(JNIEnv*, jclass, jlong handle)
{
    return (jint)c_MotController_GetLastError(&handle);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetFirmwareVersion
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetFirmwareVersion(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetFirmwareVersion(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    HasResetOccurred
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_HasResetOccurred(JNIEnv*, jclass, jlong handle)
{
    bool value = false;
    c_MotController_HasResetOccurred(&handle, &value);
    return value;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigSetCustomParam
 * Signature: (JIII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigSetCustomParam(JNIEnv*, jclass, jlong handle, jint newValue, jint paramIndex, jint timeoutMs)
{
    return (jint)c_MotController_ConfigSetCustomParam(&handle, newValue, paramIndex, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigGetCustomParam
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigGetCustomParam(JNIEnv*, jclass, jlong handle, jint paramIndex, jint timeoutMs)
{
    int output = 0;
    c_MotController_ConfigGetCustomParam(&handle, &output, paramIndex, timeoutMs);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigSetParameter
 * Signature: (JIDIII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigSetParameter(JNIEnv*, jclass, jlong handle, jint param, jdouble value, jint subValue, jint ordinal, jint timeoutMs)
{
    return (jint)c_MotController_ConfigSetParameter(&handle, param, value, subValue, ordinal, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigGetParameter
 * Signature: (JIII)F
 */
JNIEXPORT jdouble JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigGetParameter(JNIEnv*, jclass, jlong handle, jint param, jint ordinal, jint timeoutMs)
{
    double output = 0;
    c_MotController_ConfigGetParameter(&handle, param, &output, ordinal, timeoutMs);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigPeakCurrentLimit
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigPeakCurrentLimit(JNIEnv*, jclass, jlong handle, jint value, jint timeoutMs)
{
    return (jint)c_MotController_ConfigPeakCurrentLimit(&handle, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigPeakCurrentDuration
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigPeakCurrentDuration(JNIEnv*, jclass, jlong handle, jint value, jint timeoutMs)
{
    return (jint)c_MotController_ConfigPeakCurrentDuration(&handle, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ConfigContinuousCurrentLimit
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ConfigContinuousCurrentLimit(JNIEnv*, jclass, jlong handle, jint value, jint timeoutMs)
{
    return (jint)c_MotController_ConfigContinuousCurrentLimit(&handle, value, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    EnableCurrentLimit
 * Signature: (JZ)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_EnableCurrentLimit(JNIEnv*, jclass, jlong handle, jboolean value)
{
    return (jint) c_MotController_EnableCurrentLimit(&handle, value);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetAnalogIn
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetAnalogIn(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetAnalogIn(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    SetAnalogPosition
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_SetAnalogPosition(JNIEnv*, jclass, jlong handle, jint newPosition, jint timeoutMs)
{
    return (jint) c_MotController_SetAnalogPosition(&handle, newPosition, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetAnalogInRaw
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetAnalogInRaw(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetAnalogInRaw(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetAnalogInVel
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetAnalogInVel(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetAnalogInVel(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetQuadraturePosition
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetQuadraturePosition(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetQuadraturePosition(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    SetQuadraturePosition
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_SetQuadraturePosition(JNIEnv*, jclass, jlong handle, jint newPosition, jint timeoutMs)
{
    return (jint) c_MotController_SetQuadraturePosition(&handle, newPosition, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetQuadratureVelocity
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetQuadratureVelocity(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetQuadratureVelocity(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetPulseWidthPosition
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetPulseWidthPosition(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetPulseWidthPosition(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    SetPulseWidthPosition
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_SetPulseWidthPosition(JNIEnv*, jclass, jlong handle, jint newPosition, jint timeoutMs)
{
    return (jint) c_MotController_SetPulseWidthPosition(&handle, newPosition, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetPulseWidthVelocity
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetPulseWidthVelocity(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetPulseWidthVelocity(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetPulseWidthRiseToFallUs
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetPulseWidthRiseToFallUs(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetPulseWidthRiseToFallUs(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetPulseWidthRiseToRiseUs
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetPulseWidthRiseToRiseUs(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetPulseWidthRiseToRiseUs(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetPinStateQuadA
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetPinStateQuadA(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetPinStateQuadA(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetPinStateQuadB
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetPinStateQuadB(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetPinStateQuadB(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetPinStateQuadIdx
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetPinStateQuadIdx(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetPinStateQuadIdx(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    IsFwdLimitSwitchClosed
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_IsFwdLimitSwitchClosed(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_IsFwdLimitSwitchClosed(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    IsRevLimitSwitchClosed
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_IsRevLimitSwitchClosed(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_IsRevLimitSwitchClosed(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetFaults
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetFaults(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetFaults(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetStickyFaults
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetStickyFaults(JNIEnv*, jclass, jlong handle)
{
    int output = 0;
    c_MotController_GetStickyFaults(&handle, &output);
    return output;
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    ClearStickyFaults
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_ClearStickyFaults(JNIEnv*, jclass, jlong handle, jint timeoutMs)
{
    return (jint) c_MotController_ClearStickyFaults(&handle, timeoutMs);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    SelectDemandType
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_SelectDemandType(JNIEnv*, jclass, jlong handle, jint value)
{
    return (jint)c_MotController_SelectDemandType(&handle, value);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    SetMPEOutput
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_SetMPEOutput(JNIEnv*, jclass, jlong handle, jint value)
{
    return (jint)c_MotController_SetMPEOutput(&handle, value);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    EnableHeadingHold
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_EnableHeadingHold(JNIEnv*, jclass, jlong handle, jint value)
{
    return (jint)c_MotController_EnableHeadingHold(&handle, value);
}

/*
 * Class:     com_ctre_phoenix_motorcontrol_can_MotControllerJNI
 * Method:    GetClosedLoopTarget
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_motorcontrol_can_MotControllerJNI_GetClosedLoopTarget(JNIEnv*, jclass, jlong handle, jint pidIdx)
{
    int output = 0;
    c_MotController_GetClosedLoopTarget(&handle, &output, pidIdx);
    return output;
}

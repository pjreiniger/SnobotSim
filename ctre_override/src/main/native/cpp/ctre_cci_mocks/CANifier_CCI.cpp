
#include "ctre/phoenix/CCI/CANifier_CCI.h"

#include <cstring>
#include <iostream>
#include <string>
#include <vector>

#include "MockHooks.h"
#include "MockHookUtilities.h"


extern "C"{

void *c_CANifier_Create1(int deviceNumber)
{
    return NULL;
}

ctre::phoenix::ErrorCode c_CANifier_GetDescription(void *handle, char * toFill, int toFillByteSz, int * numBytesFilled)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_SetLEDOutput(void *handle,  uint32_t  dutyCycle,  uint32_t  ledChannel)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_SetGeneralOutputs(void *handle,  uint32_t  outputsBits,  uint32_t  isOutputBits)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_SetGeneralOutput(void *handle,  uint32_t  outputPin,  bool  outputValue,  bool  outputEnable)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_SetPWMOutput(void *handle,  uint32_t  pwmChannel,  uint32_t  dutyCycle)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_EnablePWMOutput(void *handle, uint32_t pwmChannel, bool bEnable)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_GetGeneralInputs(void *handle, bool allPins[], uint32_t capacity)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_GetGeneralInput(void *handle, uint32_t inputPin, bool * measuredInput)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_GetPWMInput(void *handle,  uint32_t  pwmChannel,  double dutyCycleAndPeriod [2])
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_GetLastError(void *handle)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_GetBusVoltage(void *handle, double * batteryVoltage)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_GetQuadraturePosition(void *handle, int * pos)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_SetQuadraturePosition(void *handle, int pos, int timeoutMs)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_GetQuadratureVelocity(void *handle, int * vel)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_GetQuadratureSensor(void *handle, int * pos, int * vel)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_ConfigVelocityMeasurementPeriod(void *handle, int period, int timeoutMs)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_ConfigVelocityMeasurementWindow(void *handle, int window, int timeoutMs)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

void c_CANifier_SetLastError(void *handle, int error)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
}

ctre::phoenix::ErrorCode c_CANifier_ConfigSetParameter(void *handle, int param, double value, int subValue, int ordinal, int timeoutMs)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_ConfigGetParameter(void *handle, int param, double *value, int ordinal, int timeoutMs)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_ConfigSetCustomParam(void *handle, int newValue, int paramIndex, int timeoutMs)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_ConfigGetCustomParam(void *handle, int *readValue, int paramIndex, int timoutMs)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_GetFaults(void *handle, int * param)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_GetStickyFaults(void *handle, int * param)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_ClearStickyFaults(void *handle, int timeoutMs)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_GetFirmwareVersion(void *handle, int *firmwareVers)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_HasResetOccurred(void *handle, bool * hasReset)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_SetStatusFramePeriod(void *handle, int frame, int periodMs, int timeoutMs)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_GetStatusFramePeriod(void *handle, int frame, int *periodMs, int timeoutMs)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}

ctre::phoenix::ErrorCode c_CANifier_SetControlFramePeriod(void *handle, int frame,  int periodMs)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode)0;
}


}

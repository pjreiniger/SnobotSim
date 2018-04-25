#pragma once

#include "ctre/phoenix/ErrorCode.h"
#include <set>

#include "MockHookUtilities.h"

namespace CANifier_CCI{
	enum GeneralPin{
	QUAD_IDX = 0,
	QUAD_B = 1,
	QUAD_A = 2,
	LIMR = 3,
	LIMF = 4,
	SDA = 5,
	SCL = 6,
	SPI_CS = 7,
	SPI_MISO_PWM2P = 8,
	SPI_MOSI_PWM1P = 9,
	SPI_CLK_PWM0P = 10,
	};
}

extern "C"{
	EXPORT_ void *c_CANifier_Create1(int deviceNumber);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_GetDescription(void *handle, char * toFill, int toFillByteSz, int * numBytesFilled);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_SetLEDOutput(void *handle,  uint32_t  dutyCycle,  uint32_t  ledChannel);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_SetGeneralOutputs(void *handle,  uint32_t  outputsBits,  uint32_t  isOutputBits);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_SetGeneralOutput(void *handle,  uint32_t  outputPin,  bool  outputValue,  bool  outputEnable);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_SetPWMOutput(void *handle,  uint32_t  pwmChannel,  uint32_t  dutyCycle);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_EnablePWMOutput(void *handle, uint32_t pwmChannel, bool bEnable);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_GetGeneralInputs(void *handle, bool allPins[], uint32_t capacity);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_GetGeneralInput(void *handle, uint32_t inputPin, bool * measuredInput);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_GetPWMInput(void *handle,  uint32_t  pwmChannel,  double dutyCycleAndPeriod [2]);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_GetLastError(void *handle);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_GetBusVoltage(void *handle, double * batteryVoltage);
	EXPORT_ void c_CANifier_SetLastError(void *handle, int error);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_ConfigSetParameter(void *handle, int param, double value, int subValue, int ordinal, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_ConfigGetParameter(void *handle, int param, double *value, int ordinal, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_ConfigSetCustomParam(void *handle, int newValue, int paramIndex, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_ConfigGetCustomParam(void *handle, int *readValue, int paramIndex, int timoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_GetFaults(void *handle, int * param) ;
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_GetStickyFaults(void *handle, int * param) ;
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_ClearStickyFaults(void *handle, int timeoutMs) ;
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_GetFirmwareVersion(void *handle, int *firmwareVers);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_HasResetOccurred(void *handle, bool * hasReset);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_SetStatusFramePeriod(void *handle, int frame, int periodMs, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_GetStatusFramePeriod(void *handle, int frame, int *periodMs, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_CANifier_SetControlFramePeriod(void *handle, int frame,	int periodMs) ;
}

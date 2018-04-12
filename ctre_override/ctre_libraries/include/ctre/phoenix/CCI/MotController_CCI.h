#include "ctre/phoenix/ErrorCode.h"

#include "MockHookUtilities.h"

extern "C"{
	EXPORT_ void* c_MotController_Create1(int baseArbId);

	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetDeviceNumber(void *handle, int *deviceNumber);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetDescription(void *handle, char * toFill, int toFillByteSz, int * numBytesFilled);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_SetDemand(void *handle, int mode, int demand0, int demand1);
	EXPORT_ void c_MotController_SetNeutralMode(void *handle, int neutralMode);
	EXPORT_ void c_MotController_SetSensorPhase(void *handle, bool PhaseSensor);
	EXPORT_ void c_MotController_SetInverted(void *handle, bool invert);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigOpenLoopRamp(void *handle, double secondsFromNeutralToFull, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigClosedLoopRamp(void *handle, double secondsFromNeutralToFull, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigPeakOutputForward(void *handle, double percentOut, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigPeakOutputReverse(void *handle, double percentOut, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigNominalOutputForward(void *handle, double percentOut, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigNominalOutputReverse(void *handle, double percentOut, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigNeutralDeadband(void *handle, double percentDeadband, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigVoltageCompSaturation(void *handle, double voltage, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigVoltageMeasurementFilter(void *handle, int filterWindowSamples, int timeoutMs);
	EXPORT_ void c_MotController_EnableVoltageCompensation(void *handle, bool enable);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetBusVoltage(void *handle, double *voltage);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetMotorOutputPercent(void *handle, double *percentOutput);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetOutputCurrent(void *handle, double *current);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetTemperature(void *handle, double *temperature);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigSelectedFeedbackSensor(void *handle, int feedbackDevice, int pidIdx, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigRemoteFeedbackFilter(void *handle, int deviceID, int remoteSensorSource, int remoteOrdinal, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigSensorTerm(void *handle, int sensorTerm, int feedbackDevice, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetSelectedSensorPosition(void *handle, int *param, int pidIdx);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetSelectedSensorVelocity(void *handle, int *param, int pidIdx);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_SetSelectedSensorPosition(void *handle, int sensorPos, int pidIdx,int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_SetControlFramePeriod(void *handle, int frame, int periodMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_SetStatusFramePeriod(void *handle, int frame, int periodMs, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetStatusFramePeriod(void *handle, int frame, int *periodMs, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigVelocityMeasurementPeriod(void *handle, int period, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigVelocityMeasurementWindow(void *handle, int windowSize, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigForwardLimitSwitchSource(void *handle, int type, int normalOpenOrClose, int deviceID, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigReverseLimitSwitchSource(void *handle, int type, int normalOpenOrClose, int deviceID, int timeoutMs);
	EXPORT_ void c_MotController_OverrideLimitSwitchesEnable(void *handle, bool enable);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigForwardSoftLimitThreshold(void *handle, int forwardSensorLimit, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigReverseSoftLimitThreshold(void *handle, int reverseSensorLimit, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigForwardSoftLimitEnable(void *handle, bool enable, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigReverseSoftLimitEnable(void *handle, bool enable, int timeoutMs);
	EXPORT_ void c_MotController_OverrideSoftLimitsEnable(void *handle, bool enable);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_Config_kP(void *handle, int slotIdx, double value, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_Config_kI(void *handle, int slotIdx, double value, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_Config_kD(void *handle, int slotIdx, double value, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_Config_kF(void *handle, int slotIdx, double value, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_Config_IntegralZone(void *handle, int slotIdx, double izone, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigAllowableClosedloopError(void *handle, int slotIdx, int allowableClosedLoopError, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigMaxIntegralAccumulator(void *handle, int slotIdx, double iaccum, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_SetIntegralAccumulator(void *handle, double iaccum, int pidIdx, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetClosedLoopError(void *handle, int *closedLoopError, int pidIdx);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetIntegralAccumulator(void *handle, double *iaccum, int pidIdx);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetErrorDerivative(void *handle, double *derror, int pidIdx);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_SelectProfileSlot(void *handle, int slotIdx, int pidIdx);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetActiveTrajectoryPosition(void *handle, int *param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetActiveTrajectoryVelocity(void *handle, int *param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetActiveTrajectoryHeading(void *handle, double *param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetActiveTrajectoryAll(void *handle, int * vel, int * pos, double *heading);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigMotionCruiseVelocity(void *handle, int sensorUnitsPer100ms, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigMotionAcceleration(void *handle, int sensorUnitsPer100msPerSec, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ClearMotionProfileTrajectories(void *handle);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetMotionProfileTopLevelBufferCount(void *handle, int * value);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_PushMotionProfileTrajectory(void *handle, double position,
			double velocity, double headingDeg, int profileSlotSelect, bool isLastPoint, bool zeroPos);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_PushMotionProfileTrajectory_2(
		void *handle, double position, double velocity, double headingDeg,
		int profileSlotSelect0, int profileSlotSelect1, bool isLastPoint, bool zeroPos, int durationMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_IsMotionProfileTopLevelBufferFull(void *handle, bool * value);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ProcessMotionProfileBuffer(void *handle);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetMotionProfileStatus(void *handle,
			int *topBufferRem, int *topBufferCnt, int *btmBufferCnt,
			bool *hasUnderrun, bool *isUnderrun, bool *activePointValid,
			bool *isLast, int *profileSlotSelect, int *outputEnable);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetMotionProfileStatus_2(void *handle,
			int *topBufferRem, int *topBufferCnt, int *btmBufferCnt,
			bool *hasUnderrun, bool *isUnderrun, bool *activePointValid,
			bool *isLast, int *profileSlotSelect, int *outputEnable, int *timeDurMs,
			int *profileSlotSelect1);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ClearMotionProfileHasUnderrun(void *handle,
			int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ChangeMotionControlFramePeriod(void *handle,
			int periodMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigMotionProfileTrajectoryPeriod(
			void *handle, int durationMs, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetLastError(void *handle);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetFirmwareVersion(void *handle, int *);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_HasResetOccurred(void *handle,bool *);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigSetCustomParam(void *handle, int newValue, int paramIndex, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigGetCustomParam(void *handle, int *readValue, int paramIndex, int timoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigSetParameter(void *handle, int param, double value, int subValue, int ordinal, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigGetParameter(void *handle, int param, double *value, int ordinal, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigPeakCurrentLimit(void *handle, int amps, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigPeakCurrentDuration(void *handle, int milliseconds, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ConfigContinuousCurrentLimit(void *handle, int amps, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_EnableCurrentLimit(void *handle, bool enable);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_SetLastError(void *handle, int error);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetAnalogIn(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_SetAnalogPosition(void *handle,int newPosition, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetAnalogInRaw(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetAnalogInVel(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetQuadraturePosition(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_SetQuadraturePosition(void *handle,int newPosition, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetQuadratureVelocity(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetPulseWidthPosition(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_SetPulseWidthPosition(void *handle,int newPosition, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetPulseWidthVelocity(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetPulseWidthRiseToFallUs(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetPulseWidthRiseToRiseUs(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetPinStateQuadA(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetPinStateQuadB(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetPinStateQuadIdx(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_IsFwdLimitSwitchClosed(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_IsRevLimitSwitchClosed(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetFaults(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetStickyFaults(void *handle, int * param);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_ClearStickyFaults(void *handle, int timeoutMs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_SelectDemandType(void *handle, bool enable);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_SetMPEOutput(void *handle, int MpeOutput);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_EnableHeadingHold(void *handle, bool enable);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetAnalogInAll(void *handle, int * withOv, int * raw, int * vel);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetQuadratureSensor(void *handle, int * pos, int * vel);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetPulseWidthAll(void *handle, int * pos, int * vel, int * riseToRiseUs, int * riseToFallUs);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetQuadPinStates(void *handle, int * quadA, int * quadB, int * quadIdx);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetLimitSwitchState(void *handle, int * isFwdClosed, int * isRevClosed);
	EXPORT_ ctre::phoenix::ErrorCode c_MotController_GetClosedLoopTarget(void *handle, int * value, int pidIdx);
}

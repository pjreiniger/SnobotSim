

#include "ctre/phoenix/CCI/MotController_CCI.h"
#include "MockHooks.h"

#include <iostream>
#include <string>
#include <vector>
#include <cstring>

#define RECEIVE_HELPER(paramName, size)                                         \
    MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);  \
    uint8_t buffer[size];                                                       \
    std::memset(&buffer[0], 0, size);                                           \
    wrapper->Receive(paramName, buffer);                                        \
    uint32_t buffer_pos = 0;


std::vector<SnobotSim::CTRE_CallbackFunc> gMotorControllerCallbacks;

void SnobotSim::SetMotControllerCallback(SnobotSim::CTRE_CallbackFunc callback)
{
    gMotorControllerCallbacks.clear();
    gMotorControllerCallbacks.push_back(callback);
}

template <typename T>
void PoplateReceiveResults(uint8_t* buffer, T* value, uint32_t& offset)
{
    std::memcpy(value, &buffer[offset], sizeof(*value));
    offset += sizeof(*value);
}


class MotorControllerWrapper
{
public:

    const int mDeviceId;

    MotorControllerWrapper(int deviceId) :
        mDeviceId(deviceId & 0x3F)
    {
        Send("Create");
    }
    void Send(const std::string& aName, uint8_t* aBuffer)
    {
        if(!gMotorControllerCallbacks.empty())
        {
            gMotorControllerCallbacks[0](aName.c_str(), mDeviceId, aBuffer);
        }
        else
        {
            LOG_UNSUPPORTED_CAN_FUNC("Callback " << aName << " not registered");
        }
    }

    void Receive(const std::string& aName, uint8_t* aBuffer)
    {
        if(!gMotorControllerCallbacks.empty())
        {
            gMotorControllerCallbacks[0](aName.c_str(), mDeviceId, aBuffer);
        }
        else
        {
            LOG_UNSUPPORTED_CAN_FUNC("Callback " << aName << " not registered");
        }
    }
    void Send(const std::string& aName)
    {
        uint8_t buffer[1];
        Send(aName, buffer);
    }

    template<typename T0>
    void Send(const std::string& aName, T0& param0)
    {
        int size = sizeof(T0);

        uint8_t* buffer = new uint8_t[size];
        std::memset(&buffer[0], 0, size);

        uint32_t offset = 0;
        PushValue(buffer, param0, offset);
        Send(aName, buffer);

        delete[] buffer;

    }

    template<typename T0, typename T1>
    void Send(const std::string& aName, T0& param0, T1& param1)
    {
        int size = sizeof(T0) + sizeof(T1);

        uint8_t* buffer = new uint8_t[size];
        std::memset(&buffer[0], 0, size);

        uint32_t offset = 0;
        PushValue(buffer, param0, offset);
        PushValue(buffer, param1, offset);
        Send(aName, buffer);

        delete[] buffer;
    }

    template<typename T0, typename T1, typename T2>
    void Send(const std::string& aName, T0& param0, T1& param1, T2& param2)
    {
        int size = sizeof(T0) + sizeof(T1) + sizeof(T2);

        uint8_t* buffer = new uint8_t[size];
        std::memset(&buffer[0], 0, size);

        uint32_t offset = 0;
        PushValue(buffer, param0, offset);
        PushValue(buffer, param1, offset);
        PushValue(buffer, param2, offset);
        Send(aName, buffer);

        delete[] buffer;
    }

    template<typename T0, typename T1, typename T2, typename T3>
    void Send(const std::string& aName, T0& param0, T1& param1, T2& param2, T3& param3)
    {
        int size = sizeof(T0) + sizeof(T1) + sizeof(T2) + sizeof(T3);

        uint8_t* buffer = new uint8_t[size];
        std::memset(&buffer[0], 0, size);

        uint32_t offset = 0;
        PushValue(buffer, param0, offset);
        PushValue(buffer, param1, offset);
        PushValue(buffer, param2, offset);
        PushValue(buffer, param3, offset);
        Send(aName, buffer);

        delete[] buffer;
    }
    template<typename T0, typename T1, typename T2, typename T3, typename T4, typename T5>
    void Send(const std::string& aName, T0& param0, T1& param1, T2& param2, T3& param3, T4& param4, T5& param5)
    {
        int size = sizeof(T0) + sizeof(T1) + sizeof(T2) + sizeof(T3) + sizeof(T4) + sizeof(T5);

        uint8_t* buffer = new uint8_t[size];
        std::memset(&buffer[0], 0, size);

        uint32_t offset = 0;
        PushValue(buffer, param0, offset);
        PushValue(buffer, param1, offset);
        PushValue(buffer, param2, offset);
        PushValue(buffer, param3, offset);
        PushValue(buffer, param4, offset);
        PushValue(buffer, param5, offset);
        Send(aName, buffer);

        delete[] buffer;
    }
    template<typename T0, typename T1, typename T2, typename T3, typename T4, typename T5, typename T6>
        void Send(const std::string& aName, T0& param0, T1& param1, T2& param2, T3& param3, T4& param4, T5& param5, T6& param6)
        {
            int size = sizeof(T0) + sizeof(T1) + sizeof(T2) + sizeof(T3) + sizeof(T4) + sizeof(T5) + sizeof(T6);

            uint8_t* buffer = new uint8_t[size];
            std::memset(&buffer[0], 0, size);

            uint32_t offset = 0;
            PushValue(buffer, param0, offset);
            PushValue(buffer, param1, offset);
            PushValue(buffer, param2, offset);
            PushValue(buffer, param3, offset);
            PushValue(buffer, param4, offset);
            PushValue(buffer, param5, offset);
            PushValue(buffer, param6, offset);
            Send(aName, buffer);

            delete[] buffer;
        }

    template <typename T>
    void PushValue(uint8_t* buffer, T& value, uint32_t& offset)
    {
        std::memcpy(&buffer[offset], &value, sizeof(value));
        offset += sizeof(value);
    }
};




MotorControllerWrapper* ConvertToMotorControllerWrapper(void* param)
{
    long handle = *static_cast<long*>(param);

    return (MotorControllerWrapper*) handle;
}


extern "C"{
    void c_Testblah()
    {
        LOG_UNSUPPORTED_CAN_FUNC("");
    }
    void* c_MotController_Create1(int baseArbId)
    {
        MotorControllerWrapper* output = new MotorControllerWrapper(baseArbId);
        return output;
    }

    ctre::phoenix::ErrorCode c_MotController_GetDeviceNumber(void *handle, int *deviceNumber)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        *deviceNumber = wrapper->mDeviceId;
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_SetDemand(void *handle, int mode, int demand0, int demand1)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SetDemand", mode, demand0, demand1);
        return (ctre::phoenix::ErrorCode) 0;
    }
    void c_MotController_SetNeutralMode(void *handle, int neutralMode)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SetNeutralMode", neutralMode);
    }
    void c_MotController_SetSensorPhase(void *handle, bool PhaseSensor)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SetSensorPhase", PhaseSensor);
    }
    void c_MotController_SetInverted(void *handle, bool invert)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SetInverted", invert);
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigOpenLoopRamp(void *handle, double secondsFromNeutralToFull, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigOpenLoopRamp", secondsFromNeutralToFull);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigClosedLoopRamp(void *handle, double secondsFromNeutralToFull, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigClosedLoopRamp", secondsFromNeutralToFull);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigPeakOutputForward(void *handle, double percentOut, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigPeakOutputForward", percentOut);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigPeakOutputReverse(void *handle, double percentOut, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigPeakOutputReverse", percentOut);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigNominalOutputForward(void *handle, double percentOut, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigNominalOutputForward", percentOut);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigNominalOutputReverse(void *handle, double percentOut, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigNominalOutputReverse", percentOut);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigNeutralDeadband(void *handle, double percentDeadband, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigNeutralDeadband", percentDeadband);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigVoltageCompSaturation(void *handle, double voltage, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigVoltageCompSaturation", voltage);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigVoltageMeasurementFilter(void *handle, int filterWindowSamples, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigVoltageMeasurementFilter", filterWindowSamples);
        return (ctre::phoenix::ErrorCode) 0;
    }
    void c_MotController_EnableVoltageCompensation(void *handle, bool enable)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("EnableVoltageCompensation", enable);
    }
    ctre::phoenix::ErrorCode c_MotController_GetBusVoltage(void *handle, double *voltage)
    {
        RECEIVE_HELPER("GetBusVoltage", sizeof(*voltage));
        PoplateReceiveResults(buffer, voltage, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetMotorOutputPercent(void *handle, double *percentOutput)
    {
        RECEIVE_HELPER("GetMotorOutputPercent", sizeof(*percentOutput));
        PoplateReceiveResults(buffer, percentOutput, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetOutputCurrent(void *handle, double *current)
    {
        RECEIVE_HELPER("GetOutputCurrent", sizeof(*current));
        PoplateReceiveResults(buffer, current, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetTemperature(void *handle, double *temperature)
    {
        RECEIVE_HELPER("GetTemperature", sizeof(*temperature));
        PoplateReceiveResults(buffer, temperature, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigRemoteFeedbackFilter(void *handle, int arbId, int peripheralIdx, int reserved, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigRemoteFeedbackFilter", arbId, peripheralIdx, reserved);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigSelectedFeedbackSensor(void *handle, int feedbackDevice, int pidIdx, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigSelectedFeedbackSensor", feedbackDevice);

        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigSensorTerm(void *handle, int sensorTerm, int feedbackDevice, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigSensorTerm", sensorTerm, feedbackDevice, timeoutMs);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetSelectedSensorPosition(void *handle, int *param, int pidIdx)
    {
        RECEIVE_HELPER("GetSelectedSensorPosition", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetSelectedSensorVelocity(void *handle, int *param, int pidIdx)
    {
        RECEIVE_HELPER("GetSelectedSensorVelocity", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_SetSelectedSensorPosition(void *handle, int sensorPos, int pidIdx, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SetSelectedSensorPosition", sensorPos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_SetControlFramePeriod(void *handle, int frame, int periodMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SetControlFramePeriod", frame);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_SetStatusFramePeriod(void *handle, int frame, int periodMs, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SetStatusFramePeriod", frame, periodMs);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetStatusFramePeriod(void *handle, int frame, int *periodMs, int timeoutMs)
    {
        RECEIVE_HELPER("GetStatusFramePeriod", sizeof(*periodMs));
        PoplateReceiveResults(buffer, periodMs, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigVelocityMeasurementPeriod(void *handle, int period, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigVelocityMeasurementPeriod", period);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigVelocityMeasurementWindow(void *handle, int windowSize, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigVelocityMeasurementWindow", windowSize);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigForwardLimitSwitchSource(void *handle, int type, int normalOpenOrClose, int deviceID, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigForwardLimitSwitchSource", type, normalOpenOrClose, deviceID);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigReverseLimitSwitchSource(void *handle, int type, int normalOpenOrClose, int deviceID, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigReverseLimitSwitchSource", type, normalOpenOrClose, deviceID);
        return (ctre::phoenix::ErrorCode) 0;
    }
    void c_MotController_OverrideLimitSwitchesEnable(void *handle, bool enable)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("OverrideLimitSwitchesEnable", enable);
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigForwardSoftLimitThreshold(void *handle, int forwardSensorLimit, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigForwardSoftLimitThreshold", forwardSensorLimit, timeoutMs);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigReverseSoftLimitThreshold(void *handle, int reverseSensorLimit, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigReverseSoftLimitThreshold", reverseSensorLimit, timeoutMs);
        return (ctre::phoenix::ErrorCode) 0;
    }

    ctre::phoenix::ErrorCode c_MotController_ConfigForwardSoftLimitEnable(void *handle, bool forwardSensorLimit, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigForwardSoftLimitEnable", forwardSensorLimit);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigReverseSoftLimitEnable(void *handle, bool reverseSensorLimit, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigReverseSoftLimitEnable", reverseSensorLimit);
        return (ctre::phoenix::ErrorCode) 0;
    }
    void c_MotController_OverrideSoftLimitsEnable(void *handle, bool enable)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("EnableSoftLimits", enable);
    }
    ctre::phoenix::ErrorCode c_MotController_Config_kP(void *handle, int slotIdx, double value, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("Config_kP", slotIdx, value);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_Config_kI(void *handle, int slotIdx, double value, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("Config_kI", slotIdx, value);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_Config_kD(void *handle, int slotIdx, double value, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("Config_kD", slotIdx, value);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_Config_kF(void *handle, int slotIdx, double value, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("Config_kF", slotIdx, value);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_Config_IntegralZone(void *handle, int slotIdx, double izone, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("Config_IntegralZone", slotIdx, izone);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigAllowableClosedloopError(void *handle, int slotIdx, int allowableClosedLoopError, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigAllowableClosedloopError", slotIdx, allowableClosedLoopError);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigMaxIntegralAccumulator(void *handle, int slotIdx, double iaccum, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigMaxIntegralAccumulator", slotIdx, iaccum);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_SetIntegralAccumulator(void *handle, double iaccum, int pidIdx, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SetIntegralAccumulator", iaccum);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetClosedLoopError(void *handle, int *closedLoopError, int slotIdx)
    {
        RECEIVE_HELPER("GetClosedLoopError", sizeof(*closedLoopError));
        PoplateReceiveResults(buffer, closedLoopError, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetIntegralAccumulator(void *handle, double *iaccum, int slotIdx)
    {
        RECEIVE_HELPER("GetIntegralAccumulator", sizeof(*iaccum));
        PoplateReceiveResults(buffer, iaccum, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetErrorDerivative(void *handle, double *derror, int slotIdx)
    {
        RECEIVE_HELPER("GetErrorDerivative", sizeof(*derror));
        PoplateReceiveResults(buffer, derror, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_SelectProfileSlot(void *handle, int slotIdx, int pidIdx)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SelectProfileSlot", slotIdx);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetActiveTrajectoryPosition(void *handle, int *param)
    {
        RECEIVE_HELPER("GetActiveTrajectoryPosition", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetActiveTrajectoryVelocity(void *handle, int *param)
    {
        RECEIVE_HELPER("GetActiveTrajectoryVelocity", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetActiveTrajectoryHeading(void *handle, double *param)
    {
        RECEIVE_HELPER("GetActiveTrajectoryHeading", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetActiveTrajectoryAll(void *handle, int * vel, int * pos, double *heading)
    {
        RECEIVE_HELPER("GetActiveTrajectoryAll", sizeof(*vel) + sizeof(*pos) + sizeof(*heading));
        PoplateReceiveResults(buffer, vel, buffer_pos);
        PoplateReceiveResults(buffer, pos, buffer_pos);
        PoplateReceiveResults(buffer, heading, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }

    ctre::phoenix::ErrorCode c_MotController_ConfigMotionCruiseVelocity(void *handle, int sensorUnitsPer100ms, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigMotionCruiseVelocity", sensorUnitsPer100ms);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigMotionAcceleration(void *handle, int sensorUnitsPer100msPerSec, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigMotionAcceleration", sensorUnitsPer100msPerSec);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ClearMotionProfileTrajectories(void *handle)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ClearMotionProfileTrajectories");
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetMotionProfileTopLevelBufferCount(void *handle, int * value)
    {
        RECEIVE_HELPER("GetMotionProfileTopLevelBufferCount", sizeof(*value));
        PoplateReceiveResults(buffer, value, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_PushMotionProfileTrajectory(void *handle, double position,
            double velocity, double headingDeg, int profileSlotSelect, bool isLastPoint, bool zeroPos)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("PushMotionProfileTrajectory", position, velocity, headingDeg, profileSlotSelect, isLastPoint, zeroPos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_PushMotionProfileTrajectory_2(
            void *handle, double position, double velocity, double headingDeg,
            int profileSlotSelect0, int profileSlotSelect1, bool isLastPoint, bool zeroPos, int durationMs)
    {
        LOG_UNSUPPORTED_CAN_FUNC("");
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_IsMotionProfileTopLevelBufferFull(void *handle, bool * value)
    {
        RECEIVE_HELPER("IsMotionProfileTopLevelBufferFull", sizeof(*value));
        PoplateReceiveResults(buffer, value, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ProcessMotionProfileBuffer(void *handle)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ProcessMotionProfileBuffer");
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetMotionProfileStatus(void *handle,
            int *topBufferRem, int *topBufferCnt, int *btmBufferCnt,
            bool *hasUnderrun, bool *isUnderrun, bool *activePointValid,
            bool *isLast, int *profileSlotSelect, int *outputEnable)
    {
        RECEIVE_HELPER("GetMotionProfileStatus", sizeof(int) * 5 + sizeof(bool) * 4);
        PoplateReceiveResults(buffer, topBufferRem, buffer_pos);
        PoplateReceiveResults(buffer, topBufferCnt, buffer_pos);
        PoplateReceiveResults(buffer, btmBufferCnt, buffer_pos);
        PoplateReceiveResults(buffer, hasUnderrun, buffer_pos);
        PoplateReceiveResults(buffer, isUnderrun, buffer_pos);
        PoplateReceiveResults(buffer, activePointValid, buffer_pos);
        PoplateReceiveResults(buffer, isLast, buffer_pos);
        PoplateReceiveResults(buffer, profileSlotSelect, buffer_pos);
        PoplateReceiveResults(buffer, outputEnable, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetMotionProfileStatus_2(void *handle,
            int *topBufferRem, int *topBufferCnt, int *btmBufferCnt,
            bool *hasUnderrun, bool *isUnderrun, bool *activePointValid,
            bool *isLast, int *profileSlotSelect, int *outputEnable, int *timeDurMs,
            int *profileSlotSelect1)
    {
        LOG_UNSUPPORTED_CAN_FUNC("");
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ClearMotionProfileHasUnderrun(void *handle,
            int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ClearMotionProfileHasUnderrun", timeoutMs);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ChangeMotionControlFramePeriod(void *handle,
            int periodMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ChangeMotionControlFramePeriod", periodMs);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigMotionProfileTrajectoryPeriod(
            void *handle, int durationMs, int timeoutMs)
	{
        LOG_UNSUPPORTED_CAN_FUNC("");
        return (ctre::phoenix::ErrorCode) 0;
	}
    ctre::phoenix::ErrorCode c_MotController_GetLastError(void *handle)
    {
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetFirmwareVersion(void *handle, int * version)
    {
        RECEIVE_HELPER("GetFirmwareVersion", sizeof(*version));
        PoplateReceiveResults(buffer, version, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_HasResetOccurred(void *handle, bool* output)
    {
        RECEIVE_HELPER("HasResetOccurred", sizeof(*output));
        PoplateReceiveResults(buffer, output, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigSetCustomParam(void *handle, int newValue, int paramIndex, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigSetCustomParam", newValue, paramIndex);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigGetCustomParam(void *handle, int *readValue, int paramIndex, int timoutMs)
    {
        RECEIVE_HELPER("ConfigGetCustomParam", sizeof(*readValue));
        PoplateReceiveResults(buffer, readValue, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigSetParameter(void *handle, int param, double value, int subValue, int ordinal, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigSetParameter", param, value, subValue, ordinal);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigGetParameter(void *handle, int param, double *value, int ordinal, int timeoutMs)
    {
        RECEIVE_HELPER("ConfigGetParameter", sizeof(*value));
        PoplateReceiveResults(buffer, value, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigPeakCurrentLimit(void *handle, int amps, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigPeakCurrentLimit", amps);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigPeakCurrentDuration(void *handle, int milliseconds, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigPeakCurrentDuration", milliseconds);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ConfigContinuousCurrentLimit(void *handle, int amps, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ConfigContinuousCurrentLimit", amps);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_EnableCurrentLimit(void *handle, bool enable)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("EnableCurrentLimit", enable);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_SetLastError(void *handle, int error)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SetLastError", error);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetAnalogIn(void *handle, int * param)
    {
        RECEIVE_HELPER("GetAnalogIn", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_SetAnalogPosition(void *handle,int newPosition, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SetAnalogPosition", newPosition, timeoutMs);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetAnalogInRaw(void *handle, int * param)
    {
        RECEIVE_HELPER("GetAnalogInRaw", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetAnalogInVel(void *handle, int * param)
    {
        RECEIVE_HELPER("GetAnalogInVel", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetQuadraturePosition(void *handle, int * param)
    {
        RECEIVE_HELPER("GetQuadraturePosition", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_SetQuadraturePosition(void *handle,int newPosition, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SetQuadraturePosition", newPosition, timeoutMs);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetQuadratureVelocity(void *handle, int * param)
    {
        RECEIVE_HELPER("GetQuadratureVelocity", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetPulseWidthPosition(void *handle, int * param)
    {
        RECEIVE_HELPER("GetPulseWidthPosition", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_SetPulseWidthPosition(void *handle,int newPosition, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SetQuadraturePosition", newPosition, timeoutMs);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetPulseWidthVelocity(void *handle, int * param)
    {
        RECEIVE_HELPER("GetPulseWidthVelocity", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetPulseWidthRiseToFallUs(void *handle, int * param)
    {
        RECEIVE_HELPER("GetPulseWidthRiseToFallUs", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetPulseWidthRiseToRiseUs(void *handle, int * param)
    {
        RECEIVE_HELPER("GetPulseWidthRiseToRiseUs", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetPinStateQuadA(void *handle, int * param)
    {
        RECEIVE_HELPER("GetPinStateQuadA", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetPinStateQuadB(void *handle, int * param)
    {
        RECEIVE_HELPER("GetPinStateQuadB", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetPinStateQuadIdx(void *handle, int * param)
    {
        RECEIVE_HELPER("GetPinStateQuadIdx", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_IsFwdLimitSwitchClosed(void *handle, int * param)
    {
        RECEIVE_HELPER("IsFwdLimitSwitchClosed", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_IsRevLimitSwitchClosed(void *handle, int * param)
    {
        RECEIVE_HELPER("IsRevLimitSwitchClosed", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetFaults(void *handle, int * param)
    {
        RECEIVE_HELPER("GetFaults", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetStickyFaults(void *handle, int * param)
    {
        RECEIVE_HELPER("GetStickyFaults", sizeof(*param));
        PoplateReceiveResults(buffer, param, buffer_pos);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_ClearStickyFaults(void *handle, int timeoutMs)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("ClearStickyFaults", timeoutMs);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_SelectDemandType(void *handle, bool enable)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SelectDemandType", enable);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_SetMPEOutput(void *handle, int MpeOutput)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("SetMPEOutput", MpeOutput);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_EnableHeadingHold(void *handle, bool enable)
    {
        MotorControllerWrapper* wrapper = ConvertToMotorControllerWrapper(handle);
        wrapper->Send("EnableHeadingHold", enable);
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetAnalogInAll(void *handle, int * withOv, int * raw, int * vel)
    {
        LOG_UNSUPPORTED_CAN_FUNC("");
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetQuadratureSensor(void *handle, int * pos, int * vel)
    {
        LOG_UNSUPPORTED_CAN_FUNC("");
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetPulseWidthAll(void *handle, int * pos, int * vel, int * riseToRiseUs, int * riseToFallUs)
    {
        LOG_UNSUPPORTED_CAN_FUNC("");
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetQuadPinStates(void *handle, int * quadA, int * quadB, int * quadIdx)
    {
        LOG_UNSUPPORTED_CAN_FUNC("");
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetLimitSwitchState(void *handle, int * isFwdClosed, int * isRevClosed)
    {
        LOG_UNSUPPORTED_CAN_FUNC("");
        return (ctre::phoenix::ErrorCode) 0;
    }
    ctre::phoenix::ErrorCode c_MotController_GetClosedLoopTarget(void *handle, int * value, int pidIdx)
    {
        LOG_UNSUPPORTED_CAN_FUNC("");
        return (ctre::phoenix::ErrorCode) 0;
    }
}

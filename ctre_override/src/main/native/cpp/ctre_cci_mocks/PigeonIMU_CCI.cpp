
#include "ctre/phoenix/CCI/PigeonIMU_CCI.h"

#include <cstring>
#include <vector>

#include "MockHooks.h"

#define RECEIVE_HELPER(paramName, size)                                  \
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle); \
    uint8_t buffer[size]; /* NOLINT */                                   \
    std::memset(&buffer[0], 0, size);                                    \
    wrapper->Receive(paramName, buffer);                                 \
    uint32_t offset = 0;

std::vector<SnobotSim::CTRE_CallbackFunc> gPigeonCallbacks;

template <typename T>
void PoplateReceiveResults(uint8_t* buffer, T* value, uint32_t& offset)
{
    std::memcpy(value, &buffer[offset], sizeof(*value));
    offset += sizeof(*value);
}

void SnobotSim::SetPigeonCallback(SnobotSim::CTRE_CallbackFunc callback)
{
    gPigeonCallbacks.clear();
    gPigeonCallbacks.push_back(callback);
}

class PigeonImuSimulatorWrapper
{
public:
    const int mDeviceId;

    explicit PigeonImuSimulatorWrapper(int deviceId) :
            mDeviceId(deviceId & 0x3F)
    {
        Send("Create");
    }

    void Send(const std::string& aName, uint8_t* aBuffer)
    {
        if (!gPigeonCallbacks.empty())
        {
            gPigeonCallbacks[0](aName.c_str(), mDeviceId, aBuffer);
        }
        else
        {
            LOG_UNSUPPORTED_CAN_FUNC("Callback " << aName << " not registered");
        }
    }

    void Receive(const std::string& aName, uint8_t* aBuffer)
    {
        if (!gPigeonCallbacks.empty())
        {
            gPigeonCallbacks[0](aName.c_str(), mDeviceId, aBuffer);
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

    template <typename T0>
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

    template <typename T0, typename T1>
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

    template <typename T0, typename T1, typename T2>
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

    template <typename T0, typename T1, typename T2, typename T3, typename T4>
    void Send(const std::string& aName, T0& param0, T1& param1, T2& param2, T3& param3, T4& param4)
    {
        int size = sizeof(T0) + sizeof(T1) + sizeof(T2) + sizeof(T3) + sizeof(T4);

        uint8_t* buffer = new uint8_t[size];
        std::memset(&buffer[0], 0, size);

        uint32_t offset = 0;
        PushValue(buffer, param0, offset);
        PushValue(buffer, param1, offset);
        PushValue(buffer, param2, offset);
        PushValue(buffer, param3, offset);
        PushValue(buffer, param4, offset);
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

PigeonImuSimulatorWrapper* ConvertToPigeonWrapper(void* param)
{
    long handle = *static_cast<long*>(param); // NOLINT

    return reinterpret_cast<PigeonImuSimulatorWrapper*>(handle);
}

extern "C" {
void* c_PigeonIMU_Create2(int talonDeviceID)
{
    PigeonImuSimulatorWrapper* output = new PigeonImuSimulatorWrapper(talonDeviceID);
    return output;
}
void* c_PigeonIMU_Create1(int deviceNumber)
{
    PigeonImuSimulatorWrapper* output = new PigeonImuSimulatorWrapper(deviceNumber);
    return output;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetDescription(void* handle, char* toFill, int toFillByteSz, int* numBytesFilled)
{
    RECEIVE_HELPER("GetDescription", 1);
    offset += 1; // Removes compiler warning
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_ConfigSetParameter(void* handle, int param, double paramValue, int subValue, int ordinal, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("ConfigSetParameter", param, paramValue, subValue, ordinal, timeoutMs);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_ConfigGetParameter(void* handle, int param, double* value, int ordinal, int timeoutMs)
{
    RECEIVE_HELPER("ConfigGetParameter", sizeof(double));
    PoplateReceiveResults(buffer, value, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_ConfigSetCustomParam(void* handle, int newValue, int paramIndex, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("ConfigSetCustomParam", newValue, paramIndex, timeoutMs);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_ConfigGetCustomParam(void* handle, int* readValue, int paramIndex, int timoutMs)
{
    RECEIVE_HELPER("ConfigGetCustomParam", sizeof(int));
    PoplateReceiveResults(buffer, readValue, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_SetYaw(void* handle, double angleDeg, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("SetYaw", angleDeg);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_AddYaw(void* handle, double angleDeg, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("AddYaw", angleDeg);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_SetYawToCompass(void* handle, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("SetYawToCompass");
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_SetFusedHeading(void* handle, double angleDeg, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("SetFusedHeading", angleDeg);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_AddFusedHeading(void* handle, double angleDeg, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("AddFusedHeading", angleDeg);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_SetFusedHeadingToCompass(void* handle, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("SetFusedHeadingToCompass");
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_SetAccumZAngle(void* handle, double angleDeg, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("SetAccumZAngle", angleDeg);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_ConfigTemperatureCompensationEnable(void* handle, int bTempCompEnable, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("ConfigTemperatureCompensationEnable", bTempCompEnable);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_SetCompassDeclination(void* handle, double angleDegOffset, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("SetCompassDeclination", angleDegOffset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_SetCompassAngle(void* handle, double angleDeg, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("SetCompassAngle", angleDeg);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_EnterCalibrationMode(void* handle, int calMode, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("EnterCalibrationMode", calMode);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetGeneralStatus(void* handle,
        int* state, int* currentMode, int* calibrationError, int* bCalIsBooting,
        double* tempC, int* upTimeSec, int* noMotionBiasCount, int* tempCompensationCount, int* lastError)
{
    RECEIVE_HELPER("GetGeneralStatus", sizeof(int) * 8 + sizeof(double));
    PoplateReceiveResults(buffer, state, offset);
    PoplateReceiveResults(buffer, currentMode, offset);
    PoplateReceiveResults(buffer, calibrationError, offset);
    PoplateReceiveResults(buffer, bCalIsBooting, offset);
    PoplateReceiveResults(buffer, tempC, offset);
    PoplateReceiveResults(buffer, upTimeSec, offset);
    PoplateReceiveResults(buffer, noMotionBiasCount, offset);
    PoplateReceiveResults(buffer, tempCompensationCount, offset);
    PoplateReceiveResults(buffer, lastError, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetLastError(void* handle)
{
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_Get6dQuaternion(void* handle, double wxyz[4])
{
    RECEIVE_HELPER("Get6dQuaternion", sizeof(double) * 4);
    PoplateReceiveResults(buffer, &wxyz[0], offset);
    PoplateReceiveResults(buffer, &wxyz[1], offset);
    PoplateReceiveResults(buffer, &wxyz[2], offset);
    PoplateReceiveResults(buffer, &wxyz[3], offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetYawPitchRoll(void* handle, double ypr[3])
{
    RECEIVE_HELPER("GetYawPitchRoll", sizeof(double) * 3);
    PoplateReceiveResults(buffer, &ypr[0], offset);
    PoplateReceiveResults(buffer, &ypr[1], offset);
    PoplateReceiveResults(buffer, &ypr[2], offset);
    return ctre::phoenix::NotImplemented;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetAccumGyro(void* handle, double xyz_deg[3])
{
    RECEIVE_HELPER("GetAccumGyro", sizeof(double) * 3);
    PoplateReceiveResults(buffer, &xyz_deg[0], offset);
    PoplateReceiveResults(buffer, &xyz_deg[1], offset);
    PoplateReceiveResults(buffer, &xyz_deg[2], offset);
    return ctre::phoenix::NotImplemented;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetAbsoluteCompassHeading(void* handle, double* value)
{
    RECEIVE_HELPER("GetAbsoluteCompassHeading", sizeof(double));
    PoplateReceiveResults(buffer, value, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetCompassHeading(void* handle, double* value)
{
    RECEIVE_HELPER("GetCompassHeading", sizeof(double));
    PoplateReceiveResults(buffer, value, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetCompassFieldStrength(void* handle, double* value)
{
    RECEIVE_HELPER("GetCompassFieldStrength", sizeof(double));
    PoplateReceiveResults(buffer, value, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetTemp(void* handle, double* value)
{
    RECEIVE_HELPER("GetTemp", sizeof(double));
    PoplateReceiveResults(buffer, value, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetState(void* handle, int* state)
{
    RECEIVE_HELPER("GetState", sizeof(int));
    PoplateReceiveResults(buffer, state, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetUpTime(void* handle, int* value)
{
    RECEIVE_HELPER("GetUpTime", sizeof(int));
    PoplateReceiveResults(buffer, value, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetRawMagnetometer(void* handle, short rm_xyz[3]) // NOLINT
{
    RECEIVE_HELPER("GetRawMagnetometer", sizeof(short) * 3); // NOLINT
    PoplateReceiveResults(buffer, &rm_xyz[0], offset);
    PoplateReceiveResults(buffer, &rm_xyz[1], offset);
    PoplateReceiveResults(buffer, &rm_xyz[2], offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetBiasedMagnetometer(void* handle, short bm_xyz[3]) // NOLINT
{
    RECEIVE_HELPER("GetBiasedMagnetometer", sizeof(short) * 3); // NOLINT
    PoplateReceiveResults(buffer, &bm_xyz[0], offset);
    PoplateReceiveResults(buffer, &bm_xyz[1], offset);
    PoplateReceiveResults(buffer, &bm_xyz[2], offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetBiasedAccelerometer(void* handle, short ba_xyz[3]) // NOLINT
{
    RECEIVE_HELPER("GetBiasedAccelerometer", sizeof(short) * 3); // NOLINT
    PoplateReceiveResults(buffer, &ba_xyz[0], offset);
    PoplateReceiveResults(buffer, &ba_xyz[1], offset);
    PoplateReceiveResults(buffer, &ba_xyz[2], offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetRawGyro(void* handle, double xyz_dps[3])
{
    RECEIVE_HELPER("GetRawGyro", sizeof(double) * 3);
    PoplateReceiveResults(buffer, &xyz_dps[0], offset);
    PoplateReceiveResults(buffer, &xyz_dps[1], offset);
    PoplateReceiveResults(buffer, &xyz_dps[2], offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetAccelerometerAngles(void* handle, double tiltAngles[3])
{
    RECEIVE_HELPER("GetAccelerometerAngles", sizeof(double) * 3);
    PoplateReceiveResults(buffer, &tiltAngles[0], offset);
    PoplateReceiveResults(buffer, &tiltAngles[1], offset);
    PoplateReceiveResults(buffer, &tiltAngles[2], offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetFusedHeading2(void* handle, int* bIsFusing, int* bIsValid, double* value, int* lastError)
{
    RECEIVE_HELPER("GetFusedHeading", sizeof(double));
    PoplateReceiveResults(buffer, value, offset);

    *bIsFusing = 5;
    *bIsValid = 5;
    *lastError = ctre::phoenix::NotImplemented;

    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetFusedHeading1(void* handle, double* value)
{
    RECEIVE_HELPER("GetFusedHeading", sizeof(double) * 3);
    PoplateReceiveResults(buffer, &value[0], offset);
    PoplateReceiveResults(buffer, &value[1], offset);
    PoplateReceiveResults(buffer, &value[2], offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetResetCount(void* handle, int* value)
{
    RECEIVE_HELPER("GetResetCount", sizeof(int));
    PoplateReceiveResults(buffer, value, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetResetFlags(void* handle, int* value)
{
    RECEIVE_HELPER("GetResetFlags", sizeof(int));
    PoplateReceiveResults(buffer, value, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetFirmwareVersion(void* handle, int* firmwareVers)
{
    RECEIVE_HELPER("GetFirmwareVersion", sizeof(int));
    PoplateReceiveResults(buffer, firmwareVers, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_HasResetOccurred(void* handle, bool* hasReset)
{
    RECEIVE_HELPER("HasResetOccured", sizeof(bool));
    PoplateReceiveResults(buffer, hasReset, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_SetLastError(void* handle, int value)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("SetLastError", value);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetFaults(void* handle, int* param)
{
    RECEIVE_HELPER("GetFaults", sizeof(int));
    PoplateReceiveResults(buffer, param, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetStickyFaults(void* handle, int* param)
{
    RECEIVE_HELPER("GetStickyFaults", sizeof(int));
    PoplateReceiveResults(buffer, param, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_ClearStickyFaults(void* handle, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("ClearStickyFaults", timeoutMs);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_SetStatusFramePeriod(void* handle, int frame, int periodMs, int timeoutMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("SetStatusFramePeriod", frame, periodMs, timeoutMs);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_GetStatusFramePeriod(void* handle, int frame, int* periodMs, int timeoutMs)
{
    RECEIVE_HELPER("GetStatusFramePeriod", sizeof(int));
    PoplateReceiveResults(buffer, periodMs, offset);
    return (ctre::phoenix::ErrorCode)0;
}
ctre::phoenix::ErrorCode c_PigeonIMU_SetControlFramePeriod(void* handle, int frame, int periodMs)
{
    PigeonImuSimulatorWrapper* wrapper = ConvertToPigeonWrapper(handle);
    wrapper->Send("SetControlFramePeriod", frame, periodMs);
    return (ctre::phoenix::ErrorCode)0;
}
} // extern "C"
